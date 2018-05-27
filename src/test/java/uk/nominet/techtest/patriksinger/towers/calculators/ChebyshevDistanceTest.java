package uk.nominet.techtest.patriksinger.towers.calculators;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static uk.nominet.techtest.patriksinger.towers.calculators.ChebyshevDistanceTestScenarios.testScenarios;

public class ChebyshevDistanceTest {
    private final DistanceCalculator subject = new ChebyshevDistanceCalculator();

    @Test
    public void testScenario0() throws Exception {
        assertEquals(testScenarios.get(0).expectedResult, subject.distance(testScenarios.get(0).pointA, testScenarios.get(0).pointB));
    }
    
    @Test
    public void testScenario1() throws Exception {
        assertEquals(testScenarios.get(1).expectedResult, subject.distance(testScenarios.get(1).pointA, testScenarios.get(1).pointB));
    }

    @Test
    public void testScenario2() throws Exception {
        assertEquals(testScenarios.get(2).expectedResult, subject.distance(testScenarios.get(2).pointA, testScenarios.get(2).pointB));
    }
    
    @Test
    public void testScenario3() throws Exception {
        assertEquals(testScenarios.get(3).expectedResult, subject.distance(testScenarios.get(3).pointA, testScenarios.get(3).pointB));
    }
    
    @Test
    public void testScenario4() throws Exception {
        assertEquals(testScenarios.get(4).expectedResult, subject.distance(testScenarios.get(4).pointA, testScenarios.get(4).pointB));
    }
    
    @Test
    public void testScenario5() throws Exception {
        assertEquals(testScenarios.get(5).expectedResult, subject.distance(testScenarios.get(5).pointA, testScenarios.get(5).pointB));
    }
    
    @Test
    public void testScenario6() throws Exception {
        assertEquals(testScenarios.get(6).expectedResult, subject.distance(testScenarios.get(6).pointA, testScenarios.get(6).pointB));
    }
    
    @Test
    public void testScenario7() throws Exception {
        assertEquals(testScenarios.get(7).expectedResult, subject.distance(testScenarios.get(7).pointA, testScenarios.get(7).pointB));
    }
    
    @Test
    public void testScenario8() throws Exception {
        assertEquals(testScenarios.get(8).expectedResult, subject.distance(testScenarios.get(8).pointA, testScenarios.get(8).pointB));
    }
}