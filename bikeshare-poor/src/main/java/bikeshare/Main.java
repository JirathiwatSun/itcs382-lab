package bikeshare;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static String[] stations = {"Library", "Canteen", "Dorm"};
    public static int[] available = {2, 2, 2};
    public static int nextBike = 1;
    public static String rentedBike;
    public static String rentedMember;
    public static int rentedType;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Commands: rent <memberId> <type 1=student 2=staff> <station 0=Library 1=Canteen 2=Dorm>, return <station> <minutes>, quit");
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] words = line.split("\\s+");
            if (words[0].equals("quit")) {
                break;
            }
            handle(words[0].equals("return"), words);
        }
    }

    static void handle(boolean returning, String[] words) {
        if (returning) {
            giveBack(words);
        } else if (words[0].equals("rent")) {
            rent(words);
        } else {
            System.out.println("Unknown command");
        }
    }

    static void rent(String[] words) {
        if (words.length < 4) {
            System.out.println("Usage: rent <memberId> <type> <station>");
            return;
        }
        if (rentedBike != null) {
            System.out.println("Already renting " + rentedBike);
            return;
        }
        int type = Integer.parseInt(words[2]);
        int station = Integer.parseInt(words[3]);
        if (type != 1 && type != 2) {
            System.out.println("Type must be 1 or 2");
            return;
        }
        if (station < 0 || station > 2) {
            System.out.println("Station must be 0, 1 or 2");
            return;
        }
        if (available[station] == 0) {
            System.out.println("No bike at " + stations[station]);
            return;
        }
        moveBike(station, true);
        rentedBike = "B" + nextBike;
        nextBike++;
        rentedMember = words[1];
        rentedType = type;
        System.out.println("Rented " + rentedBike + " from " + stations[station] + " for " + rentedMember);
    }

    static void giveBack(String[] words) {
        if (words.length < 3) {
            System.out.println("Usage: return <station> <minutes>");
            return;
        }
        if (rentedBike == null) {
            System.out.println("No bike on loan");
            return;
        }
        int station = Integer.parseInt(words[1]);
        int minutes = Integer.parseInt(words[2]);
        if (station < 0 || station > 2) {
            System.out.println("Station must be 0, 1 or 2");
            return;
        }
        int fee = fee(minutes, rentedType == 1);
        moveBike(station, false);
        saveRide(rentedBike, minutes, fee);
        System.out.println("Bike " + rentedBike + ", " + minutes + " minutes, fee " + fee + " baht");
        rentedBike = null;
        rentedMember = null;
        rentedType = 0;
    }

    static void moveBike(int station, boolean taking) {
        if (taking) {
            available[station]--;
        } else {
            available[station]++;
        }
    }

    static int fee(int minutes, boolean student) {
        if (student) {
            return minutes * 2;
        }
        return minutes * 3;
    }

    static void saveRide(String bike, int minutes, int fee) {
        try (FileWriter writer = new FileWriter("rides.csv", true)) {
            writer.write(bike + "," + minutes + "," + fee + "\n");
        } catch (IOException e) {
            System.out.println("Could not save ride: " + e.getMessage());
        }
    }
}
