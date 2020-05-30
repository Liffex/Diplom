package controllers;

import misc.data.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import misc.sql.SQLQueriesStore;
import misc.sql.SQLCommands;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEventController {

    @FXML
    public TableView<Event> eventTableView;
    @FXML
    public TableColumn<Event, String> eventColumn;
    @FXML
    public TableColumn<Event, String> dateColumn;
    @FXML
    public Button deleteButton;
    @FXML
    public Label errorLabel;
    @FXML
    private CheckBox accurateCheckBox;

    @FXML
    private TextField eventText;

    @FXML
    private ComboBox<Integer> monthComboBox;

    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private DatePicker eventDate;

    ObservableList<Event> events = FXCollections.observableArrayList();
    @FXML
    void initialize() {
        getData();
        fillComboBox();
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("eventTitle"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));

        eventTableView.setItems(events);
    }

    public void addButtonClicked(ActionEvent actionEvent) {
        writeData();
    }

    private void getData() {
        events.clear();
        events.addAll(SQLQueriesStore.getEventList());
    }

    private void writeData() { //todo check fill
        boolean exists = SQLCommands.checkEvent(eventText.getText());

        if (exists) {
            errorLabel.setText("Такое событие уже есть");
            errorLabel.setTextFill(Color.RED);
        } else if (accurateCheckBox.isSelected()) {
            SQLCommands.addEvent(eventText.getText(), eventDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), accurateCheckBox.isSelected());
        } else {
            SQLCommands.addEvent(eventText.getText(), "01." + monthComboBox.getValue().toString() + '.' + yearComboBox.getValue().toString(), accurateCheckBox.isSelected());
        }
        getData();
        errorLabel.setText("Событие добавлено");
        errorLabel.setTextFill(Color.GREEN);
    }

    public void checkBoxChecked(ActionEvent actionEvent) {
        if (accurateCheckBox.isSelected())
        {
            monthComboBox.setVisible(false);
            yearComboBox.setVisible(false);
            eventDate.setVisible(true);
        }
        else {
            monthComboBox.setVisible(true);
            yearComboBox.setVisible(true);
            eventDate.setVisible(false);
        }
    }

    public void fillComboBox() {
        monthComboBox.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();
        int currentData = Integer.parseInt(dtf.format(now));
        for (int i=currentData; i>=1800; i--)
        {
            yearComboBox.getItems().add(i);
        }
    }

    public void deleteButtonClicked(ActionEvent actionEvent) {
     int idEvent = SQLCommands.getEventId(eventText.getText());
     boolean eventUsed = SQLCommands.checkEventInPair(idEvent);

     if (eventUsed)
     {
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Ошибка");
         alert.setHeaderText("Выбранное событие используется");
         alert.setContentText("Данное событие используется парой фраза-перевод, пожалуйста, сперва удалите эту пару");alert.showAndWait();
     } else {
         SQLCommands.deleteEvent(eventText.getText());
     }

     getData();
     errorLabel.setText("Событие удалено");
     errorLabel.setTextFill(Color.GREEN);
    }
}

