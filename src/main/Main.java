package main;

import controllers.ViewFormController;
import db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import misc.Authentication;
import misc.sql.SQLCommands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;

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
    }

    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException {
        if(args.length == 0 || !args[0].equals("-adduser"))
            launch(args);
        else {
            DBConnection db = new DBConnection();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Войти. Введите логин: ");
            String login = reader.readLine();
            System.out.println("Введите пароль: ");
            String password = reader.readLine();
            if (!Authentication.checkUser(login, password))
                System.out.println("Неправильные данные");
            else
            {
                System.out.println("Успешный вход. \nДобавить пользователя");
                System.out.println("Введите логин: ");
                String newLogin = reader.readLine();
                if (SQLCommands.checkUsername(newLogin))
                {
                    System.out.println("Такой аккаунт уже существует");
                    return;
                }
                System.out.println("Введите пароль: ");
                String newPass1 = reader.readLine();
                System.out.println("Повторите пароль: ");
                String newPass2 = reader.readLine();
                if (!newPass1.equals(newPass2)) {
                    System.out.println("Пароли не одинаковые");
                    return;
                }
                Authentication.addUser(newLogin, newPass1);
                System.out.println("Пользователь добавлен");
            }
        }
    }
}
