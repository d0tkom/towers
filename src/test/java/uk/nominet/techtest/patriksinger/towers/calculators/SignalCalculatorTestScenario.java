package uk.nominet.techtest.patriksinger.towers.calculators;

import java.util.List;

import uk.nominet.techtest.patriksinger.towers.model.Receiver;
import uk.nominet.techtest.patriksinger.towers.model.Transmitter;

public class SignalCalculatorTestScenario {
    public final List<Transmitter> transmitters;
    public final Receiver receiver;
    public final boolean expectedResult;

    public SignalCalculatorTestScenario(List<Transmitter> transmitters, Receiver receiver, boolean expectedResult) {
        this.transmitters = transmitters;
        this.receiver = receiver;
        this.expectedResult = expectedResult;
    }
}
