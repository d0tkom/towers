package uk.nominet.techtest.patriksinger.towers.calculators;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import uk.nominet.techtest.patriksinger.towers.model.Point;
import uk.nominet.techtest.patriksinger.towers.model.Receiver;

import static org.junit.Assert.assertEquals;
import static uk.nominet.techtest.patriksinger.towers.optimisers.TestScenarios.testScenarios;

// No TestScenarios defined here as we just use the test scenarios from the optimisers
public class SignalCalculatorTest {
    private final SignalCalculator subject = new SignalCalculator(new ChebyshevDistanceCalculator());

    @Test
    public void testScenario0() throws Exception {
        assertEquals(true, subject.hasSignal(testScenarios.get(2).scenario.transmitters, testScenarios.get(2).scenario.receivers.get(0)));
    }
    
    @Test
    public void testScenario1() throws Exception {
        assertEquals(false, subject.hasSignal(testScenarios.get(2).scenario.transmitters, testScenarios.get(2).scenario.receivers.get(1)));
    }
    
    @Test
    public void testScenario2() throws Exception {
        assertEquals(true, subject.hasSignal(testScenarios.get(2).scenario.transmitters, testScenarios.get(2).scenario.receivers.get(2)));
    }
    
    @Test
    public void testScenario3() throws Exception {
        assertEquals(false, subject.hasSignal(testScenarios.get(4).scenario.transmitters, testScenarios.get(4).scenario.receivers.get(5)));
    }
    
    @Test
    public void testScenario4() throws Exception {
        assertEquals(false, subject.fullCoverage(testScenarios.get(0).scenario.transmitters, testScenarios.get(0).scenario.receivers));
    }
    
    @Test
    public void testScenario5() throws Exception {
        assertEquals(false, subject.fullCoverage(testScenarios.get(1).scenario.transmitters, testScenarios.get(1).scenario.receivers));
    }
    
    @Test
    public void testScenario6() throws Exception {
        assertEquals(false, subject.fullCoverage(testScenarios.get(2).scenario.transmitters, testScenarios.get(2).scenario.receivers));
    }
    
    @Test
    public void testScenario7() throws Exception {
        assertEquals(false, subject.fullCoverage(testScenarios.get(3).scenario.transmitters, testScenarios.get(3).scenario.receivers));
    }
    
    @Test
    public void testScenario8() throws Exception {
        assertEquals(false, subject.fullCoverage(testScenarios.get(4).scenario.transmitters, testScenarios.get(4).scenario.receivers));
    }
    
    @Test
    public void testScenario9() throws Exception {
        assertEquals(
        		testScenarios.get(3).scenario.receivers, 
        		subject.receiversWithoutSignal(testScenarios.get(3).scenario.transmitters, testScenarios.get(3).scenario.receivers)
        );
    }
    
    @Test
    public void testScenario10() throws Exception {
        assertEquals(
        		testScenarios.get(4).scenario.receivers, 
        		subject.receiversWithoutSignal(testScenarios.get(4).scenario.transmitters, testScenarios.get(4).scenario.receivers)
        );
    }
    
    @Test
    public void testScenario11() throws Exception {
        assertEquals(
        		ImmutableList.of(
        				new Receiver(2, new Point(8, 8))
                ),
        		subject.receiversWithoutSignal(testScenarios.get(2).scenario.transmitters, testScenarios.get(2).scenario.receivers)
        );
    }
}