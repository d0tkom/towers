package uk.nominet.techtest.patriksinger.towers.optimisers;

import uk.nominet.techtest.patriksinger.towers.model.Result;
import uk.nominet.techtest.patriksinger.towers.model.Scenario;

public class TestScenario {
    public final Scenario scenario;
    public final Result expectedResult;

    public TestScenario(Scenario scenario, Result expectedResult) {
        this.scenario = scenario;
        this.expectedResult = expectedResult;
    }
}
