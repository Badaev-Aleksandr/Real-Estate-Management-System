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
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

@Slf4j
public class ClientsManager {
    private static final File textFile = Path.of("src", "main", "resources", "clients_base.txt").toFile();
    private static final File objectFile = Path.of("src", "main", "resources", "clients_base.ser").toFile();
    private static final File directory = Path.of("src", "main", "resources").toFile();
    private static Scanner scanner = new Scanner(System.in);
    private static Set<Client> clientsList = new HashSet<>(readObjectFile(objectFile));
    private static boolean clientAdded = false; //флаг для сериализации листа если добавлены новые клиенты

    //метод добавления клиентов в базу данных
    public static void addNewClient() {
        System.out.println("Вы выбрали функцию Добавить клиента.");
        int id = randomId();
        String name = getNameFromUser();
        String contactDate = getContactDateFromUser();
        ClientTyp clientTyp = getClientTyp();
        Client client = new Client(id, name, contactDate, clientTyp);
        clientAdded = clientsList.add(client);
        if (clientAdded) {
            System.out.println("Клиенту присвоен id: " + id);
            log.info("Клиент {} успешно добавлен в список с id{}.", client.getName(), id);
            saveNewClientInTextFile(client);
        } else {
            System.out.println("Не добавлен! В базе данных уже существует клиент с контактными данными: " + client.getContactDate());
            log.info("Клиент {} не добавлен!", client.getName());
        }
    }

    // Сохранение клиентов в текстовый файл
    private static void saveNewClientInTextFile(Client client) {
        checkDirectoryFileExists();
        try (BufferedWriter br = new BufferedWriter(new FileWriter(textFile, true))) {
            br.write(client.getId() + "," + client.getName() + "," + client.getContactDate() + "," + client.getClientTyp());
            br.newLine();
            log.info("Клиент {} с id: {} успешно сохранен в текстовый файл {}", client.getName(), client.getId(), textFile.getName());
        } catch (FileNotFoundException exception) {
            log.error("Файл {} для записи данных не найден! Error: {}", textFile, exception.getMessage());
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
    }

    //сериализация объектов в файл
    private static void saveNewClientInObjectFile(Set<Client> list) {
        if (!objectFile.exists()) {
            System.out.println("Внимание файл: " + objectFile.getName() + " с базой данных клиентов не был найден. Будет создан новый!");
            log.warn("Внимание файл: {} с базой данных клиентов не был найден. Будет создан новый!", objectFile.getName());
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(objectFile))) {
            objectOutputStream.writeObject(list);
            log.info("Сериализация объектов прошла успешно в файл {}", objectFile.getName());
        } catch (FileNotFoundException exception) {
            log.error("Файл {} для записи данных не найден!Error: {}", objectFile, exception.getMessage());
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
    }

    //сериализация листа с объектами при выходе из приложения
    public static void exitFromApp() {
        if (clientAdded) {
            saveNewClientInObjectFile(clientsList);
        }
    }

    //Просмотреть всех клиентов
    public static void showAllClients() {
        System.out.println("Вы выбрали функцию Просмотр всех клиентов");
        System.out.println("Ниже указаны все клиенты в базе данных:");
        if (!clientsList.isEmpty()) {
            clientsList.forEach(System.out::println);
        } else {
            System.out.println("Список клиентов пуст");
        }
    }

    //десериализация объектов в список
    private static Set<Client> readObjectFile(File file) {
        //проверка на соответствие файла
        Set<Client> clientReadList = new HashSet<>();
        if (file.exists()) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                clientReadList = (HashSet<Client>) objectInputStream.readObject();
            } catch (FileNotFoundException exception) {
                log.error("Файл {} для записи данных не найден!Error: {}", file.getName(), exception.getMessage());
            } catch (IOException exception) {
                log.error(exception.getMessage(), exception);
            } catch (ClassNotFoundException exception) {
                log.error(exception.getMessage(), exception);
            }
            return clientReadList;
        } else {
            return clientReadList;
        }
    }

    //    //проверка на наличие клиента в базе даннах
//    private static boolean isClientAvailability(Client client) {
//        return clientsList.stream().anyMatch(clientObject -> clientObject.getContactDate().equalsIgnoreCase(client.getContactDate()));
//    }
    //добавление транзакций клиенту
    public static void addTransaction(int clientId, Transaction transaction) {
        Optional<Client> foundClient = clientsList.stream().filter(client -> client.getId() == clientId).findFirst();
        foundClient.ifPresentOrElse(client -> {
                    client.addTransactionToClientList(transaction);
                    System.out.println("Транзакция добавлена клиенту " + client.getName() + " с id: " + client.getId());
                },
                () -> System.out.println("Клиент с id: " + clientId + " не найден!"));
    }

    //просмотр всех транзакций клиента
    public static void showAllTransactionsClient() {
        System.out.println("Введите id клиента для просмотра его транзакций: ");
        int clientId = scanner.nextInt();
        Optional<Client> foundClient = clientsList.stream().filter(client -> client.getId() == clientId).findFirst();
        foundClient.ifPresentOrElse(Client::showAllClientTransaction,
                () -> System.out.println("Клиент с id: " + clientId + " не найден!"));
    }

    //генерирование рандом id для объекта
    private static int randomId() {
        int id = (int) (Math.random() * 90000000) + 10000000;
        if (clientsList.stream().anyMatch(client -> client.getId() == id)) {
            return randomId();
        }
        return id;
    }

    private static String getNameFromUser() {
        System.out.println("Введите имя клиента: ");
        String name = scanner.nextLine().trim();
        while (name.trim().isEmpty()) {
            System.out.println("Вы не ввели имя!");
            System.out.println("Введите имя клиента: ");
            name = scanner.nextLine().trim();
        }
        return name;
    }

    private static String getContactDateFromUser() {
        System.out.println("Введите контактные данные клиента: ");
        String contactDate = scanner.nextLine().trim();
        while (contactDate.trim().isEmpty()) {
            System.out.println("Вы не ввели контактные данные!");
            System.out.println("Введите контактные данные клиента: ");
            contactDate = scanner.nextLine().trim();
        }
        return contactDate;
    }

    private static ClientTyp getClientTyp() {
        System.out.println("Введите тип клиента: (BUYER, SELLER, LEASER, LESSOR).");
        ClientTyp clientTyp = ClientTyp.fromStringClientTyp(scanner.nextLine().trim());
        while (clientTyp == ClientTyp.NONE || clientTyp == null) {
            System.out.println("Вы указали неправильное значение Тип Клиента!");
            System.out.println("Введите тип клиента как указано в скобках: (BUYER, SELLER, LEASER, LESSOR).");
            clientTyp = ClientTyp.fromStringClientTyp(scanner.nextLine().trim());
        }
        return clientTyp;
    }

    // проверка на наличие файла и папки
    private static void checkDirectoryFileExists() {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                System.out.println("Внимание папка с resources не найдена! Будет создана новая! " +
                        directory.getName());
                if (directory.mkdir()) {
                    System.out.println("Создана новая папка: " + directory.getName());
                    log.warn("Создана новая папка: {}", directory.getName());
                } else
                    System.out.println("Не удалось создать папку: " + directory.getName());
            }
        }
        if (!textFile.exists()) {
            System.out.println("Внимание текстовый файл: " + textFile.getName() + " не был найден. Будет создан новый!");
            log.warn("Внимание текстовый файл: {} не был найден. Будет создан новый!", textFile.getName());
        }
    }


    public static Set<Client> getClientsList() {
        return new HashSet<>(clientsList);
    }
}

