package project;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class PropertyManager {
    private static final File textFile = Path.of("src", "main", "resources", "property_base.txt").toFile();
    private static final File objectFile = Path.of("src", "main", "resources", "property_base.ser").toFile();
    private static Scanner scanner = new Scanner(System.in);
    private static List<Property> propertyList = new ArrayList<>(readObjectFile(objectFile));

    // Добавить объект недвижимости
    public static void addNewProperty() {
        System.out.println("Вы выбрали функцию Добавить объект недвижимости в базу данных!");
        System.out.println("Введите адрес недвижимости: ");
        String address = scanner.nextLine();
        System.out.println("Введите тип недвижимости: (APARTMENT,HOUSE,COMMERCIAL).");
        PropertyTyp propertyTyp = PropertyTyp.fromStringPropertyTyp(scanner.nextLine());
        while (propertyTyp == PropertyTyp.NONE) {
            System.out.println("Вы указали неправильное значение Тип Недвижимости!");
            System.out.println("Введите тип недвижимости как указано в скобках (APARTMENT,HOUSE,COMMERCIAL).");
            propertyTyp = PropertyTyp.fromStringPropertyTyp(scanner.nextLine());
        }
        System.out.println("Введите цену недвижимости в $: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Введено неверное значение цены!");
            System.out.println("Введите цену цифрами пример: (99,99)");
            scanner.next();
        }
        double price = scanner.nextDouble();
        System.out.println("Введите размер (кв. метры): ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Введено неверное значение цены!");
            System.out.println("Введите цену цифрами пример: (99.99)");
            scanner.next();
        }
        double size = scanner.nextDouble();
        System.out.println("Введите статус недвижимости (AVAILABLE, RESERVED, SOLD)");
        PropertyStatus propertyStatus = PropertyStatus.fromStringPropertyStatus(scanner.next());
        while (propertyStatus == PropertyStatus.NONE) {
            System.out.println("Вы указали неправильное значение Статус Недвижимости!");
            System.out.println("Введите статус недвижимости как указано в скобках (AVAILABLE, RESERVED, SOLD).");
            propertyStatus = PropertyStatus.fromStringPropertyStatus(scanner.next());
        }
        Property property = new Property(address.trim(), propertyTyp, price, size, propertyStatus);
        if (!isPropertyAvailability(property)) {
            propertyList.add(property);
            log.info("Недвижимость по адресу {} успешно добавлена в список.", property.getAddress());
            saveNewPropertyInTextFile(property);
        } else {
            System.out.println("Не добавлена! В базе данных есть такая недвижимость. " + property);
            log.info("Недвижимость не добавлена!");
        }
    }

    // Сохранение клиентов в текстовый файл
    public static void saveNewPropertyInTextFile(Property property) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(textFile, true))) {
            br.write(property.getAddress() + "," + property.getPropertyTyp() + "," + property.getPrice() + "," +
                    property.getSize() + "," + property.getStatusOfProperty());
            br.newLine();
            log.info("Недвижимость {} успешно сохранен в текстовый файл {}", property.getAddress(), textFile.getName());
        } catch (FileNotFoundException exception) {
            log.error("Файл {} для записи данных не найден!Error: {}", textFile, exception.getMessage());
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
    }

    //сериализация объектов в файл
    public static void saveNewPropertyInObjectFile(List<Property> list) {
        if (!propertyList.isEmpty()) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(objectFile))) {
                objectOutputStream.writeObject(list);
                log.info("Сериализация объектов прошла успешно в файл {}", objectFile.getName());
            } catch (FileNotFoundException exception) {
                log.error("Файл {} для записи данных не найден!Error: {}", objectFile, exception.getMessage());
            } catch (IOException exception) {
                log.error(exception.getMessage(), exception);
            }
        } else {
            System.out.println("Вы не добавляли новую недвижимость");
        }
    }

    public static void exitFromApp() {
        saveNewPropertyInObjectFile(propertyList);
    }

    //Просмотреть всех клиентов
    public static void showAllProperty() {
        System.out.println("Вы выбрали функцию  Просмотреть все объекты недвижимости");
        System.out.println("Ниже указаны все недвижимости в базе данных:");
        if (!propertyList.isEmpty()) {
            propertyList.stream().forEach(System.out::println);
        } else {
            System.out.println("Список недвижимости пуст");
        }
    }

    //десериализация объектов в список
    public static List<Property> readObjectFile(File file) {
        List<Property> propertyReadList = new ArrayList<>();
        if (file.exists()) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                propertyReadList = (ArrayList<Property>) objectInputStream.readObject();
            } catch (FileNotFoundException exception) {
                log.error("Файл {} для записи данных не найден!Error: {}", file.getName(), exception.getMessage());
            } catch (IOException exception) {
                log.error(exception.getMessage(), exception);
            } catch (ClassNotFoundException exception) {
                log.error(exception.getMessage(), exception);
            }
            return propertyReadList;
        } else {
            return propertyReadList;
        }
    }

    //проверка на наличие клиента в базе даннах
    public static boolean isPropertyAvailability(Property property) {
        return propertyList.stream().anyMatch(propertyObject -> propertyObject.equals(property));
    }
}
