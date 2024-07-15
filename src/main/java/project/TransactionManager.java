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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

@Slf4j
public class TransactionManager {
    private static final File textFile = Path.of("src", "main", "resources", "transaction_base.txt").toFile();
    private static final File objectFile = Path.of("src", "main", "resources", "transaction_base.ser").toFile();
    private static Scanner scanner = new Scanner(System.in);
    // private static Set<Transaction> transactionList = new HashSet<>(readObjectFile(objectFile));
    private static boolean transactionAdded = false; // флаг для сериализации клиентов если были добавлены новые

    //метод добавления сделки в базу данных
    public static void addNewTransaction() {
        System.out.println("Вы выбрали функцию Зарегистрировать сделку.");
        // int transactionId = randomId();
        System.out.println("Введите id недвижимости состоящее из 8 чисел: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Вы не ввели id!");
            System.out.println("Введите id недвижимости состоящее из 8 чисел: ");
            scanner.next();
        }
        int propertyId = scanner.nextInt();
        Property property = searchPropertyById(propertyId);
//        System.out.println("Введите id клиента состоящее из 8 значных чисел: ");
//        int clientId = scanner.nextInt();
//        while (!scanner.hasNextInt()) {
//            System.out.println("Вы не ввели id!");
//            System.out.println("Введите id недвижимости состоящее из 8 значных чисел: ");
//            propertyId = scanner.nextInt();
//        }
//        System.out.println("Введите дату сделки пример: (dd.MM.yyyy).");
//        ClientTyp clientTyp = ClientTyp.fromStringClientTyp(scanner.nextLine());
//        while (clientTyp == ClientTyp.NONE) {
//            System.out.println("Вы указали неправильное значение Тип Клиента!");
//            System.out.println("Введите тип клиента как указано в скобках (BAYER,SELLER,TENANT).");
//            clientTyp = ClientTyp.fromStringClientTyp(scanner.nextLine());
//        }
//        Client client = new Client(name.trim(), contactDate.trim(), clientTyp);
//        if (!isTransactionAvailability(client)) {
//            transactionAdded = transactionList.add(client);
//            log.info("Клиент {} успешно добавлен в список.", client.getName());
//            System.out.println("Количество Клиентов " + transactionList.size());
//            saveNewClientInTextFile(client);
//        } else {
//            System.out.println("Не добавлен! В базе данных есть такой клиент с контактными данными: " + client.getContactDate());
//            log.info("Клиент не добавлен!");
//        }
    }

    // Сохранение клиентов в текстовый файл
//    public static void saveNewClientInTextFile(Transaction transaction) {
//        try (BufferedWriter br = new BufferedWriter(new FileWriter(textFile, true))) {
//            br.write(client.getName() + "," + client.getContactDate() + "," + client.getClientTyp());
//            br.newLine();
//            log.info("Клиент {} успешно сохранен в текстовый файл {}", client.getName(), textFile.getName());
//        } catch (FileNotFoundException exception) {
//            log.error("Файл {} для записи данных не найден!Error: {}", textFile, exception.getMessage());
//        } catch (IOException exception) {
//            log.error(exception.getMessage(), exception);
//        }
//    }

    //сериализация объектов в файл
//    public static void saveNewClientInObjectFile(List<Transaction> list) {
//        if (!transactionList.isEmpty()) {
//            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(objectFile))) {
//                objectOutputStream.writeObject(list);
//                log.info("Сериализация объектов прошла успешно в файл {}", objectFile.getName());
//            } catch (FileNotFoundException exception) {
//                log.error("Файл {} для записи данных не найден!Error: {}", objectFile, exception.getMessage());
//            } catch (IOException exception) {
//                log.error(exception.getMessage(), exception);
//            }
//        } else {
//            System.out.println("Вы не добавляли новых клиентов");
//        }
//    }


    //Просмотреть всех клиентов
//    public static void showAllClients() {
//        System.out.println("Вы выбрали функцию Просмотр всех клиентов");
//        System.out.println("Ниже указаны все клиенты в базе данных:");
//        if (!transactionList.isEmpty()) {
//            transactionList.stream().forEach(System.out::println);
//        } else {
//            System.out.println("Список клиентов пуст");
//        }
//    }

    //десериализация объектов в список
    public static List<Transaction> readObjectFile(File file) {
        List<Transaction> transactionReadList = new ArrayList<>();
        if (file.exists()) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                transactionReadList = (ArrayList<Transaction>) objectInputStream.readObject();
            } catch (FileNotFoundException exception) {
                log.error("Файл {} для записи данных не найден!Error: {}", file.getName(), exception.getMessage());
            } catch (IOException exception) {
                log.error(exception.getMessage(), exception);
            } catch (ClassNotFoundException exception) {
                log.error(exception.getMessage(), exception);
            }
            return transactionReadList;
        } else {
            return transactionReadList;
        }
    }

    //проверка на наличие клиента в базе даннах
//    public static boolean isTransactionAvailability(Transaction transaction) {
//        return transactionList.stream().anyMatch(transactionObject -> transactionObject.equals(transaction));
//    }

    //выход из приложения серилизируя лист если были добавлены новые клиенты
//    public static void exitFromApp() {
//        if (transactionAdded) {
//            saveNewClientInObjectFile(transactionList);
//            log.info("Лист с новыми клиентами был сериалезирован");
//        }
//    }

    //генерирование рандом id для объекта
//    private static int randomId() {
//        int id = (int) (Math.random() * 90000000) + 10000000;
//        if (transactionList.stream().anyMatch(transaction -> transaction.getId() == id)) {
//            randomId();
//        }
//        return id;
//    }

    // поиск недвижимости по id возвращает объект Property если есть или null если его нет
    private static Property searchPropertyById(int id) {
        if (String.valueOf(id).length() == 8) {
            int finalId = id;
            Property property = PropertyManager.getPropertyList().stream().filter(propertyObject ->
                    propertyObject.getId() == finalId).findAny().orElse(null);
            while (property == null) {
                System.out.println("Данной недвижимости с id:" + id + " нет в базе данных!");
                System.out.println("Введите id недвижимости состоящее из 8 чисел: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Вы не ввели id!");
                    System.out.println("Введите id недвижимости состоящее из 8 значных чисел: ");
                    scanner.next();
                }
                id = scanner.nextInt();
                searchPropertyById(id);
            }
            return property;
        } else return null;
    }
}


