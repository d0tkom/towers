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
	
	// This function takes in a scenario, and finds which transmitter, to which power level could be turned up
	// with the best (extra power)/(extra coverage) ratio, creates a new scenario with this transmitter turned up
	// and keeps calling itself with these new scenarios until we reach full coverage at which point it returns the
	// transmitters in that scenario as the solution.
	@Override
	public Result optimise(Scenario scenario) {
		// Check if all receivers have coverage. If they have we have our solution.
		if (scenario.receivers.stream().allMatch(r -> signalCalculator.hasSignal(r, scenario.transmitters))) {
			return new Result(scenario.transmitters);
		}
		
		// Get the list of possibleBoost we could apply to the transmitters to get extra coverage
		List<Boost> possibleBoosts = PossibleBoosts(scenario);
		// Find the boost with the best (extra power)/(extra coverage) ratio
		Boost cheapestBoost = possibleBoosts
				.stream()
				.min((b1, b2) -> Double.compare(b1.boostPerCoverage(), b2.boostPerCoverage()))
				.get();
		// Create a new list of transmitters with the found cheapestBoost applied
		List<Transmitter> newTransmitters = scenario.transmitters
				.stream()
				.map(t -> 
					t.id == cheapestBoost.transmitter.id ? 
					new Transmitter(t.id, t.location, t.power + cheapestBoost.boost) : 
					new Transmitter(t.id, t.location, t.power)
				)
				.collect(Collectors.toList());
		// Create the new scenario with the new list of transmitters
		Scenario newScenario = new Scenario(newTransmitters, scenario.receivers);
		// Call optimise function with the new scenario to find the next best scenario
		return optimise(newScenario);
	}
	
	// This function takes a given scenario and returns all the possible ways transmitters
	// (a single transmitter at a time) could be turned up to provide at least +1, but no more
	// than full coverage.
	public List<Boost> PossibleBoosts(Scenario scenario) {
		// List to keep track of possible boosts.
		List<Boost> boosts = new ArrayList<Boost>();

		for (Transmitter t : scenario.transmitters) {
			// This receiversWithoutSignal list will be used to find the closest receiver without a signal, 
			// (closest to the current transmitter) and to exit the while loop down the line once we covered 
			// all cases up to full coverage.
			List<Receiver> receiversWithoutSignal = scenario.receivers
					.stream()
					.filter(r -> !signalCalculator.hasSignal(r, scenario.transmitters))
					.collect(Collectors.toList());
			
			while (receiversWithoutSignal.size() > 0) {
				// Find the closest receiver so we can figure out the power needed to provide coverage to it
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
				// Determine coverage with the new list so we can determine the (extra power)/(coverage) ratio later inside
				// our boost objects
				int coverageAfterBoost = (int)scenario.receivers
						.stream()
						.filter(r -> signalCalculator.hasSignal(r, transmittersWithBoostedTransmitter))
						.count();
				boosts.add(new Boost(t, boostNeeded, coverageAfterBoost));
				// Create new list of receiversWithoutSignal to see if there are more possible cases for this transmitter.
				// If we reached full coverage the size will be 0 and we can skip to the next transmitter
				receiversWithoutSignal = scenario.receivers
						.stream()
						.filter(r -> !signalCalculator.hasSignal(r, transmittersWithBoostedTransmitter))
						.collect(Collectors.toList());
			}
		}
		
		return boosts;
	}
}
