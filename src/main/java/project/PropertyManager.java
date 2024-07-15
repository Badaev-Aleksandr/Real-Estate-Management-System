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
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Slf4j
public class PropertyManager {
    private static final File textFile = Path.of("src", "main", "resources", "property_base.txt").toFile();
    private static final File objectFile = Path.of("src", "main", "resources", "property_base.ser").toFile();
    private static Scanner scanner = new Scanner(System.in);
    private static Set<Property> propertyList = new HashSet<>(readObjectFile(objectFile));
    private static boolean propertyAdded = false; //флаг для сериализации листа если добавлена новая недвижимость

    // Добавить объект недвижимости
    public static void addNewProperty() {
        System.out.println("Вы выбрали функцию Добавить объект недвижимости в базу данных!");
        int id = randomId(); // генерация рандом id
        System.out.println("Введите адрес недвижимости: ");
        System.out.println("Пример ввода: (Индекс,Город,Улица,Дом,Квартира");
        String address = scanner.nextLine().trim();
        while (address.equalsIgnoreCase("") ) {
            System.out.println("Вы не ввели адрес!");
            System.out.println("Введите адрес недвижимости: ");
            System.out.println("Пример ввода: (Индекс,Город,Улица,Дом,Квартира");
            address = scanner.nextLine().trim();
        }
        System.out.println("Введите тип недвижимости: (APARTMENT,HOUSE,COMMERCIAL).");
        PropertyTyp propertyTyp = PropertyTyp.fromStringPropertyTyp(scanner.nextLine().trim());
        while (propertyTyp == PropertyTyp.NONE || propertyTyp == null) {
            System.out.println("Вы указали неправильное значение Тип Недвижимости!");
            System.out.println("Введите тип недвижимости как указано в скобках (APARTMENT,HOUSE,COMMERCIAL).");
            propertyTyp = PropertyTyp.fromStringPropertyTyp(scanner.nextLine().trim());
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
        PropertyStatus propertyStatus = PropertyStatus.fromStringPropertyStatus(scanner.next().trim());
        while (propertyStatus == PropertyStatus.NONE || propertyStatus == null) {
            System.out.println("Вы указали неправильное значение Статус Недвижимости!");
            System.out.println("Введите статус недвижимости как указано в скобках (AVAILABLE, RESERVED, SOLD).");
            propertyStatus = PropertyStatus.fromStringPropertyStatus(scanner.next());
        }
        Property property = new Property(id, address, propertyTyp, price, size, propertyStatus);
        propertyAdded = propertyList.add(property);
        if (propertyAdded) {
            System.out.println("Недвижимости присвоен id: " + id);
            log.info("Недвижимость по адресу {} успешно добавлена в список. Присвоен id: {}", property.getAddress(), id);
            saveNewPropertyInTextFile(property);
        } else {
            System.out.println("Не добавлена! В базе данных есть такая недвижимость. " + property);
            log.info("Недвижимость не добавлена!");
        }
    }

    // Сохранение клиентов в текстовый файл
    private static void saveNewPropertyInTextFile(Property property) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(textFile, true))) {
            br.write(property.getAddress() + "," + property.getPropertyTyp() + "," + property.getPrice() + "," +
                    property.getSize() + "," + property.getStatusOfProperty());
            br.newLine();
            log.info("Недвижимость {} успешно сохранена в текстовый файл {}", property.getAddress(), textFile.getName());
        } catch (FileNotFoundException exception) {
            log.error("Файл {} для записи данных не найден!Error: {}", textFile, exception.getMessage());
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
    }

    //сериализация объектов в файл
    private static void saveNewPropertyInObjectFile(Set<Property> list) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(objectFile))) {
            objectOutputStream.writeObject(list);
            log.info("Сериализация объектов прошла успешно в файл {}", objectFile.getName());
        } catch (FileNotFoundException exception) {
            log.error("Файл {} для записи данных не найден!Error: {}", objectFile, exception.getMessage());
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
    }

    public static void exitFromApp() {
        if (propertyAdded) {
            saveNewPropertyInObjectFile(propertyList);
        }
    }

    //Просмотреть всех клиентов
    public static void showAllProperty() {
        System.out.println("Вы выбрали функцию  Просмотреть все объекты недвижимости");
        System.out.println("Ниже указаны все недвижимости в базе данных:");
        if (!propertyList.isEmpty()) {
            propertyList.forEach(System.out::println);
        } else {
            System.out.println("Список недвижимости пуст");
        }
    }

    //десериализация объектов в список
    private static Set<Property> readObjectFile(File file) {
        Set<Property> propertyReadList = new HashSet<>();
        if (file.exists()) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                propertyReadList = (HashSet<Property>) objectInputStream.readObject();
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

    //генерирование рандом id для объекта
    public static int randomId() {
        int id = (int) (Math.random() * 90000000) + 10000000;
        if (propertyList.stream().anyMatch(property -> property.getId() == id)) {
            randomId();
        }
        return id;
    }


    public static Set<Property> getPropertyList() {
        return new HashSet<>(propertyList);
    }
}
