package main;

import controllers.ViewFormController;
import db.DBConnection;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import misc.Authentication;
import misc.sql.SQLCommands;
import misc.sql.SQLQueriesStore;

import java.awt.*;
import java.io.Console;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parameters test = getParameters();
        //System.out.println(test.getUnnamed().toString());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/viewForm.fxml"));
        Scene scene = new Scene(loader.load());
        ViewFormController contr = loader.getController();
        primaryStage.setTitle("Система учёта эвфемизмов");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));
        contr.setMode(test.getUnnamed());

        primaryStage.show();
    }

    private static void removeUser() throws SQLException, NoSuchAlgorithmException {
        DBConnection db = new DBConnection();
        Console console = System.console();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Необходим доступ с правами администратора\nВведите логин: ");

        String login = scanner.nextLine();
        while (login.equals("")) {
            System.out.println("Введена пустая строка, повторите ввод");
            System.out.println("Введите логин:");
            login = scanner.nextLine();
        }

        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();
        //String password = String.valueOf(console.readPassword());
        while (password.equals("")) {
            System.out.println("Введена пустая строка, повторите ввод");
            System.out.println("Введите пароль:");
            password = scanner.nextLine();
            //password = String.valueOf(console.readPassword());
        }

        while (!Authentication.checkUser(login, password)) {
            System.out.println("Неправильные данные, повторите ввод");
            System.out.println("Введите логин:");
            login = scanner.nextLine();
            while (login.equals("")) {
                System.out.println("Введена пустая строка, повторите ввод");
                System.out.println("Введите логин:");
                login = scanner.nextLine();
            }
            System.out.println("Введите пароль: ");
            password = scanner.nextLine();
            //password = String.valueOf(console.readPassword());
            while (password.equals("")) {
                System.out.println("Введена пустая строка, повторите ввод");
                System.out.println("Введите пароль:");
                password = scanner.nextLine();
                //password = String.valueOf(console.readPassword());
            }
        }
        System.out.println("Успешный вход");

        boolean cont = true;

        while (cont) {
            System.out.println("Выберите пользователя, которого хотите удалить:");

            ObservableList<String> users = SQLQueriesStore.getUsers();
            if (users.size() == 1) {
                System.out.println("В системе остался один администратор, удаление невозможно");
                return;
            }
            for (int i = 0; i < users.size(); i++) {
                System.out.println(i + ". " + users.get(i));
            }
            int choise = -1;

            while (choise < 0 || choise > users.size()-1) {
                while (true) {
                    try {
                        choise = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (NumberFormatException ne) {
                        System.out.println("Это не число");
                    }
                }
            }

            System.out.println("Вы действительно хотите удалить пользователя " + users.get(choise) + "? д/н");
            String confirmation = scanner.nextLine();
            if (confirmation.toLowerCase().matches("д|да")) {
                SQLCommands.deleteUser(users.get(choise));
                System.out.println("Пользователь " + users.get(choise) + " удалён.");
            }
            System.out.println("Вы хотите удалить другого пользователя? д/н");
            confirmation = scanner.nextLine();
            if (!confirmation.toLowerCase().matches("д|да")) {
                cont = false;
            }
        }
    }

    private static void addUser() throws SQLException, NoSuchAlgorithmException {
        DBConnection db = new DBConnection();
        Console console = System.console();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Необходим доступ с правами администратора\nВведите логин: ");
        String login = scanner.nextLine();

        while (login.equals("")) {
            System.out.println("Введена пустая строка, повторите ввод");
            System.out.println("Введите логин:");
            login = scanner.nextLine();
        }

        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();
        //String password = String.valueOf(console.readPassword());

        while (password.equals("")) {
            System.out.println("Введена пустая строка, повторите ввод");
            System.out.println("Введите пароль:");
            password = scanner.nextLine();
            //password = String.valueOf(console.readPassword());
        }

        while (!Authentication.checkUser(login, password)) {
            System.out.println("Неправильные данные, повторите ввод");
            System.out.println("Введите логин:");
            login = scanner.nextLine();
            while (login.equals("")) {
                System.out.println("Введена пустая строка, повторите ввод");
                System.out.println("Введите логин:");
                login = scanner.nextLine();
            }
            System.out.println("Введите пароль: ");
            password = scanner.nextLine();
            //password = String.valueOf(console.readPassword());
            while (password.equals("")) {
                System.out.println("НВведена пустая строка, повторите ввод");
                System.out.println("Введите пароль:");
                password = scanner.nextLine();
                //password = String.valueOf(console.readPassword());
            }
        }

        System.out.println("Успешный вход \nДобавить пользователя");
        System.out.println("Введите логин нового пользователя: ");
        String newLogin = scanner.nextLine();

        while (newLogin.equals("")) {
            System.out.println("Введена пустая строка, повторите ввод");
            System.out.println("Введите логин:");
            newLogin = scanner.nextLine();
        }

        while (SQLCommands.checkUsername(newLogin)) {
            System.out.println("Такой аккаунт уже существует");
            System.out.println("Введите логин: ");
            newLogin = scanner.nextLine();
            while (newLogin.equals("")) {
                System.out.println("Введена пустая строка, повторите ввод");
                System.out.println("Введите логин:");
                newLogin = scanner.nextLine();
            }
        }

        System.out.println("Введите пароль: ");
        String newPass1 = scanner.nextLine();
        //String newPass1 = String.valueOf(console.readPassword());
        while (newPass1.equals("")) {
            System.out.println("Введена пустая строка, повторите ввод");
            System.out.println("Введите пароль:");
            newPass1 = scanner.nextLine();
            //newPass1 = String.valueOf(console.readPassword());
        }
        System.out.println("Повторите пароль: ");
        String newPass2 = scanner.nextLine();
        //String newPass2 = String.valueOf(console.readPassword());
        while (newPass2.equals("")) {
            System.out.println("Введена пустая строка, повторите ввод");
            System.out.println("Повторите пароль:");
            newPass2 = scanner.nextLine();
            //newPass2 = String.valueOf(console.readPassword());
        }

        while (!newPass1.equals(newPass2)) {
            System.out.println("Пароли не совпадают");
            System.out.println("Введите пароль: ");
            newPass1 = scanner.nextLine();
            //newPass1 = String.valueOf(console.readPassword());
            while (newPass1.equals("")) {
                System.out.println("Введена пустая строка, повторите ввод");
                System.out.println("Введите пароль:");
                newPass1 = scanner.nextLine();
                //newPass1 = String.valueOf(console.readPassword());
            }
            System.out.println("Повторите пароль: ");
            newPass2 = scanner.nextLine();
            //newPass2 = String.valueOf(console.readPassword());
            while (newPass2.equals("")) {
                System.out.println("Введена пустая строка, повторите ввод");
                System.out.println("Повторите пароль:");
                newPass2 = scanner.nextLine();
                //newPass2 = String.valueOf(console.readPassword());
            }
        }
        Authentication.addUser(newLogin, newPass1);
        System.out.println("Пользователь добавлен");
    }

    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
        if(args.length == 0)
            launch(args);
        else {
            switch (args[0]) {
                case "-adduser": addUser(); break;
                case "-edituser": removeUser(); break;
                case "-admin": launch(args); break;
                default: {
                    System.out.println("Введённый ключ не найден");
                } break;
            }
        }
        System.exit(0);
    }
}
