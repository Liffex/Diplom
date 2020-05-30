package misc;

import javafx.scene.control.Alert;
import misc.sql.SQLCommands;
import org.apache.commons.codec.binary.Hex;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Authentication {
    private static Logger log = Logger.getLogger(Authentication.class.getName());

    private static String hash (String pass) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        assert md != null;
        md.update(pass.getBytes());
        byte[] hash = md.digest();
        return Hex.encodeHexString(hash);
    }

    public static boolean checkUser (String username, String password) {
        if(SQLCommands.checkUsername(username)) {
            String pass = SQLCommands.getPassword(username);
            String inputPass = hash(password);

            return pass.equals(inputPass);
        } else {
            return false;
        }
    }

    public static void addUser (String username, String password) {
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
