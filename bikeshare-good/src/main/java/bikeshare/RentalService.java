package bikeshare;

import java.util.Map;

public class RentalService {
    private final Map<String, Station> stations;
    private final RideStore rideStore;
    private String memberId;
    private MemberType memberType;
    private String bikeId;

    public RentalService(Map<String, Station> stations, RideStore rideStore) {
        this.stations = stations;
        this.rideStore = rideStore;
    }

    public String rent(String memberId, MemberType memberType, String stationName) {
        if (bikeId != null) {
            return "Already renting " + bikeId;
        }
        Station station = stationNamed(stationName);
        if (station == null) {
            return "Unknown station";
        }
        if (!station.hasBike()) {
            return "No bike at " + station.name();
        }
        this.memberId = memberId;
        this.memberType = memberType;
        this.bikeId = station.takeBike();
        return "Rented " + bikeId + " from " + station.name() + " for " + memberId;
    }

    public String giveBack(String stationName, int minutes) {
        if (bikeId == null) {
            return "No bike on loan";
        }
        Station station = stationNamed(stationName);
        if (station == null) {
            return "Unknown station";
        }
        Ride ride = new Ride(memberId, bikeId, minutes, memberType.fee(minutes));
        station.parkBike(bikeId);
        rideStore.save(ride);
        bikeId = null;
        memberId = null;
        memberType = null;
        return ride.receipt();
    }

    private Station stationNamed(String stationName) {
        return stations.get(stationName.toLowerCase());
    }
}
