package project;

public enum PropertyTyp {
    APARTMENT, HOUSE, COMMERCIAL, NONE;

    //преобразуем String в Enum
    public static PropertyTyp fromStringPropertyTyp(String typ) {
        if (typ != null) {
            if (typ.equalsIgnoreCase(APARTMENT.toString())) {
                return APARTMENT;
            } else if (typ.equalsIgnoreCase(HOUSE.toString())) {
                return HOUSE;
            } else if (typ.equalsIgnoreCase(COMMERCIAL.toString())) {
                return COMMERCIAL;
            } else
                return NONE;
        }
        return null;
    }
}
