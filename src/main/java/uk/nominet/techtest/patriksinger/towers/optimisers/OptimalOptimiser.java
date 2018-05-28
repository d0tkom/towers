package uk.nominet.techtest.patriksinger.towers.optimisers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uk.nominet.techtest.patriksinger.towers.calculators.ChebyshevDistanceCalculator;
import uk.nominet.techtest.patriksinger.towers.calculators.DistanceCalculator;
import uk.nominet.techtest.patriksinger.towers.calculators.SignalCalculator;
import uk.nominet.techtest.patriksinger.towers.model.Boost;
import uk.nominet.techtest.patriksinger.towers.model.Receiver;
import uk.nominet.techtest.patriksinger.towers.model.Result;
import uk.nominet.techtest.patriksinger.towers.model.Scenario;
import uk.nominet.techtest.patriksinger.towers.model.Transmitter;

public class OptimalOptimiser implements PowerOptimiser {
	private final DistanceCalculator distanceCalculator = new ChebyshevDistanceCalculator();
	private final SignalCalculator signalCalculator = new SignalCalculator(distanceCalculator);
	
	public OptimalOptimiser() {
	}
	
	// This function takes a given scenario and returns all the possible ways transmitters
	// could be turned up (one transmitter at a time) to add at least one more receiver to the coverage
	public List<Boost> PossibleBoosts(Scenario scenario) {
		List<Boost> boosts = new ArrayList<Boost>();

		for (Transmitter t : scenario.transmitters) {
			// Get list of receivers without signal at the moment, so we can exit the while loop
			// down the line once we get to full coverage. (Receivers that have signal will always
			// have signal as we cannot decrease power of transmitters);
			List<Receiver> receiversWithoutSignal = scenario.receivers
					.stream()
					.filter(r -> !signalCalculator.hasSignal(r, scenario.transmitters))
					.collect(Collectors.toList());
			
			while (receiversWithoutSignal.size() > 0) {
				// Find the closes receiver so we can figure out the power needed to provide coverage to it
				Receiver closestReceiver = receiversWithoutSignal
						.stream()
						.min((r1, r2) -> 
							Integer.compare(
									distanceCalculator.distance(t.location, r1.location), 
									distanceCalculator.distance(t.location, r2.location)
							)
						)
						.get();
				// Calculate the extra power the transmitter would need to provide coverage
				int boostNeeded = distanceCalculator.distance(t.location, closestReceiver.location) - t.power;
				Transmitter boostedTransmitter = new Transmitter(t.id, t.location, t.power + boostNeeded);
				// Create a new list of transmitters with the new boosted transmitter to determine the new coverage it would give. It can be more than
				// +1 in cases when we had more receivers in the same distance
				final List<Transmitter> transmittersWithBoostedTransmitter = scenario.transmitters
						.stream()
						.map(x -> 
							x.id == t.id ? 
							boostedTransmitter : 
							new Transmitter(x.id, x.location, x.power)
						)
						.collect(Collectors.toList());
				// Determine coverage after we turned up the transmitter so we can determine the (extra power)/(coverage) ratio inside
				// our boost class
				int coverageAfterBoost = (int)scenario.receivers
						.stream()
						.filter(r -> signalCalculator.hasSignal(r, transmittersWithBoostedTransmitter))
						.count();
				boosts.add(new Boost(t, boostNeeded, coverageAfterBoost));
				// Create new list of receivers without signal to see if there are more possible cases for this transmitter.
				// If we reached full coverage we can skip to the next transmitter
				receiversWithoutSignal = scenario.receivers
						.stream()
						.filter(r -> !signalCalculator.hasSignal(r, transmittersWithBoostedTransmitter))
						.collect(Collectors.toList());
			}
		}
		
		return boosts;
	}

	
	@Override
	public Result optimise(Scenario scenario) {
		if (scenario.receivers.stream().allMatch(r -> signalCalculator.hasSignal(r, scenario.transmitters))) {
			return new Result(scenario.transmitters);
		}
		
		List<Boost> possibleBoosts = PossibleBoosts(scenario);
		Boost cheapestBoost = possibleBoosts
				.stream()
				.min((b1, b2) -> Double.compare(b1.boostPerCoverage(), b2.boostPerCoverage()))
				.get();
		List<Transmitter> newTransmitters = scenario.transmitters
				.stream()
				.map(t -> 
					t.id == cheapestBoost.transmitter.id ? 
					new Transmitter(t.id, t.location, t.power + cheapestBoost.boost) : 
					new Transmitter(t.id, t.location, t.power)
				)
				.collect(Collectors.toList());
		Scenario newScenario = new Scenario(newTransmitters, scenario.receivers);
		return optimise(newScenario);

	}
}
