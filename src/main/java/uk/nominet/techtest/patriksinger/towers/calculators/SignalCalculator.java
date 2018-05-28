package uk.nominet.techtest.patriksinger.towers.calculators;

import java.util.List;

import uk.nominet.techtest.patriksinger.towers.model.Receiver;
import uk.nominet.techtest.patriksinger.towers.model.Transmitter;

// This SignalCalculator helps determine if a receiver has signal based on given transmitters, and
// based on a given distance calculation method
public class SignalCalculator {
	private final DistanceCalculator distanceCalculator;
	
	// To create the object we need to specify a distance calculation method for the object to use
	public SignalCalculator(DistanceCalculator calculator) {
		distanceCalculator = calculator;
	}

	// Checks if a given receiver is covered at least by one transmitter
	public boolean hasSignal(Receiver receiver, List<Transmitter> transmitters) {
    	return transmitters.stream().anyMatch(t -> distanceCalculator.distance(receiver.location, t.location) - t.power <= 0);
    }
}