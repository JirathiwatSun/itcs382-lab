package bikeshare;

public enum MemberType {
    STUDENT(2),
    STAFF(3);

    private final int bahtPerMinute;

    MemberType(int bahtPerMinute) {
        this.bahtPerMinute = bahtPerMinute;
    }

    public int fee(int minutes) {
        return minutes * bahtPerMinute;
    }

    public static MemberType fromName(String text) {
        for (MemberType type : values()) {
            if (type.name().equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("member type must be student or staff");
    }
}
