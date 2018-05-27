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
	
	public List<Transmitter> optimise(List<Receiver> receivers, List<Transmitter> transmitters) {
		if (receivers.stream().allMatch(r -> signalCalculator.hasSignal(r, transmitters))) {
			return transmitters;
		}

		List<Boost> possibleBoosts = PossibleBoosts(transmitters, receivers);
		
		Boost cheapestBoost = possibleBoosts.stream().min((b1, b2) -> Double.compare(b1.boostPerCoverage(), b2.boostPerCoverage())).get();
	
		List<Transmitter> newTransmitters = transmitters.stream().map(t -> t.id == cheapestBoost.transmitter.id ? new Transmitter(t.id, t.location, t.power + cheapestBoost.boost) : new Transmitter(t.id, t.location, t.power)).collect(Collectors.toList());
		return optimise(receivers, newTransmitters);
	}
	
	public List<Boost> PossibleBoosts(List<Transmitter> transmitters, List<Receiver> receivers) {

		List<Boost> boosts = new ArrayList<Boost>();

		for (Transmitter t : transmitters) {
			List<Receiver> receiversWithoutSignal = receivers.stream().filter(r -> !signalCalculator.hasSignal(r, transmitters)).collect(Collectors.toList());
		
			while (receiversWithoutSignal.size() > 0) {
				Receiver closestReceiver = receiversWithoutSignal.stream().min((r1, r2) -> Integer.compare(distanceCalculator.distance(t.location, r1.location), distanceCalculator.distance(t.location, r2.location))).get();
				int boostNeeded = distanceCalculator.distance(t.location, closestReceiver.location) - t.power;
				Transmitter boostedTransmitter = new Transmitter(t.id, t.location, t.power + boostNeeded);
				final List<Transmitter> transmittersWithBoostedTransmitter = transmitters.stream().map(x -> x.id == t.id ? boostedTransmitter : new Transmitter(x.id, x.location, x.power)).collect(Collectors.toList());
				int coverageAfterBoost = (int)receivers.stream().filter(r -> signalCalculator.hasSignal(r, transmittersWithBoostedTransmitter)).count();
				boosts.add(new Boost(t, boostNeeded, coverageAfterBoost));
				receiversWithoutSignal = receivers.stream().filter(r -> !signalCalculator.hasSignal(r, transmittersWithBoostedTransmitter)).collect(Collectors.toList());
			}
		}
		
		return boosts;
	}

	
	@Override
	public Result optimise(Scenario scenario) {
		return new Result(
				optimise(scenario.receivers, scenario.transmitters)
		);
	}
}
