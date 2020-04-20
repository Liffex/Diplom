package Controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sample.TestModel;

import java.sql.*;
import java.util.Optional;
import java.util.Vector;

public class AddKeyWordController {

    @FXML
    public TableColumn<String, String> keyWordColumn;

    @FXML
    public Button deleteButton;

    @FXML
    public TableView<String> keyWordTableView;

    @FXML
    public Label errorLabel;

    @FXML
    private Button addKeyWordButton;

    private ObservableList<String> keyWords = FXCollections.observableArrayList();

    @FXML
    private TextField keyWordText;

    @FXML
    void initialize() throws SQLException {
        getData();
        keyWordColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        keyWordTableView.setItems(keyWords);
    }

    @FXML
    void addKeyWordButtonClicked(ActionEvent event) throws SQLException {
        writeData();
    }

    private void getData() throws SQLException {
        keyWords.clear();
        Connection conn = TestModel.getConnection();
        String sql = "SELECT keyWord FROM keyWord";

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                keyWords.add(rs.getString("keyWord"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void writeData() throws SQLException {
        Connection conn = TestModel.getConnection();
        String sqlAddKeyWord = "INSERT INTO keyWord(keyWord) VALUES (?)";

        if (keyWords.contains(keyWordText.getText().toLowerCase()))
        {
            errorLabel.setText("Такое ключевое слово уже есть");
            errorLabel.setTextFill(Color.RED);
        } else {
            try (
                    PreparedStatement pstmt = conn.prepareStatement(sqlAddKeyWord)) {
                pstmt.setString(1, keyWordText.getText().toLowerCase());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            errorLabel.setText("Ключевое слово добавлено!");
            errorLabel.setTextFill(Color.GREEN);
            getData();
        }
    }

    private void showAlert(Alert.AlertType at, String text)
    {
        Alert alert = new Alert(at);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ключевое слово используется");
        alert.setContentText(text);
        alert.showAndWait();
    }

    @FXML
    private void deleteButtonClicked(ActionEvent actionEvent) throws SQLException {
        Connection conn = TestModel.getConnection();
        String sqlDelete = "DELETE FROM keyWord WHERE (keyWord = ?)";
        String sqlCheckPhrases = "SELECT idEngPhrase, engPhrase FROM engPhrase WHERE (idkeyWords = ?)";
        String sqlCheckPair = "SELECT idEngPhrase FROM engRuTranslation WHERE (idEngPhrase = ?)";
        String sqlGetKeyWordId = "SELECT idKeyWord FROM keyWord WHERE (keyWord = ?)";
        String sqlDeletePhrase = "DELETE FROM engPhrase WHERE (idEngPhrase = ?)";
        int idKeyWord;

        //todo change to dictionary
        Vector<String> existingPhrases = new Vector<>();
        Vector<Integer> existingPhrasesId = new Vector<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetKeyWordId)) {
            pstmt.setString(1,keyWordTableView.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            idKeyWord = rs.getInt("idKeyWord");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPhrases)) {
            pstmt.setString(1, String.valueOf(idKeyWord));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                existingPhrasesId.add(rs.getInt("idEngPhrase"));
                existingPhrases.add(rs.getString("engPhrase"));
            }
        }

        boolean pairExists = false;

        for (int id: existingPhrasesId) {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPair))
            {
                pstmt.setString(1, String.valueOf(id));
                ResultSet rs = pstmt.executeQuery();
                if (rs.next())
                    pairExists = true;
            }
        }

        if (pairExists)
        {
            showAlert(Alert.AlertType.ERROR,"Данное ключевое слово используется парой фраза-перевод, пожалуйста, удалите эту пару перед удаление ключевого слова");
        }
        else if(!existingPhrases.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Выбранное ключевое слово используется");
            alert.setContentText("Выбранное ключевое слово используется следующими словами без перевода, удалить их?");
            VBox dialogPaneContent = new VBox();
            Label label = new Label("Фразы:");
            TextArea textArea = new TextArea();
            String phrasesToDelete = "";
            for (String phr: existingPhrases) {
                phrasesToDelete = phrasesToDelete.concat(phr + '\n');
            }
            textArea.setText(phrasesToDelete);

            dialogPaneContent.getChildren().addAll(label, textArea);
            alert.getDialogPane().setContent(dialogPaneContent);

            Optional<ButtonType> option = alert.showAndWait();
            if(option.get() == null) {
                errorLabel.setText("Не выбран ответ");
            }else if (option.get() == ButtonType.OK) {
                for (int id: existingPhrasesId) {
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlDeletePhrase)){
                        pstmt.setString(1, String.valueOf(id));
                        pstmt.executeUpdate();
                    }
                }

                try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)){
                    pstmt.setString(1, keyWordTableView.getSelectionModel().getSelectedItem());
                    pstmt.executeUpdate();
                }

                getData();
                errorLabel.setText("Ключевое слово удалено");
                errorLabel.setTextFill(Color.GREEN);
            }else if (option.get() == ButtonType.CANCEL) {
                errorLabel.setText("");
            }
        }
    }
}
