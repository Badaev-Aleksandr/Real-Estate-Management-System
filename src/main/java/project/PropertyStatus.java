package project;

public enum PropertyStatus {
    AVAILABLE, RESERVED, SOLD, NONE;

    public static PropertyStatus fromStringPropertyStatus(String typ) {
        if (typ != null) {
            String propertyStatus = typ.trim();
            if (propertyStatus.equalsIgnoreCase(AVAILABLE.toString())) {
                return AVAILABLE;
            } else if (propertyStatus.equalsIgnoreCase(RESERVED.toString())) {
                return RESERVED;
            } else if (propertyStatus.equalsIgnoreCase(SOLD.toString())) {
                return SOLD;
            } else
                return NONE;
        }
        return null;
    }
}
