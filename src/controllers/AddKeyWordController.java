package controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import misc.sql.SQLQueriesStore;
import misc.sql.SQLCommands;

import java.sql.*;
import java.util.Optional;

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
    private TextField keyWordText;

    private ObservableList<String> keyWords = FXCollections.observableArrayList();
    @FXML
    void initialize() {
        getData();
        keyWordColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        keyWordTableView.setItems(keyWords);
    }

    @FXML
    void addKeyWordButtonClicked(ActionEvent event) {
        writeData();
    }

    private void getData() {
        keyWords.clear();
        keyWords.addAll(SQLQueriesStore.getKeyWordList());
    }

    private void writeData() {
        if (keyWords.contains(keyWordText.getText().toLowerCase())) {
            errorLabel.setText("Такое ключевое слово уже есть");
            errorLabel.setTextFill(Color.RED);
        } else {
            SQLCommands.addKeyWord(keyWordText.getText().toLowerCase());
            errorLabel.setText("Ключевое слово добавлено!");
            errorLabel.setTextFill(Color.GREEN);
            getData();
        }
    }

    private void showAlert(Alert.AlertType at, String text) {
        Alert alert = new Alert(at);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ключевое слово используется");
        alert.setContentText(text);
        alert.showAndWait();
    }

    @FXML
    private void deleteButtonClicked(ActionEvent actionEvent) {
        int idKeyWord = SQLCommands.getKeyWordId(keyWordTableView.getSelectionModel().getSelectedItem());

        ObservableList<Integer> existingPhrasesId = SQLQueriesStore.getIdPhrasesUsingKeyWordList(idKeyWord);

        boolean pairExists = false;

        for (int id: existingPhrasesId) {
            pairExists = SQLCommands.checkPairUsingPhrase(id);
        }

        if (pairExists)
        {
            showAlert(Alert.AlertType.ERROR,"Данное ключевое слово используется парой фраза-перевод, пожалуйста, удалите эту пару перед удаление ключевого слова");
        }
        else if(!existingPhrasesId.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Выбранное ключевое слово используется");
            alert.setContentText("Выбранное ключевое слово используется следующими словами без перевода, удалить их?");
            VBox dialogPaneContent = new VBox();
            Label label = new Label("Фразы:");
            TextArea textArea = new TextArea();
            String phrasesToDelete = "";
            for (int id: existingPhrasesId) {
                phrasesToDelete = phrasesToDelete.concat(SQLCommands.getPhrase(id) + '\n');
            }
            textArea.setText(phrasesToDelete);

            dialogPaneContent.getChildren().addAll(label, textArea);
            alert.getDialogPane().setContent(dialogPaneContent);

            Optional<ButtonType> option = alert.showAndWait();

            if(option.get() == null) {
                errorLabel.setText("Не выбран ответ");
            } else if (option.get() == ButtonType.OK) {
                for (int id: existingPhrasesId) {
                    SQLCommands.deletePhrase(id);
                }

                SQLCommands.deleteKeyWord(idKeyWord);
                getData();
                errorLabel.setText("Ключевое слово удалено");
                errorLabel.setTextFill(Color.GREEN);
            } else if (option.get() == ButtonType.CANCEL) {
                errorLabel.setText("");
            }
        }
    }
}
