package bikeshare;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, Station> stations = new LinkedHashMap<>();
        stations.put("library", new Station("Library", "L1", "L2"));
        stations.put("canteen", new Station("Canteen", "C1", "C2"));
        stations.put("dorm", new Station("Dorm", "D1", "D2"));
        RentalService service = new RentalService(stations, new CsvRideStore("rides.csv"));
        Scanner in = new Scanner(System.in);
        System.out.println("Commands: rent <memberId> <student|staff> <Library|Canteen|Dorm>, return <station> <minutes>, quit");
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] words = line.split("\\s+");
            if (words[0].equals("quit")) {
                break;
            }
            System.out.println(run(service, words));
        }
    }

    private static String run(RentalService service, String[] words) {
        try {
            if (words[0].equals("rent") && words.length >= 4) {
                return service.rent(words[1], MemberType.fromName(words[2]), words[3]);
            }
            if (words[0].equals("return") && words.length >= 3) {
                return service.giveBack(words[1], Integer.parseInt(words[2]));
            }
            return "Unknown command";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
