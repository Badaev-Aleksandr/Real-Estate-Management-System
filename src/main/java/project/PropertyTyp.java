package project;

public enum PropertyTyp {
    APARTMENT, HOUSE, COMMERCIAL, NONE;

    public static PropertyTyp fromStringPropertyTyp(String typ) {
        if (typ != null) {
            String propertyTyp = typ.trim();
            if (propertyTyp.equalsIgnoreCase(APARTMENT.toString())) {
                return APARTMENT;
            } else if (propertyTyp.equalsIgnoreCase(HOUSE.toString())) {
                return HOUSE;
            } else if (propertyTyp.equalsIgnoreCase(COMMERCIAL.toString())) {
                return COMMERCIAL;
            } else
                return NONE;
        }
        return null;
    }
}
