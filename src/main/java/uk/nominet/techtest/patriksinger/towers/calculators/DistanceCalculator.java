package uk.nominet.techtest.patriksinger.towers.calculators;

import uk.nominet.techtest.patriksinger.towers.model.Point;

public interface DistanceCalculator {
    int distance(Point p1, Point p2);
}