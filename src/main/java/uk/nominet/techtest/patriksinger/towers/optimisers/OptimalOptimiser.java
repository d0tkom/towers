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
	
	// This function takes in a list of receivers and transmitters, finds which transmitter can be turned up with the lowest 
	// (extra power)/(coverage) ratio, creates a new list with that transmitter boosted, and recursively calls itself
	// until all receivers have coverage at which point it returns the list of transmitters found;
	public List<Transmitter> optimise(List<Transmitter> transmitters, List<Receiver> receivers) {
		// If all receivers have signal we return the list of transmitters as we should have arrived to the optimal solution
		if (receivers.stream().allMatch(r -> signalCalculator.hasSignal(r, transmitters))) {
			return transmitters;
		}
		// Get list of possible boosts, so we can decide later which one would give the cheapest (extra power)/(coverage)
		List<Boost> possibleBoosts = PossibleBoosts(transmitters, receivers);
		// Find the boost with the smallest (extra power)/(coverage) ratio
		Boost cheapestBoost = possibleBoosts.stream().min((b1, b2) -> Double.compare(b1.boostPerCoverage(), b2.boostPerCoverage())).get();
		// Create a new list of transmitters with the transmitter boosted that we determined before
		List<Transmitter> newTransmitters = transmitters.stream().map(t -> t.id == cheapestBoost.transmitter.id ? new Transmitter(t.id, t.location, t.power + cheapestBoost.boost) : new Transmitter(t.id, t.location, t.power)).collect(Collectors.toList());
		// Call the function again with the new list of transmitters to find the next optimal boost
		return optimise(newTransmitters, receivers);
	}
	
	// This function takes in a list of transmitters and receivers and lists all the possible ways
	// the transmitters could be turned up to add at least one more receiver to the coverage
	public List<Boost> PossibleBoosts(List<Transmitter> transmitters, List<Receiver> receivers) {
		List<Boost> boosts = new ArrayList<Boost>();

		for (Transmitter t : transmitters) {
			// Get list of receivers without signal at the moment, as we don't need to worry about
			// receivers that are already covered
			List<Receiver> receiversWithoutSignal = receivers.stream().filter(r -> !signalCalculator.hasSignal(r, transmitters)).collect(Collectors.toList());
			
			while (receiversWithoutSignal.size() > 0) {
				// Find the closes receiver so we can figure out the power needed to provide coverage to it
				Receiver closestReceiver = receiversWithoutSignal.stream().min((r1, r2) -> Integer.compare(distanceCalculator.distance(t.location, r1.location), distanceCalculator.distance(t.location, r2.location))).get();
				// Calculate the extra power the transmitter would need to provide coverage
				int boostNeeded = distanceCalculator.distance(t.location, closestReceiver.location) - t.power;
				Transmitter boostedTransmitter = new Transmitter(t.id, t.location, t.power + boostNeeded);
				// Create a new list of transmitters with the new boosted transmitter to determine the new coverage it would give. It can be more than
				// +1 on cases when we had more receivers in the same distance
				final List<Transmitter> transmittersWithBoostedTransmitter = transmitters.stream().map(x -> x.id == t.id ? boostedTransmitter : new Transmitter(x.id, x.location, x.power)).collect(Collectors.toList());
				// Determine coverage after we turned up the transmitter so we can determine the (extra power)/(coverage) ratio inside
				// our boost class
				int coverageAfterBoost = (int)receivers.stream().filter(r -> signalCalculator.hasSignal(r, transmittersWithBoostedTransmitter)).count();
				boosts.add(new Boost(t, boostNeeded, coverageAfterBoost));
				// Create new list of receivers without signal to see if there are more possible cases for this transmitter.
				// If we reached full coverage we can skip to the next transmitter
				receiversWithoutSignal = receivers.stream().filter(r -> !signalCalculator.hasSignal(r, transmittersWithBoostedTransmitter)).collect(Collectors.toList());
			}
		}
		
		return boosts;
	}

	
	@Override
	public Result optimise(Scenario scenario) {
		return new Result(
				optimise(scenario.transmitters, scenario.receivers)
		);
	}
}
