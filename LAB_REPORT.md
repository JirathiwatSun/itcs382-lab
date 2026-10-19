# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/JirathiwatSun/itcs382-lab

## Tools

| Item | Version or name |
| --- | --- |
| JDK | 23.0.1 |
| Maven | 3.10.0 |
| IDE | Cursor 3.25.9 |
| SonarQube for IDE | 6.1.1 |
| AI tool and exact model | Cursor Grok 4.7 |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: `baac4ec`

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S106 | Main.java:9 | Replace this use of System.out by a logger. |
| 2 | java:S106 | Main.java:15 | Replace this use of System.out by a logger. |
| 3 | java:S106 | Main.java:17 | Replace this use of System.out by a logger. |
| 4 | java:S106 | Main.java:22 | Replace this use of System.out by a logger. |
| 5 | java:S2143 | ParcelService.java:1 | Use the "java.time" API for date and time. |
| 6 | java:S1128 | ParcelService.java:6 | Remove this unused import 'java.util.Date'. |
| 7 | java:S1068 | ParcelService.java:12 | Remove this unused "maxParcels" private field. |
| 8 | java:S106 | ParcelService.java:30 | Replace this use of System.out by a logger. |
| 9 | java:S4973 | ParcelService.java:42 | Strings and Boxed types should be compared using "equals()". |
| 10 | java:S106 | ParcelService.java:67 | Replace this use of System.out by a logger. |
| 11 | java:S106 | ParcelService.java:69 | Replace this use of System.out by a logger. |
| 12 | java:S2095 | ParcelService.java:76 | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 13 | java:S108 | ParcelService.java:80 | Remove this block of code, fill it in, or add a comment explaining why it is empty. |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 19 October 2026 |
| AI tool and exact model | Cursor Grok 4.7 |
| Commit reviewed | baac4ec |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Opened the project folder and read every Java file under parcelpoint/src/main/java |
| Output file in the reviews folder | reviews/parcelpoint-review.md |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | ParcelService.java:42 | `pickUp` compares parcel codes with `==` | both | java:S4973 | yes, because `store P1` then `pickup P1` printed Fee: 0.0 before the fix |
| 2 | ParcelService.java:76 | `FileWriter` is never closed | both | java:S2095 | yes, because parcels.csv was empty after the starter run |
| 3 | ParcelService.java:80 | Empty `catch (IOException e)` | both | java:S108 | yes, the catch block in the starter has no statements |
| 4 | ParcelService.java:6 | Unused import `java.util.Date` | both | java:S1128 | yes, no `Date` type is used in the file |
| 5 | ParcelService.java:12 | Unused field `maxParcels` | both | java:S1068 | yes, nothing reads the field |
| 6 | Parcel.java:7 | Public field `status` | AI | field not private / content coupling | yes, `ParcelService` writes `parcel.status` directly. SonarQube for IDE 6.1.1 did not report it |
| 7 | ParcelService.java:21 | `getLockers()` returns the live list | AI | leaked internal collection | yes, the method returns the field itself. SonarQube for IDE 6.1.1 did not report it |
| 8 | ParcelService.java:1 | Importing `java.util.Date` instead of `java.time` | SonarQube | java:S2143 | yes, the only date type in the file is the unused `java.util.Date` import |
| 9 | Main.java:9 | `System.out.println` for the command list | both | java:S106 | yes it prints, but it is the CLI, so I kept it |
| 10 | Main.java:15 | `System.out.println` for the locker number | SonarQube | java:S106 | yes, kept for the same CLI reason |
| 11 | Main.java:17 | `System.out.println` for the fee | SonarQube | java:S106 | yes, kept for the same CLI reason |
| 12 | Main.java:22 | `System.out.println` for free lockers | SonarQube | java:S106 | yes, kept for the same CLI reason |
| 13 | ParcelService.java:30 | `System.out.println` for the SMS line | both | java:S106 | yes, kept because the program has no other way to show the SMS |
| 14 | ParcelService.java:67 | `System.out.println` in the staff listing | SonarQube | java:S106 | yes, kept for the CLI |
| 15 | ParcelService.java:69 | `System.out.println` in the waiting listing | SonarQube | java:S106 | yes, kept for the CLI |
| 16 | ParcelService.java:64 | `printParcels(boolean forStaff)` does two jobs | AI | logical cohesion / control coupling | yes, the boolean chooses two different reports |
| 17 | ParcelService.java:9 | `ParcelService` stores parcels, prices them, prints and saves a file | AI | weak class cohesion | yes, those are separate responsibilities |
| 18 | ParcelService.java:28 | `parcel.status = "STORED"` writes another object's field | AI | content coupling | yes, `status` was public and set from outside |
| 19 | Main.java:22 | Main reaches through `getLockers()` to `Locker::isFree` | AI | strong coupling | yes, Main knows Locker's method instead of asking ParcelService |
| 20 | Parcel.java:6 | Size is an `int` code 1/2/3 also used by Locker and `fee` | AI | connascence of convention | yes, three places must agree on the same numbers |
| 21 | ParcelService.java:28 | Status strings `"STORED"` and `"PICKED_UP"` are magic values | AI | connascence of convention | yes, the same literals appear in store, pick-up and printing |
| 22 | Main.java:6 | `main` has logical cohesion because it handles every command | AI | logical cohesion | no, too vague: a short CLI `main` is supposed to dispatch commands |

2.1 Name two problems that only the AI review found.

`printParcels(boolean forStaff)` has logical cohesion and control coupling, and the `int` size codes are connascence of convention between `Parcel`, `Locker` and `fee`.

2.2 Name one problem that only SonarQube found, or write none.

`ParcelService.java:1`, rule `java:S2143`, "Use the java.time API for date and time." The AI review only called the `Date` import unused. It did not mention `java.time`.

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.

Finding 22, that `main` has logical cohesion, is too vague. I opened `Main.java` at line 6: the method's job is to read commands and call `ParcelService`, which is what a CLI entry point does. I checked the other AI rows the same way, by opening the file and line, and I ran the starter with `store P1 6501 2`, `pickup P1 5` and `staff` to confirm the `==` bug and the empty CSV.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | kept | Printing is the output of a command-line program. |
| 2 | kept | Printing is the output of a command-line program. |
| 3 | kept | Printing is the output of a command-line program. |
| 4 | kept | Printing is the output of a command-line program. |
| 5 | fixed | d23db7e removes the unused `java.util.Date` import. Its message says "Close FileWriter"; that change is in f2bdecd. |
| 6 | fixed | d23db7e removes the unused import. Same message mismatch as row 5. |
| 7 | fixed | d23db7e removes the unused `maxParcels` field. Same message mismatch as row 5. |
| 8 | kept | The SMS line is user-visible output in this CLI lab program. |
| 9 | fixed | f2bdecd changes `==` to `equals`. |
| 10 | kept | Printing is the output of a command-line program. |
| 11 | kept | Printing is the output of a command-line program. |
| 12 | fixed | f2bdecd closes the `FileWriter` with try-with-resources. The message only mentions the string comparison. |
| 13 | fixed | f2bdecd fills the empty catch. Same commit as row 12. |

Number of SonarQube issues that remain after your fixes: 8 (all `java:S106`, including the new save-error print)

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| Connascence of convention | Replaced the size codes 1/2/3 with enum `ParcelSize`, so Parcel, Locker and `fee` share one type | 256c19c |
| Logical cohesion / control coupling | Split `printParcels(boolean)` into `printWaiting()` and `printForStaff()` | b031665 |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text
Commands: store <code> <studentId> <size 1-3>, pickup <code> <days>, list, staff, quit
SMS to 6501: parcel P1 is in locker 2
Locker 2
Fee: 30.0
P1 6501 PICKED_UP
Free lockers: 3
```

parcels.csv:

```text
P1,PICKED_UP
```

Before the fixes the same commands printed `Fee: 0.0`, left the parcel `STORED`, showed 2 free lockers, and wrote an empty CSV.

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | 19 October 2026 | 19 October 2026 |
| AI tool and exact model | Cursor Grok 4.7 | Cursor Grok 4.7 |
| Prompt (B or C as given, or your changed prompt in full) | Prompt B as given | Prompt C as given |
| Builds with mvn package (yes or no) | yes | yes |
| Number of classes, interfaces, enums and records | 1 class | 6 classes, 1 interface, 1 enum |
| SonarQube issues, total | 16 | 4 |
| SonarQube issues, by rule | java:S106 (13), java:S2386 (2), java:S135 (1) | java:S106 (3), java:S135 (1) |
| AI review findings in Check 1 | 5 | 3 |
| AI review findings in Check 2 | 10 | 6, mostly notes / not found |
| Review output file in the reviews folder | reviews/bikeshare-poor-review.md | reviews/bikeshare-good-review.md |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

No. It reported `java:S2386`, "Make this member \"protected\".", on the two public static arrays, `java:S106`, "Replace this use of System.out by a logger.", and `java:S135`, "Reduce the total number of break and continue statements in this loop to use at most one." None of those messages is about cohesion or coupling.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

Yes. For bikeshare-poor it reported common coupling on the public static fields in `Main.java:8`, and connascence of convention on the member-type `int` codes in `Main.java:13`. Neither finding appears in the good review, which uses private fields and `MemberType`.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

bikeshare-good. I would add `VISITOR(4)` to `MemberType` and the menu would accept the new name through `fromName`. In bikeshare-poor I would have to add a new `int` code, change `handle`/`fee`'s boolean, and update every place that still assumes type is only 1 or 2.

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

SonarQube for IDE checks local code quality. On the starter it reported the unused field (`java:S1068`), `==` on strings (`java:S4973`), the unclosed `FileWriter` (`java:S2095`) and the old `Date` import (`java:S2143`). It does not check design quality in the course sense. It did not report the public `status` field or `getLockers()` returning its list, and it never said that `printParcels(boolean)` has control coupling or that the size codes are connascence of convention.

5.2 When should you not trust the AI review? Give an example from this lab.

Do not trust a design label until you open the line and can point to the code. The review called `main` logically cohesive, but that method only dispatches CLI commands. I also checked claims about bugs by running the program: the AI was right about `==` and the unclosed writer, because the starter printed `Fee: 0.0` and left `parcels.csv` empty.

5.3 What will you do differently in Project Phase 2 after this lab?

I will run SonarQube for IDE on every Java file and keep a table of rule, file, line and whether I fixed or kept it. I will then run Prompt A and compare the two lists, and I will treat cohesion, coupling and connascence as a separate pass that SonarQube will not do for me. For new code I will start from the good BikeShare shape: one job per class, private fields, enums instead of number codes, and file I/O behind an interface.

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| Cursor Grok 4.7 | Prompt A review of the ParcelPoint starter | reviews/parcelpoint-review.md | Opened each cited line; ran store/pickup/staff on the starter |
| Cursor Grok 4.7 | Prompt B poor BikeShare program | bikeshare-poor/pom.xml and bikeshare-poor/src/main/java/bikeshare/Main.java | `mvn package`, then `rent M1 1 0` and `return 1 10` |
| Cursor Grok 4.7 | Prompt C good BikeShare program | bikeshare-good/pom.xml and every Java file under bikeshare-good/src/main/java/bikeshare | `mvn package`, then `rent M1 student Library` and `return Canteen 10` |
| Cursor Grok 4.7 | Prompt A reviews of both BikeShare versions | reviews/bikeshare-poor-review.md, reviews/bikeshare-good-review.md | Compared each finding with the Java file and line |
| Cursor Grok 4.7 | ParcelPoint fixes for Tasks 1 and 3 | parcelpoint Java files after commit baac4ec | `mvn package` and the same store/pickup/staff script, which then printed Fee: 30.0 and wrote P1,PICKED_UP |
