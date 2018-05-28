package uk.nominet.techtest.patriksinger.towers.calculators;

import uk.nominet.techtest.patriksinger.towers.model.Point;

// Interface for DistanceCalculators to implement. 
// It was created in case we would need to calculate distance of two points in different ways.
public interface DistanceCalculator {
    int distance(Point p1, Point p2);
}