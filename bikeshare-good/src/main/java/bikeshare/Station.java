package bikeshare;

import java.util.ArrayDeque;
import java.util.Deque;

public class Station {
    private final String name;
    private final Deque<String> bikes = new ArrayDeque<>();

    public Station(String name, String... initialBikes) {
        this.name = name;
        for (String bike : initialBikes) {
            bikes.addLast(bike);
        }
    }

    public String name() {
        return name;
    }

    public boolean hasBike() {
        return !bikes.isEmpty();
    }

    public String takeBike() {
        return bikes.removeFirst();
    }

    public void parkBike(String bikeId) {
        bikes.addLast(bikeId);
    }
}
