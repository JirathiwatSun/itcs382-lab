package parcelpoint;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParcelService {
    private List<Locker> lockers = new ArrayList<>();
    private List<Parcel> parcels = new ArrayList<>();

    public ParcelService() {
        lockers.add(new Locker(1, ParcelSize.SMALL));
        lockers.add(new Locker(2, ParcelSize.MEDIUM));
        lockers.add(new Locker(3, ParcelSize.LARGE));
    }

    public List<Locker> getLockers() {
        return List.copyOf(lockers);
    }

    public Locker storeParcel(Parcel parcel) {
        for (Locker locker : lockers) {
            if (locker.isFree() && parcel.getSize().fitsIn(locker.getSize())) {
                locker.setParcel(parcel);
                parcel.setStatus("STORED");
                parcels.add(parcel);
                System.out.println("SMS to " + parcel.getStudentId() + ": parcel "
                        + parcel.getCode() + " is in locker " + locker.getNumber());
                saveToFile();
                return locker;
            }
        }
        return null;
    }

    public double pickUp(String code, int days) {
        for (Locker locker : lockers) {
            Parcel parcel = locker.getParcel();
            if (parcel != null && parcel.getCode().equals(code)) {
                locker.setParcel(null);
                parcel.setStatus("PICKED_UP");
                saveToFile();
                return fee(parcel, days);
            }
        }
        return 0;
    }

    public double fee(Parcel parcel, int days) {
        if (days <= 3) {
            return 0;
        }
        return (days - 3) * parcel.getSize().extraDayFee();
    }

    public void printWaiting() {
        for (Parcel parcel : parcels) {
            if (parcel.getStatus().equals("STORED")) {
                System.out.println(parcel.getCode() + " is waiting");
            }
        }
    }

    public void printForStaff() {
        for (Parcel parcel : parcels) {
            System.out.println(parcel.getCode() + " " + parcel.getStudentId() + " " + parcel.getStatus());
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter("parcels.csv")) {
            for (Parcel parcel : parcels) {
                writer.write(parcel.getCode() + "," + parcel.getStatus() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Could not save parcels: " + e.getMessage());
        }
    }
}
