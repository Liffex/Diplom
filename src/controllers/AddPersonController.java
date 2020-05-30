package controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import misc.sql.SQLQueriesStore;
import misc.sql.SQLCommands;

import java.sql.*;

public class AddPersonController {

    @FXML
    public Button deletePersonButton;
    @FXML
    public Label errorLabel;
    @FXML
    public TableColumn<String, String> personColumn;
    @FXML
    public TableView<String> personTableView;
    @FXML
    private TextField personText;

    private ObservableList<String> persons = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        getData();
        personColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        personTableView.setItems(persons);
    }

    @FXML
    void addPersonButtonClicked(ActionEvent event) {
        writeData();
    }

    private void getData() {
        persons.clear();
        persons.addAll(SQLQueriesStore.getPersonList());
    }

    private void writeData() {
        if (persons.contains(personText.getText())) {
            errorLabel.setText("Такая персона уже есть");
            errorLabel.setTextFill(Color.RED);
        } else {
            SQLCommands.addPerson(personText.getText());
            errorLabel.setText("Персона добавлена!");
            errorLabel.setTextFill(Color.GREEN);
            getData();
        }
    }

    public void deletePersonButtonClicked(ActionEvent actionEvent) {
        boolean pairExists = SQLCommands.checkPairPerson(personTableView.getSelectionModel().getSelectedItem());

        if(pairExists)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выбранное персона используется");
            alert.setContentText("Данная персона используется парой фраза-перевод, пожалуйста, сперва удалите эту пару");
            alert.showAndWait();
        } else {
            SQLCommands.deletePerson(personTableView.getSelectionModel().getSelectedItem());
            getData();
            errorLabel.setText("Персона удалена");
            errorLabel.setTextFill(Color.GREEN);
        }
    }
}
