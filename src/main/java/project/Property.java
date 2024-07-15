package project;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class Property implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int id; // айди недвижимости будет присвоен рандомно каждому объекту
    private String address; // адрес недвижимости
    private PropertyTyp propertyTyp; // тип недвижимости Enum
    private double price; //цена недвижимости
    private double size; // размер (кв. метры)
    private PropertyStatus statusOfProperty; // статус недвижимости

    public Property(int id, String address, PropertyTyp propertyTyp, double price, double size,
                    PropertyStatus statusOfProperty) {
        this.id = id;
        this.address = address;
        this.propertyTyp = propertyTyp;
        this.price = price;
        this.size = size;
        this.statusOfProperty = statusOfProperty;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatusOfProperty(PropertyStatus statusOfProperty) {
        this.statusOfProperty = statusOfProperty;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setPropertyTyp(PropertyTyp propertyTyp) {
        this.propertyTyp = propertyTyp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Double.compare(price, property.price) == 0 && Double.compare(size, property.size) == 0 &&
                Objects.equals(address, property.address) && propertyTyp == property.propertyTyp &&
                statusOfProperty == property.statusOfProperty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, propertyTyp, price, size, statusOfProperty);
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", propertyTyp=" + propertyTyp +
                ", price=" + price +
                ", size=" + size +
                ", statusOfProperty=" + statusOfProperty +
                '}';
    }
}
