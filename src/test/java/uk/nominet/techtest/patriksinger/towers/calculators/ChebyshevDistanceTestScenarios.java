package uk.nominet.techtest.patriksinger.towers.calculators;

import com.google.common.collect.ImmutableList;
import uk.nominet.techtest.patriksinger.towers.model.*;

import java.util.List;

public abstract class ChebyshevDistanceTestScenarios {
    public static List<ChebyshevDistanceTestScenario> testScenarios = ImmutableList.of(
            new ChebyshevDistanceTestScenario(
                    new Point(0,0),
                    new Point(0,0),
                    0
            ),
            new ChebyshevDistanceTestScenario(
                    new Point(0,0),
                    new Point(0,10),
                    10
            ),
            new ChebyshevDistanceTestScenario(
                    new Point(0,0),
                    new Point(10,10),
                    10
            ),
            new ChebyshevDistanceTestScenario(
                    new Point(0,0),
                    new Point(10,0),
                    10
            ),
            new ChebyshevDistanceTestScenario(
                    new Point(0,0),
                    new Point(-10,10),
                    10
            ),
            new ChebyshevDistanceTestScenario(
                    new Point(0,0),
                    new Point(-10,0),
                    10
            ),
            new ChebyshevDistanceTestScenario(
                    new Point(0,0),
                    new Point(-10,-10),
                    10
            ),
            new ChebyshevDistanceTestScenario(
                    new Point(0,0),
                    new Point(0,-10),
                    10
            ),
            new ChebyshevDistanceTestScenario(
                    new Point(0,0),
                    new Point(10,-10),
                    10
            )
    );
}
