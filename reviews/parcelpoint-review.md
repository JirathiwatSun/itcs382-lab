Files read: parcelpoint/src/main/java/parcelpoint/Main.java, parcelpoint/src/main/java/parcelpoint/Parcel.java, parcelpoint/src/main/java/parcelpoint/Locker.java, parcelpoint/src/main/java/parcelpoint/ParcelService.java.

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | 1 | ParcelService.java:42 | `parcel.getCode() == code` compares String objects by reference, so a matching parcel is usually not found and `pickUp` returns 0. | Must fix | Compare with `parcel.getCode().equals(code)`. |
| 2 | 1 | ParcelService.java:76 | `FileWriter` is never closed or flushed, so `parcels.csv` may be empty or incomplete. | Must fix | Open the writer in try-with-resources so it is closed after the loop. |
| 3 | 1 | ParcelService.java:80 | Empty `catch (IOException e)` swallows save failures with no record of the error. | Must fix | Print or log `e.getMessage()` in the catch block. |
| 4 | 1 | ParcelService.java:6 | Unused import `java.util.Date`. | Should fix | Delete the import. |
| 5 | 1 | ParcelService.java:12 | Unused field `maxParcels`; nothing reads it. | Should fix | Remove the field. |
| 6 | 1 | Parcel.java:7 | Public field `status` can be changed by any class, so Parcel cannot protect legal values. | Should fix | Make `status` private and add a getter plus methods that set allowed values. |
| 7 | 1 | ParcelService.java:21 | `getLockers()` returns the live `lockers` list, so a caller can add or remove lockers. | Should fix | Return `List.copyOf(lockers)` or an unmodifiable view. |
| 8 | 1 | Main.java:9 | `System.out.println` is used for the command-line UI. | Note | Keep it here; this is a console program, not a library. |
| 9 | 1 | ParcelService.java:30 | SMS text is printed with `System.out` mixed into the store logic. | Note | Keep for this lab, or later move messaging out of `storeParcel`. |
| 10 | 2 | ParcelService.java:64 | `printParcels(boolean forStaff)` has logical cohesion: the boolean chooses two different reports. | Should fix | Split into `printWaiting()` and `printForStaff()`. |
| 11 | 2 | ParcelService.java:9 | Class `ParcelService` holds lockers, the parcel list, fees, console printing and file saving, so it represents more than one thing. | Should fix | Keep lockers and pick-up here; move file saving and report printing out in a later change. |
| 12 | 2 | ParcelService.java:28 | `parcel.status = "STORED"` writes another object's field (content coupling). | Should fix | Call a method on `Parcel`, for example `parcel.markStored()`. |
| 13 | 2 | ParcelService.java:44 | `parcel.status = "PICKED_UP"` is the same content coupling on pick-up. | Should fix | Call `parcel.markPickedUp()`. |
| 14 | 2 | Main.java:20 | `printParcels(true)` is control coupling: Main tells ParcelService *how* to print with a boolean. | Should fix | Call a staff-specific method with no flag. |
| 15 | 2 | Main.java:22 | Main reaches through `getLockers()` to `Locker::isFree` (Law of Demeter / strong coupling). | Should fix | Add `countFreeLockers()` on `ParcelService` and print that number. |
| 16 | 2 | Parcel.java:6 | Size is an `int` code (1/2/3) also used in `Locker.java:5` and `ParcelService.java:56`; this is connascence of convention. | Should fix | Replace the codes with an enum such as `ParcelSize`. |
| 17 | 2 | ParcelService.java:28 | Status strings `"STORED"` and `"PICKED_UP"` are magic values shared across classes (connascence of convention). | Should fix | Use an enum `ParcelStatus` inside `Parcel`. |
| 18 | 2 | Parcel.java:7 | Field `status` is not private. | Must fix | Make it private. |
| 19 | 2 | ParcelService.java:20 | `getLockers()` returns the internal collection so callers can change it. | Should fix | Return a copy, as in finding 7. |
| 20 | 2 | Main.java:6 | `main` has logical cohesion: one loop handles store, pickup, list, staff and quit. | Note | Acceptable for a short CLI; extract a small `handle(String[] words)` later if the menu grows. |
| 21 | 2 | Main.java:13 | Main constructs a full `Parcel` and passes it into `storeParcel` (stamp coupling). | Note | Fine at this size; only change if Parcel grows extra fields that store does not need. |

Number of findings for Check 1: 9.
Number of findings for Check 2: 12.
