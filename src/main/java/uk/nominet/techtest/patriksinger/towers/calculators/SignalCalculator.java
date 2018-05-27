package uk.nominet.techtest.patriksinger.towers.calculators;

import java.util.List;

import uk.nominet.techtest.patriksinger.towers.model.Receiver;
import uk.nominet.techtest.patriksinger.towers.model.Transmitter;

public class SignalCalculator {
	private final DistanceCalculator distanceCalculator;
	
	public SignalCalculator(DistanceCalculator calculator) {
		distanceCalculator = calculator;
	}

	public boolean hasSignal(Receiver receiver, List<Transmitter> transmitters) {
    	return transmitters.stream().anyMatch(t -> distanceCalculator.distance(receiver.location, t.location) - t.power <= 0);
    }
}