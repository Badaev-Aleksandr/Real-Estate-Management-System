package project;

import lombok.Getter;

import java.io.Serializable;


@Getter
public class Property implements Serializable {
    private static final long serialVersionUID = 1L;
    private String address; // адрес недвижимости
    private PropertyTyp propertyTyp; // тип недвижимости Enum
    private double price; //цена недвижимости
    private double size; // размер (кв. метры)
    private PropertyStatus statusOfProperty; // статус недвижимости

    public Property(String address, PropertyTyp propertyTyp, double price, double size,
                    PropertyStatus statusOfProperty) {
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

    @Override
    public String toString() {
        return "Property{" +
                "address='" + address + '\'' +
                ", propertyTyp=" + propertyTyp +
                ", price=" + price +
                ", size=" + size +
                ", statusOfProperty=" + statusOfProperty +
                '}';
    }
}
