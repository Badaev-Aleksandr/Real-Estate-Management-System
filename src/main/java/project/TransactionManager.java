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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Slf4j
public class TransactionManager {
    private static final File textFile = Path.of("src", "main", "resources", "transaction_base.txt").toFile();
    private static final File objectFile = Path.of("src", "main", "resources", "transaction_base.ser").toFile();
    private static Scanner scanner = new Scanner(System.in);
    private static Set<Transaction> transactionList = new HashSet<>(readObjectFile(objectFile));
    private static boolean transactionAdded = false; // флаг для сериализации клиентов если были добавлены новые

    //метод добавления сделки в базу данных
    public static void addNewTransaction() {
        System.out.println("Вы выбрали функцию Зарегистрировать сделку.");
        int transactionId = randomId();
        Property property = getProperty();
        Client client = getClient();
        LocalDate date = getLocalDate();
        TransactionType transactionType = getTransactionType();
        double transactionAmount = getTransactionAmount();
        Transaction
                transaction = new Transaction(transactionId, property, client, date, transactionType, transactionAmount);
        transactionAdded = transactionList.add(transaction);
        if (transactionAdded) {
            System.out.println("Транзакции присвоен id: " + transactionId);
            ClientsManager.addTransaction(client.getId(), transaction); // добавляем транзакцию клиенту
            saveNewTransactionInTextFile(transaction);
            log.info("Транзакция с id: {} добавлена в список.", transactionId);
        } else {
            System.out.println("Данная транзакция уже есть в списке!");
        }
    }

    // Сохранение сделки в текстовый файл
    private static void saveNewTransactionInTextFile(Transaction transaction) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(textFile, true))) {
            br.write(transaction.getId() + "," + transaction.getProperty().getId() + "," + transaction.getClient().getId()
                    + "," + transaction.getLocalDate() + "," + transaction.getTransactionType() + "," + transaction.getTransactionAmount());
            br.newLine();
            log.info("Транзакция с id: {} успешно записана в текстовый файл {}.", transaction.getId(), textFile.getName());
        } catch (FileNotFoundException exception) {
            log.error("Файл {} для записи данных не найден!Error: {}", textFile, exception.getMessage());
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
    }

    //сериализация объектов в файл
    private static void saveNewTransactionInObjectFile(Set<Transaction> list) {
        if (!transactionList.isEmpty()) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(objectFile))) {
                objectOutputStream.writeObject(list);
                log.info("Сериализация объектов прошла успешно в файл {}", objectFile.getName());
            } catch (FileNotFoundException exception) {
                log.error("Файл {} для записи данных не найден!Error: {}", objectFile, exception.getMessage());
            } catch (IOException exception) {
                log.error(exception.getMessage(), exception);
            }
        } else {
            System.out.println("Вы не добавляли транзакции");
        }
    }


    //Просмотреть всех транзакций
    public static void showAllTransaction() {
        System.out.println("Вы выбрали функцию Просмотр всех Транзакций");
        System.out.println("Ниже указаны все транзакции в базе данных:");
        if (!transactionList.isEmpty()) {
            transactionList.forEach(System.out::println);
        } else {
            System.out.println("Список транзакций пуст");
        }
    }

    //десериализация объектов в список
    private static Set<Transaction> readObjectFile(File file) {
        Set<Transaction> transactionReadList = new HashSet<>();
        if (file.exists()) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                transactionReadList = (HashSet<Transaction>) objectInputStream.readObject();
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

    //проверка на наличие транзакции в базе даннах
//    public static boolean isTransactionAvailability(Transaction transaction) {
//        return transactionList.stream().anyMatch(transactionObject -> transactionObject.equals(transaction));
//    }

    //выход из приложения серилизируя лист если были добавлены новые клиенты
    public static void exitFromApp() {
        if (transactionAdded) {
            saveNewTransactionInObjectFile(transactionList);
            log.info("Лист с новыми транзакциями был сериалезирован");
        }
    }

    //генерирование рандом id для объекта
    private static int randomId() {
        int id = (int) (Math.random() * 90000000) + 10000000;
        if (transactionList.stream().anyMatch(transaction -> transaction.getId() == id)) {
            randomId();
        }
        return id;
    }

    // поиск недвижимости по id возвращает объект Property если есть или null если его нет
    private static Property searchPropertyById(int id) {
        Property property = PropertyManager.getPropertyList().stream().filter(propertyObject ->
                propertyObject.getId() == id).findAny().orElse(null);
        if (property == null) {
            System.out.println("Данной недвижимости с id:" + id + " нет в базе данных!");
            return null;
        } else return property;
    }

    // поиск клиента по id возвращает объект Client если есть или null если его нет
    private static Client searchClientById(int id) {
        Client client = ClientsManager.getClientsList().stream().filter(clientObject ->
                clientObject.getId() == id).findAny().orElse(null);
        if (client == null) {
            System.out.println("Данной недвижимости с id:" + id + " нет в базе данных!");
            return null;
        } else return client;
    }

    //метод добывает Недвижимость по id
    private static Property getProperty() {
        int propertyId;
        Property property;
        do {
            do {
                System.out.println("Введите id недвижимости состоящее из 8 чисел: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Вы не ввели id!");
                    System.out.println("Введите id недвижимости состоящее из 8 чисел: ");
                    scanner.next();
                }
                propertyId = scanner.nextInt();
                if (String.valueOf(propertyId).trim().length() != 8) {
                    System.out.println("Вы ввели неправильное количество чисел");
                }
            } while (String.valueOf(propertyId).trim().length() != 8);
            property = searchPropertyById(propertyId);
        } while (property == null);
        return property;
    }

    //получаем клиента
    private static Client getClient() {
        int clientId;
        Client client;
        do {
            do {
                System.out.println("Введите id клиента состоящее из 8 чисел: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Вы не ввели id!");
                    System.out.println("Введите id клиента состоящее из 8 чисел: ");
                    scanner.next();
                }
                clientId = scanner.nextInt();
                if (String.valueOf(clientId).trim().length() != 8) {
                    System.out.println("Вы ввели неправильное количество чисел");
                }
            } while (String.valueOf(clientId).trim().length() != 8);
            client = searchClientById(clientId);
        } while (client == null);
        return client;
    }

    // получаем дату
    private static LocalDate getLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = null;
        do {
            System.out.println("Введите дату сделки в формате: (dd.MM.yyyy).");
            try {
                String dateString = scanner.nextLine();
                date = LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException exception) {
                log.error("Дата введена не правильно! Error: {}", exception.getMessage());
            }
        } while (date == null);
        return date;
    }

    private static TransactionType getTransactionType() {
        System.out.println("Введите тип сделки : (PURCHASE, SALE, RENT).");
        TransactionType transactionType = TransactionType.fromStringTransactionTyp(scanner.nextLine().trim());
        while (transactionType == TransactionType.NONE || transactionType == null) {
            System.out.println("Вы указали неправильное значение Тип Недвижимости!");
            System.out.println("Введите тип недвижимости как указано в скобках (APARTMENT, HOUSE, COMMERCIAL).");
            transactionType = TransactionType.fromStringTransactionTyp(scanner.nextLine().trim());
        }
        return transactionType;
    }

    private static Double getTransactionAmount() {
        System.out.println("Введите сумму сделки в $: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Введено неверное значение суммы сделки!");
            System.out.println("Введите сумму цифрами пример: (99,99)");
            scanner.next();
        }
        return scanner.nextDouble();

    }
}



