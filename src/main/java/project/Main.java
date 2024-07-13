package project;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;


@Slf4j
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static int numberFunction = 0;

    public static void main(String[] args) {

        System.out.println("Добро пожаловать в программу Real Estate Management System");
        do {
            System.out.println("Меню: ");
            System.out.println("1. Добавить объект недвижимости\n" +
                    "2. Добавить клиента\n" +
                    "3. Зарегистрировать сделку\n" +
                    "4. Просмотреть все объекты недвижимости\n" +
                    "5. Просмотреть всех клиентов\n" +
                    "6. Просмотреть все сделки\n" +
                    "7. Выход\n");
            System.out.println("Укажите номер вашей функции: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Вы ввели некорректное значение!!");
                System.out.println("Введите цифру функции от 1 до 7 пожалуйста.");
                scanner.next();
            }
            numberFunction = scanner.nextInt();
            while (!(numberFunction > 0 && numberFunction < 8)) {
                System.out.println("Вы ввели номер: " + numberFunction + ". Номера этой функции нет в списке меню!");
                System.out.println("Введите цифру функции от 1 до 7 пожалуйста.");
                while (!scanner.hasNextInt()) {
                    System.out.println("Вы ввели некорректное значение!!");
                    System.out.println("Введите цифру функции от 1 до 7 пожалуйста.");
                    scanner.next();
                }
                numberFunction = scanner.nextInt();
            }
            switch (numberFunction) {
                case 1:

                    break;
                case 2:
                    ClientsManager.addNewClient();
                    break;
                case 3:
                case 4:
                case 5:
                    ClientsManager.showAllClients();
                    break;
                case 6:
                case 7:
                    ClientsManager.exitFromApp();
                    break;
                default:
                    System.out.println("Сюда вы никогда не попадете!!!");
            }
        } while (numberFunction != 7);
    }
}

