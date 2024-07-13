package project;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class ClientsManager {
    private static List<Client> clientsList = new ArrayList<>();
    private static final File textFile = Path.of("src", "main", "resources", "clients_base.txt").toFile();
    private static final File objectFile = Path.of("src", "main", "resources", "clients_base.ser").toFile();
    private static Scanner scanner = new Scanner(System.in);

    //метод добавления клиентов в базу данных
    public static void addNewClient() {
        System.out.println("Добро пожаловать в метод добавления Клиента в базу данных!");
        System.out.println("Введите имя клиента: ");
        String name = scanner.nextLine();
        System.out.println("Введите контактные данные клиента: ");
        String contactDate = scanner.nextLine();
        System.out.println("Введите тип клиента: (BAYER,SELLER,TENANT).");
        ClientTyp clientTyp = ClientTyp.fromStringClientTyp(scanner.nextLine());
        while (clientTyp == ClientTyp.NONE) {
            System.out.println("Вы указали неправильное значение Тип Клиента!");
            System.out.println("Введите тип клиента как указано в скобках (BAYER,SELLER,TENANT).");
            clientTyp = ClientTyp.fromStringClientTyp(scanner.nextLine());
        }
        Client client = new Client(name, contactDate, clientTyp);
        clientsList.add(client);
        log.info("Клиент {} успешно добавлен в список.", client.getName());
        saveNewClientInTextFile(client);
    }

    // Сохранение клиентов в текстовый файл
    public static void saveNewClientInTextFile(Client client) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(textFile, true))) {
            br.write(client.getName() + "," + client.getContactDate() + "," + client.getClientTyp());
            br.newLine();
            log.info("Клиент {} успешно сохранен в текстовый файл {}", client.getName(), textFile.getName());
        } catch (FileNotFoundException exception) {
            log.error("Файл {} для записи данных не найден!Error: {}", textFile, exception.getMessage());
        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
    }

    //сериализация объектов в файл
    public static void saveNewClientInObjectFile(List<Client> list) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(objectFile))) {
            objectOutputStream.writeObject(list);
            log.info("Сериализация объектов прошла успешно в файл {}", objectFile.getName());
        } catch (FileNotFoundException exception) {
            log.error("Файл {} для записи данных не найден!Error: {}", objectFile, exception.getMessage());
        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
    }

    public static void exitFromApp() {
        saveNewClientInObjectFile(clientsList);
        System.out.println("До новых встреч");
    }

    public static void showAllClients() {
        if (!clientsList.isEmpty()) {
            clientsList.stream().forEach(System.out::println);
        } else {
            System.out.println("Список клиентов пуст");
        }
    }

    public static List<Client> getClientsList() {
        return clientsList;
    }
}

