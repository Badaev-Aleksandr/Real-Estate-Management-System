package project;

public enum TransactionType {
    PURCHASE, SALE, RENT, NONE;

    public static TransactionType fromStringTransactionTyp(String typ) {
        if (typ != null) {
            String cleanTyp = typ.trim();
            if (cleanTyp.equalsIgnoreCase(PURCHASE.toString())) {
                return PURCHASE;
            } else if (cleanTyp.equalsIgnoreCase(SALE.toString())) {
                return SALE;
            } else if (cleanTyp.equalsIgnoreCase(RENT.toString())) {
                return RENT;
            } else
                return NONE;
        }
        return null;
    }
}
