package uk.nominet.techtest.patriksinger.towers.calculators;

import uk.nominet.techtest.patriksinger.towers.model.Point;

public class ChebyshevDistanceCalculator implements DistanceCalculator {
	public ChebyshevDistanceCalculator() {
		
	}
	
	// Calculates the Chebyshev distance of two points
	@Override
	public int distance(Point p1, Point p2) {
		int xDiff = java.lang.Math.abs(p1.x - p2.x);
		int yDiff = java.lang.Math.abs(p1.y - p2.y);
		return java.lang.Math.max(xDiff, yDiff);
	}	
}