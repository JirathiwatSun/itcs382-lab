package parcelpoint;

public enum ParcelSize {
    SMALL(1, 10.0),
    MEDIUM(2, 15.0),
    LARGE(3, 20.0);

    private final int code;
    private final double extraDayFee;

    ParcelSize(int code, double extraDayFee) {
        this.code = code;
        this.extraDayFee = extraDayFee;
    }

    public int code() {
        return code;
    }

    public double extraDayFee() {
        return extraDayFee;
    }

    public boolean fitsIn(ParcelSize lockerSize) {
        return lockerSize.code >= this.code;
    }

    public static ParcelSize fromCode(int code) {
        for (ParcelSize size : values()) {
            if (size.code == code) {
                return size;
            }
        }
        throw new IllegalArgumentException("size must be 1, 2 or 3");
    }
}
