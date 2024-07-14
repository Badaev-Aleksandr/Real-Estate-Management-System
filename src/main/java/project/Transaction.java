package project;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private Property property;//объект недвижимости
    private Client client;// клиент
    private LocalDate localDate;// дата сделки
    private TransactionType transactionType;//тип сделки
    private double transactionAmount; // сумма сделки

    public Transaction(Property property, Client client, LocalDate localDate, TransactionType transactionType,
                       double transactionAmount) {
        this.property = property;
        this.client = client;
        this.localDate = localDate;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "property=" + property +
                ", client=" + client +
                ", localDate=" + localDate +
                ", transactionType=" + transactionType +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}
