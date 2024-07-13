package project;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name; // Имя клиента
    private String contactDate; // контактные данные
    private ClientTyp clientTyp; // тип клиента (покупатель, продавец, арендатор).

    public Client(String name, String contactDate, ClientTyp clientTyp) {
        this.name = name;
        this.contactDate = contactDate;
        this.clientTyp = clientTyp;
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
                "name='" + name + '\'' +
                ", contactDate='" + contactDate + '\'' +
                ", clientTyp=" + clientTyp +
                '}';
    }
}
