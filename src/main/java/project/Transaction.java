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
    private final int id;
    private Property property;//объект недвижимости
    private Client client;// клиент
    private LocalDate localDate;// дата сделки
    private TransactionType transactionType;//тип сделки
    private double transactionAmount; // сумма сделки

    public Transaction(int id, Property property, Client client, LocalDate localDate,
                       TransactionType transactionType, double transactionAmount) {
        this.id = id;
        this.property = property;
        this.client = client;
        this.localDate = localDate;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(transactionAmount, that.transactionAmount) == 0 && Objects.equals(property, that.property) && Objects.equals(client, that.client) && Objects.equals(localDate, that.localDate) && transactionType == that.transactionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(property, client, localDate, transactionType, transactionAmount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", property=" + property +
                ", client=" + client +
                ", localDate=" + localDate +
                ", transactionType=" + transactionType +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}
