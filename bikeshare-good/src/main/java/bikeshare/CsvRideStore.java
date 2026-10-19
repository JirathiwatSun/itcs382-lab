package bikeshare;

import java.io.FileWriter;
import java.io.IOException;

public class CsvRideStore implements RideStore {
    private final String path;

    public CsvRideStore(String path) {
        this.path = path;
    }

    @Override
    public void save(Ride ride) {
        try (FileWriter writer = new FileWriter(path, true)) {
            writer.write(ride.toCsv() + "\n");
        } catch (IOException e) {
            System.out.println("Could not save ride: " + e.getMessage());
        }
    }
}
