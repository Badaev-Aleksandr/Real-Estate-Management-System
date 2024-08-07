package project;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int id; // каждый созданный объект будет получать id номер рандом
    private String name; // Имя клиента
    private String contactDate; // контактные данные
    private ClientTyp clientTyp; // тип клиента (покупатель, продавец, арендатор).
    private static Set<Transaction> transactionsClientList = new HashSet<>();

    public Client(int id, String name, String contactDate, ClientTyp clientTyp) {
        this.id = id;
        this.name = name;
        this.contactDate = contactDate;
        this.clientTyp = clientTyp;
    }

    //метод добавления Транзакций Клиента в лист
    public void addTransactionToClientList(Transaction transaction) {
        boolean result = transactionsClientList.add(transaction);
        if (result)
            System.out.println("Добавил");
    }

    //вывод на экран всех транзакций клиента
    public void showAllClientTransaction() {
        if (transactionsClientList.isEmpty()) {
            System.out.println("Транзакций нет");
        }
        transactionsClientList.forEach(System.out::println);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactDate(String contactDate) {
        this.contactDate = contactDate;
    }

    public void setClientTyp(ClientTyp clientTyp) {
        this.clientTyp = clientTyp;
    }

    public static Set<Transaction> getTransactionsClientList() {
        return new HashSet<>(transactionsClientList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(contactDate, client.contactDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contactDate);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactDate='" + contactDate + '\'' +
                ", clientTyp=" + clientTyp +
                '}';
    }
}
