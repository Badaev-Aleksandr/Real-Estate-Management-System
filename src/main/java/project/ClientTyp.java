package project;

public enum ClientTyp {
    BUYER, SELLER, LEASER, LESSOR, NONE;

    //преобразуем String в Enum
    public static ClientTyp fromStringClientTyp(String typ) {
        if (typ != null) {
            if (typ.equalsIgnoreCase(BUYER.toString())) {
                return BUYER;
            } else if (typ.equalsIgnoreCase(SELLER.toString())) {
                return SELLER;
            } else if (typ.equalsIgnoreCase(LEASER.toString())) {
                return LEASER;
            } else if (typ.equalsIgnoreCase(LESSOR.toString())) {
                return LESSOR;
            } else
                return NONE;
        }
        return null;
    }
}
