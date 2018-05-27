package uk.nominet.techtest.patriksinger.towers.optimisers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uk.nominet.techtest.patriksinger.towers.calculators.ChebyshevDistanceCalculator;
import uk.nominet.techtest.patriksinger.towers.calculators.SignalCalculator;
import uk.nominet.techtest.patriksinger.towers.model.Boost;
import uk.nominet.techtest.patriksinger.towers.model.Receiver;
import uk.nominet.techtest.patriksinger.towers.model.Result;
import uk.nominet.techtest.patriksinger.towers.model.Scenario;
import uk.nominet.techtest.patriksinger.towers.model.Transmitter;

public class OptimalOptimiser implements PowerOptimiser {
	private final SignalCalculator signalCalculator = new SignalCalculator(new ChebyshevDistanceCalculator());
	
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
			int boost = 0;
			boolean keepGoing = true;
			long currentCoverage = receivers.stream().filter(r -> signalCalculator.hasSignal(r, transmitters)).count();
			while(keepGoing) {
				boost++;
				final Transmitter newTransmitter = new Transmitter(t.id, t.location, t.power + boost);
				final List<Transmitter> newTransmitters = transmitters.stream().map(x -> x.id == t.id ? newTransmitter : new Transmitter(x.id, x.location, x.power)).collect(Collectors.toList());
				long newCoverage = receivers.stream().filter(r -> signalCalculator.hasSignal(r, newTransmitters)).count();
				if (newCoverage != currentCoverage) {
					currentCoverage = newCoverage;
					boosts.add(new Boost(t, boost, (int)currentCoverage));
				}
				keepGoing = !receivers.stream().allMatch(r -> signalCalculator.hasSignal(r, newTransmitters));
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
