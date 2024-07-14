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
public class ClientsManager {
    private static final File textFile = Path.of("src", "main", "resources", "clients_base.txt").toFile();
    private static final File objectFile = Path.of("src", "main", "resources", "clients_base.ser").toFile();
    private static Scanner scanner = new Scanner(System.in);
    private static List<Client> clientsList = new ArrayList<>(readObjectFile(objectFile));
    private static boolean clientAdded = false; //флаг для сериализации листа если добавлены новые клиенты

    //метод добавления клиентов в базу данных
    public static void addNewClient() {
        System.out.println("Вы выбрали функцию добавления Клиента в базу данных!");
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
        Client client = new Client(name.trim(), contactDate.trim(), clientTyp);
        if (!isClientAvailability(client)) {
            clientAdded = clientsList.add(client);
            log.info("Клиент {} успешно добавлен в список.", client.getName());
            saveNewClientInTextFile(client);
        } else {
            System.out.println("Не добавлен! В базе данных есть такой клиент с контактными данными: " + client.getContactDate());
            log.info("Клиент не добавлен!");
        }
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
            log.error(exception.getMessage(), exception);
        }
    }

    //сериализация объектов в файл
    public static void saveNewClientInObjectFile(List<Client> list) {
        if (!clientsList.isEmpty()) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(objectFile))) {
                objectOutputStream.writeObject(list);
                log.info("Сериализация объектов прошла успешно в файл {}", objectFile.getName());
            } catch (FileNotFoundException exception) {
                log.error("Файл {} для записи данных не найден!Error: {}", objectFile, exception.getMessage());
            } catch (IOException exception) {
                log.error(exception.getMessage(), exception);
            }
        } else {
            System.out.println("Вы не добавляли новых клиентов");
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
            clientsList.stream().sorted().forEach(System.out::println);
        } else {
            System.out.println("Список клиентов пуст");
        }
    }

    //десериализация объектов в список
    public static List<Client> readObjectFile(File file) {
        List<Client> clientReadList = new ArrayList<>();
        if (file.exists()) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                clientReadList = (ArrayList<Client>) objectInputStream.readObject();
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

    //проверка на наличие клиента в базе даннах
    public static boolean isClientAvailability(Client client) {
        return clientsList.stream().anyMatch(clientObject -> clientObject.getContactDate().equalsIgnoreCase(client.getContactDate()));
    }

    public static List<Client> getClientsList() {
        return new ArrayList<>(clientsList);
    }
}

