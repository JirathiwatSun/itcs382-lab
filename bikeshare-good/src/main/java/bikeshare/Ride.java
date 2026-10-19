package bikeshare;

public final class Ride {
    private final String memberId;
    private final String bikeId;
    private final int minutes;
    private final int feeBaht;

    public Ride(String memberId, String bikeId, int minutes, int feeBaht) {
        this.memberId = memberId;
        this.bikeId = bikeId;
        this.minutes = minutes;
        this.feeBaht = feeBaht;
    }

    public String receipt() {
        return "Bike " + bikeId + ", " + minutes + " minutes, fee " + feeBaht + " baht";
    }

    public String toCsv() {
        return memberId + "," + bikeId + "," + minutes + "," + feeBaht;
    }
}
