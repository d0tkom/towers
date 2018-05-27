package uk.nominet.techtest.patriksinger.towers.calculators;

import uk.nominet.techtest.patriksinger.towers.model.Point;

public class ChebyshevDistanceTestScenario {
    public final Point pointA;
    public final Point pointB;
    public final int expectedResult;

    public ChebyshevDistanceTestScenario(Point pointA, Point pointB, int expectedResult) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.expectedResult = expectedResult;
    }
}
