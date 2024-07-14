package project;

public enum PropertyStatus {
    AVAILABLE, RESERVED, SOLD, NONE;

    //преобразуем String в Enum
    public static PropertyStatus fromStringPropertyStatus(String typ) {
        if (typ != null) {
            if (typ.equalsIgnoreCase(AVAILABLE.toString())) {
                return AVAILABLE;
            } else if (typ.equalsIgnoreCase(RESERVED.toString())) {
                return RESERVED;
            } else if (typ.equalsIgnoreCase(SOLD.toString())) {
                return SOLD;
            } else
                return NONE;
        }
        return null;
    }
}
