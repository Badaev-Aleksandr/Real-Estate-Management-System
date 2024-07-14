package project;

public enum ClientTyp {
    BAYER, SELLER, TENANT, NONE;

    //преобразуем String в Enum
    public static ClientTyp fromStringClientTyp(String typ) {
        if (typ != null) {
            if (typ.equalsIgnoreCase(BAYER.toString())) {
                return BAYER;
            } else if (typ.equalsIgnoreCase(SELLER.toString())) {
                return SELLER;
            } else if (typ.equalsIgnoreCase(TENANT.toString())) {
                return TENANT;
            } else
                return NONE;
        }
        return null;
    }
}
