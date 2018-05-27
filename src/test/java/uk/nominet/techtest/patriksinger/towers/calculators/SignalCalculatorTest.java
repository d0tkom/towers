package uk.nominet.techtest.patriksinger.towers.calculators;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static uk.nominet.techtest.patriksinger.towers.optimisers.TestScenarios.testScenarios;

public class SignalCalculatorTest {
    private final SignalCalculator subject = new SignalCalculator(new ChebyshevDistanceCalculator());

    @Test
    public void testScenario0() throws Exception {
        assertEquals(true, subject.hasSignal(testScenarios.get(2).scenario.receivers.get(0), testScenarios.get(2).scenario.transmitters));
    }
    
    @Test
    public void testScenario1() throws Exception {
        assertEquals(false, subject.hasSignal(testScenarios.get(2).scenario.receivers.get(1), testScenarios.get(2).scenario.transmitters));
    }
    
    @Test
    public void testScenario2() throws Exception {
        assertEquals(true, subject.hasSignal(testScenarios.get(2).scenario.receivers.get(2), testScenarios.get(2).scenario.transmitters));
    }
    
    @Test
    public void testScenario3() throws Exception {
        assertEquals(false, subject.hasSignal(testScenarios.get(4).scenario.receivers.get(5), testScenarios.get(4).scenario.transmitters));
    }
}