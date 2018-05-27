package uk.nominet.techtest.patriksinger.towers.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Transmitter extends Tower {
    public final int power;

    public Transmitter(int id, Point location, int power) {
        super(id, location);
        this.power = power;
    }
    /*
    public int boostToCoverReceiver(Receiver receiver) {
    	return this.chebyshevDistance(receiver) - power;
    }
    
    public Receiver closestReceiver(List<Transmitter> transmitters, List<Receiver> receivers) {
    	Receiver closest = receivers.stream().min((r1, r2) -> Integer.compare(this.chebyshevDistance(r1) - power, this.chebyshevDistance(r2) - power)).get();
    	return closest;
    }
*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Transmitter that = (Transmitter) o;

        return power == that.power;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + power;
        return result;
    }

    @Override
    public String toString() {
        return "Transmitter{" +
                "power=" + power +
                ", id=" + id +
                ", location=" + location +
                '}';
    }
}
