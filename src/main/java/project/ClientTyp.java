package project;

public enum ClientTyp {
    BAYER, SELLER, TENANT, NONE;

    public static ClientTyp fromStringClientTyp(String typ) {
        if (typ != null) {
            String cleanTyp = typ.trim();
            if (cleanTyp.equalsIgnoreCase(BAYER.toString())) {
                return BAYER;
            } else if (cleanTyp.equalsIgnoreCase(SELLER.toString())) {
                return SELLER;
            } else if (cleanTyp.equalsIgnoreCase(TENANT.toString())) {
                return TENANT;
            } else
                return NONE;
        }
        return null;
    }
}
