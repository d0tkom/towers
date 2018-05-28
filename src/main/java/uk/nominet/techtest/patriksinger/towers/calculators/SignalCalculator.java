package uk.nominet.techtest.patriksinger.towers.calculators;

import java.util.List;
import java.util.stream.Collectors;

import uk.nominet.techtest.patriksinger.towers.model.Receiver;
import uk.nominet.techtest.patriksinger.towers.model.Transmitter;

// This SignalCalculator class includes helper methods to determine the receivers
// with or without signal using given transmitters
public class SignalCalculator {
	private final DistanceCalculator distanceCalculator;
	
	// To create the object we need to specify a distance calculator for the object to use
	public SignalCalculator(DistanceCalculator calculator) {
		distanceCalculator = calculator;
	}

	// Checks if a given receiver is covered at least by one transmitter
	public boolean hasSignal(List<Transmitter> transmitters, Receiver receiver) {
		return transmitters.stream().anyMatch(t -> distanceCalculator.distance(receiver.location, t.location) - t.power <= 0);
	}
	
	public boolean fullCoverage(List<Transmitter> transmitters, List<Receiver> receivers) {
		return receivers.stream().allMatch(r -> hasSignal(transmitters, r));
	}
	
	public List<Receiver> receiversWithoutSignal(List<Transmitter> transmitters, List<Receiver> receivers) {
		return receivers.stream().filter(r -> !hasSignal(transmitters, r)).collect(Collectors.toList());
	}
}