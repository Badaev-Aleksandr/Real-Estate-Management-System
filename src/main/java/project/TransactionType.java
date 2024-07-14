package project;

public enum TransactionType {
    PURCHASE, SALE, RENT, NONE;

    //преобразуем String в Enum
    public static TransactionType fromStringTransactionTyp(String typ) {
        if (typ != null) {
            if (typ.equalsIgnoreCase(PURCHASE.toString())) {
                return PURCHASE;
            } else if (typ.equalsIgnoreCase(SALE.toString())) {
                return SALE;
            } else if (typ.equalsIgnoreCase(RENT.toString())) {
                return RENT;
            } else
                return NONE;
        }
        return null;
    }
}
