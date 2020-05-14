package misc;

import javafx.scene.control.Alert;
import misc.sql.SQLCommands;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Authentication {
    private static String hash (String pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pass.getBytes());
        byte[] hash = md.digest();
        return DatatypeConverter.printHexBinary(hash).toLowerCase();
    }

    public static boolean checkUser (String username, String password) throws NoSuchAlgorithmException, SQLException {
        if(SQLCommands.checkUsername(username)) {
            String pass = SQLCommands.getPassword(username);
            String inputPass = hash(password);

            return pass.equals(inputPass);
        } else {
            System.out.println("Пользователь не найден");
            return false;
        }
    }

    public static void addUser (String username, String password) throws SQLException, NoSuchAlgorithmException {
        if(!SQLCommands.checkUsername(username)) {
            SQLCommands.addUser(username, hash(password), "ADMIN");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Такой пользователь уже существует");
            alert.showAndWait();
        }
    }
}
