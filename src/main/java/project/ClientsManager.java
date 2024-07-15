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
public class ClientsManager {
    private static final File textFile = Path.of("src", "main", "resources", "clients_base.txt").toFile();
    private static final File objectFile = Path.of("src", "main", "resources", "clients_base.ser").toFile();
    private static Scanner scanner = new Scanner(System.in);
    private static Set<Client> clientsList = new HashSet<>(readObjectFile(objectFile));
    private static boolean clientAdded = false; //флаг для сериализации листа если добавлены новые клиенты

    //метод добавления клиентов в базу данных
    public static void addNewClient() {
        System.out.println("Вы выбрали функцию Добавить клиента.");
        int id = randomId();
        System.out.println("Введите имя клиента: ");
        String name = scanner.nextLine().trim();
        while (name.equalsIgnoreCase("")) {
            System.out.println("Вы не ввели имя!");
            System.out.println("Введите имя клиента: ");
            name = scanner.nextLine().trim();
        }
        System.out.println("Введите контактные данные клиента: ");
        String contactDate = scanner.nextLine().trim();
        while (contactDate.equalsIgnoreCase("")) {
            System.out.println("Вы не ввели контактные данные!");
            System.out.println("Введите контактные данные клиента: ");
            contactDate = scanner.nextLine().trim();
        }
        System.out.println("Введите тип клиента: (BAYER,SELLER,TENANT).");
        ClientTyp clientTyp = ClientTyp.fromStringClientTyp(scanner.nextLine().trim());
        while (clientTyp == ClientTyp.NONE || clientTyp == null) {
            System.out.println("Вы указали неправильное значение Тип Клиента!");
            System.out.println("Введите тип клиента как указано в скобках (BAYER,SELLER,TENANT).");
            clientTyp = ClientTyp.fromStringClientTyp(scanner.nextLine().trim());
        }
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
        // проверка на наличие файла
        try (BufferedWriter br = new BufferedWriter(new FileWriter(textFile, true))) {
            br.write(client.getName() + "," + client.getContactDate() + "," + client.getClientTyp());
            br.newLine();
            log.info("Клиент {} успешно сохранен в текстовый файл {}", client.getName(), textFile.getName());
        } catch (FileNotFoundException exception) {
            log.error("Файл {} для записи данных не найден!Error: {}", textFile, exception.getMessage());
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
    }

    //сериализация объектов в файл
    private static void saveNewClientInObjectFile(Set<Client> list) {
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

    //генерирование рандом id для объекта
    public static int randomId() {
        int id = (int) (Math.random() * 90000000) + 10000000;
        if (clientsList.stream().anyMatch(client -> client.getId() == id)) {
            randomId();
        }
        return id;
    }

    public static Set<Client> getClientsList() {
        return new HashSet<>(clientsList);
    }
}

