Files read: bikeshare-poor/src/main/java/bikeshare/Main.java.

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | 1 | Main.java:8 | Public static fields `stations` and `available` hold the whole system's data, so any code can overwrite them. | Must fix | Make the fields private instance fields of a small service class. |
| 2 | 1 | Main.java:31 | `handle(boolean returning, String[] words)` uses a boolean flag to choose rent vs return, which is easy to call the wrong way. | Should fix | Use two methods, `rent` and `giveBack`, and dispatch on the command word only. |
| 3 | 1 | Main.java:17 | `System.out.println` is used for the command-line UI. | Note | Keep it; this is a console program. |
| 4 | 1 | Main.java:96 | `moveBike` changes what it does from a boolean `taking`. | Should fix | Use `takeBike(station)` and `parkBike(station)`. |
| 5 | 1 | Main.java:104 | `fee(int minutes, boolean student)` encodes member type as a boolean, so a third type cannot be added cleanly. | Should fix | Replace the flag with an enum of member types. |
| 6 | 2 | Main.java:7 | Class `Main` holds the menu, station stock, fee rules, rental state and CSV saving, so it represents more than one thing. | Must fix | Split into Main (menu), a rental service, a fee policy and a file store. |
| 7 | 2 | Main.java:15 | `main` has logical cohesion: one loop handles rent, return and quit. | Note | Acceptable for a short CLI once the rules move out of this class. |
| 8 | 2 | Main.java:31 | `handle` has logical cohesion: the boolean selects unrelated operations. | Must fix | Delete `handle` and call `rent` or `giveBack` directly. |
| 9 | 2 | Main.java:96 | `moveBike` has logical cohesion: take and park are opposite operations chosen by a flag. | Should fix | Split into two methods. |
| 10 | 2 | Main.java:104 | `fee` has logical cohesion: student and staff prices are selected by a boolean (control coupling). | Should fix | Give each member type its own rate, for example on an enum. |
| 11 | 2 | Main.java:8 | All methods share `public static` station and rental fields (common coupling). | Must fix | Pass a service object, or keep the data as private instance fields. |
| 12 | 2 | Main.java:13 | Member type is an `int` code `1` or `2`, which is connascence of convention with `fee` and the help text. | Must fix | Use an enum `MemberType` with the baht-per-minute on it. |
| 13 | 2 | Main.java:51 | Station is an `int` index `0/1/2`, which is connascence of convention with the `stations` array. | Should fix | Use station names, or an enum, as the public command. |
| 14 | 2 | Main.java:8 | Fields are not private. | Must fix | Make every field private. |
| 15 | 2 | Main.java:8 | `stations` and `available` are public arrays, so callers can change the internal stock. | Must fix | Stop exposing the arrays; hide them behind methods. |

Number of findings for Check 1: 5.
Number of findings for Check 2: 10.
