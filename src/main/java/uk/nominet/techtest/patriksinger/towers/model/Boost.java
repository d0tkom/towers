package uk.nominet.techtest.patriksinger.towers.model;

// This class defines a possible boost (extra power) given to a transmitter, with the new coverage it would give,
// helping us to determine the cost effectiveness of the change
public class Boost {
    public final Transmitter transmitter;
    public final int boost;
    public final int newCoverage;

    public Boost(Transmitter t, int boost, int newCoverage) {
        this.transmitter = t;
        this.boost = boost;
        this.newCoverage = newCoverage;
    }
    
    // This function simply returns the ratio of the given boost / newCoverage to easily determine the
    // cost effectiveness of boost
    public double boostPerCoverage() {
    	return (double)boost / (double)newCoverage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Boost boost = (Boost) o;

        if (!transmitter.equals(boost.transmitter)) return false;
        if (this.boost != boost.boost) return false;
        return this.newCoverage == boost.newCoverage;
    }

    @Override
    public int hashCode() {
        int result = boost;
        result = 31 * result + newCoverage + transmitter.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Boost{" +
                "transmitter=" + transmitter.toString() +
                ", boost=" + boost +
                ", newCoverage=" + newCoverage +
                '}';
    }
}
