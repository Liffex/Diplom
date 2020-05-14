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

import java.sql.SQLException;

public class AddTypeController {

    @FXML
    public TableColumn<String, String> typeColumn;
    @FXML
    public Button deleteButton;
    @FXML
    public TableView<String> typeTableView;
    @FXML
    public Label errorLabel;
    @FXML
    private TextField typeText;

    private ObservableList<String> types = FXCollections.observableArrayList();
    @FXML
    void initialize() throws SQLException {
        getData();
        typeColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        typeTableView.setItems(types);
    }

    @FXML
    void addTypeButtonClicked(ActionEvent event) throws SQLException {
        writeData();
    }

    private void getData() throws SQLException {
        types.clear();
        types.addAll(SQLQueriesStore.getTypesList());
    }

    private void writeData() throws SQLException {
        if (types.stream().anyMatch(typeText.getText()::equalsIgnoreCase)) {
            errorLabel.setText("Такой тип уже есть");
            errorLabel.setTextFill(Color.RED);
        } else {
            SQLCommands.addType(typeText.getText());
            errorLabel.setText("Тип добавлен!");
            errorLabel.setTextFill(Color.GREEN);
            getData();
        }
    }

    @FXML
    private void deleteButtonClicked(ActionEvent actionEvent) throws SQLException {
        boolean pairExists = SQLCommands.checkPairType(typeTableView.getSelectionModel().getSelectedItem());

        if(pairExists)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выбранный тип используется");
            alert.setContentText("Данная тип используется парой фраза-перевод, пожалуйста, сперва удалите эту пару");
            alert.showAndWait();
        } else {
            SQLCommands.deleteType(typeTableView.getSelectionModel().getSelectedItem());
            getData();
            errorLabel.setText("Тип удален");
            errorLabel.setTextFill(Color.GREEN);
        }
    }
}
