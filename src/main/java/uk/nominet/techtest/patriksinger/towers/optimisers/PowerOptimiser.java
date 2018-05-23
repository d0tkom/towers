package uk.nominet.techtest.patriksinger.towers.optimisers;

import uk.nominet.techtest.patriksinger.towers.model.Result;
import uk.nominet.techtest.patriksinger.towers.model.Scenario;

public interface PowerOptimiser {
    Result optimise(Scenario scenario);
}
