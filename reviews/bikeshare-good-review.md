Files read: bikeshare-good/src/main/java/bikeshare/Main.java, bikeshare-good/src/main/java/bikeshare/MemberType.java, bikeshare-good/src/main/java/bikeshare/Station.java, bikeshare-good/src/main/java/bikeshare/Ride.java, bikeshare-good/src/main/java/bikeshare/RideStore.java, bikeshare-good/src/main/java/bikeshare/CsvRideStore.java, bikeshare-good/src/main/java/bikeshare/RentalService.java.

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | 1 | Main.java:15 | `System.out.println` is used for the command-line UI. | Note | Keep it; this is a console program. |
| 2 | 1 | CsvRideStore.java:18 | Save failures are printed with `System.out` instead of being returned to the caller. | Note | Keep for this short program, or return a result type if the menu should decide the message. |
| 3 | 1 | Main.java:14 | `Scanner` wrapping `System.in` is never closed. | Note | Leave it open; closing it would close `System.in`. |
| 4 | 2 | RentalService.java:8 | `RentalService` keeps the current loan (`memberId`, `memberType`, `bikeId`) together with the station map, so it is both a catalogue and a session. | Note | Extract a small `Loan` object if a second concurrent rental is ever needed. |
| 5 | 2 | Main.java:32 | Main parses member type and station names, then calls `RentalService` with those values (data / stamp coupling, not tighter). | Note | not found as a problem to fix at this size. |
| 6 | 2 | CsvRideStore.java:6 | File saving is behind `RideStore`; `RentalService` does not open files. | Note | not found: no content coupling between the service and the CSV format. |
| 7 | 2 | MemberType.java:4 | Fee rates live on the enum, so adding a visitor rate is a new constant rather than a new integer code. | Note | not found: connascence of convention on member type codes is absent. |
| 8 | 2 | Station.java:8 | `bikes` is private and never returned, so callers cannot mutate the deque. | Note | not found: no leaked internal collection. |
| 9 | 2 | RideStore.java:4 | `save(Ride ride)` is stamp coupling (whole Ride). | Note | Acceptable, because the store needs the CSV columns that Ride already formats. |

Number of findings for Check 1: 3 (all Note).
Number of findings for Check 2: 6, of which 4 are "not found" / acceptable notes and 1 optional session split.
