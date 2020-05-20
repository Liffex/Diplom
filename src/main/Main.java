package main;

import controllers.ViewFormController;
import db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import misc.Authentication;
import misc.sql.SQLCommands;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        Parameters test = getParameters();
        System.out.println(test.getUnnamed().toString());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/viewForm.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/viewForm.fxml"));
        Scene scene = new Scene(loader.load());
        ViewFormController contr = loader.getController();
        primaryStage.setTitle("Система учёта эвфемизмов");
        primaryStage.setScene(scene);
        contr.setMode(test.getUnnamed());

        primaryStage.show();
        //contr.loadLibrary();
    }

    private static void addUser() throws SQLException, NoSuchAlgorithmException {
        DBConnection db = new DBConnection();
        Console console = System.console();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Необходим доступ с правами администратора\nВведите логин: ");
        String login = scanner.nextLine();
        System.out.println("Введите пароль: ");
        String password = String.valueOf(console.readPassword());
        while (!Authentication.checkUser(login, password)) {
            System.out.println("Неправильные данные, повторите ввод");
            System.out.println("Введите логин:");
            login = scanner.nextLine();
            System.out.println("Введите пароль: ");
            password = String.valueOf(console.readPassword());
        }

            System.out.println("Успешный вход \nДобавить пользователя");
            System.out.println("Введите логин нового пользователя: ");
            String newLogin = scanner.nextLine();

            while (SQLCommands.checkUsername(newLogin)) {
                System.out.println("Такой аккаунт уже существует");
                System.out.println("Введите логин: ");
                newLogin = scanner.nextLine();
            }

            System.out.println("Введите пароль: ");
            String newPass1 = String.valueOf(console.readPassword());
            System.out.println("Повторите пароль: ");
            String newPass2 = String.valueOf(console.readPassword());

            while (!newPass1.equals(newPass2)) {
                System.out.println("Пароли не одинаковые");
                System.out.println("Введите пароль: ");
                newPass1 = String.valueOf(console.readPassword());
                System.out.println("Повторите пароль: ");
                newPass2 = String.valueOf(console.readPassword());
            }
            Authentication.addUser(newLogin, newPass1);
            System.out.println("Пользователь добавлен");
    }

    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
        if(args.length == 0 || !args[0].equals("-adduser"))
            launch(args);
        else {
            addUser();
        }
    }
}
