package project;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Transaction implements Serializable{
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(transactionAmount, that.transactionAmount) == 0 && Objects.equals(property, that.property)
                && Objects.equals(client, that.client) && Objects.equals(localDate, that.localDate) && transactionType == that.transactionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(property, client, localDate, transactionType, transactionAmount);
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
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
