package controllers;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
import java.time.LocalDate;
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
    public Button addButton;
    @FXML
    public Button saveButton;
    @FXML
    public Button clearButton;
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
        eventTableView.getItems().clear();
        events.clear();
        events.addAll(SQLQueriesStore.getEventList());
    }

    private void writeData() {
        if(eventDate.getValue() == null || monthComboBox.getValue() == null || yearComboBox.getValue() == null) {
            errorLabel.setText("Не выбрана дата");
            errorLabel.setTextFill(Color.RED);
            return;
        }


        String eventDateString;
        if (accurateCheckBox.isSelected())
            eventDateString = eventDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        else
            eventDateString = "01." + monthComboBox.getValue().toString() + '.' + yearComboBox.getValue().toString();

        boolean exists = SQLCommands.checkEvent(eventText.getText(), eventDateString);

        if (exists) {
            errorLabel.setText("Такое событие уже есть");
            errorLabel.setTextFill(Color.RED);
        } else {
            SQLCommands.addEvent(eventText.getText(), eventDateString, accurateCheckBox.isSelected());
            getData();
            errorLabel.setText("Событие добавлено");
            errorLabel.setTextFill(Color.GREEN);
        }
    }

    public void checkBoxChecked(ActionEvent actionEvent) {
        if (accurateCheckBox.isSelected()) {
            monthComboBox.setVisible(false);
            yearComboBox.setVisible(false);
            eventDate.setVisible(true);
        } else {
            monthComboBox.setVisible(true);
            yearComboBox.setVisible(true);
            eventDate.setVisible(false);
        }
    }

    public void fillComboBox() {
        monthComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();
        int currentData = Integer.parseInt(dtf.format(now));
        for (int i = currentData; i >= 1800; i--) {
            yearComboBox.getItems().add(i);
        }
    }

    public void deleteButtonClicked(ActionEvent actionEvent) {
        //    int idEvent = SQLCommands.getEventIdFull(eventTableView.getSelectionModel().getSelectedItem().getEventTitle(),
        //            eventTableView.getSelectionModel().getSelectedItem().getEventDate());
        int idEvent = eventTableView.getSelectionModel().getSelectedItem().getEventId();
        boolean eventUsed = SQLCommands.checkEventInPair(idEvent);

        if (eventUsed) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выбранное событие используется");
            alert.setContentText("Данное событие используется парой фраза-перевод, пожалуйста, сперва удалите эту пару");
            alert.showAndWait();
        } else {
            SQLCommands.deleteEventById(idEvent);
        }

        getData();
        errorLabel.setText("Событие удалено");
        errorLabel.setTextFill(Color.GREEN);
    }

    public void fillSelectedData(MouseEvent mouseEvent) {
        eventText.setText(eventTableView.getSelectionModel().getSelectedItem().getEventTitle());
        if (!eventTableView.getSelectionModel().getSelectedItem().getEventDate().equals("Не задано")) {
            if (eventTableView.getSelectionModel().getSelectedItem().getEventDate().split("\\.").length == 2) {
                if (accurateCheckBox.isSelected()) {
                    accurateCheckBox.fire();
                    eventDate.getEditor().clear();
                }
                monthComboBox.setValue(Integer.parseInt(eventTableView.getSelectionModel().getSelectedItem().getEventDate().split("\\.")[0]));
                yearComboBox.setValue(Integer.parseInt(eventTableView.getSelectionModel().getSelectedItem().getEventDate().split("\\.")[1]));
            }
            else {
                if (!accurateCheckBox.isSelected()) {
                    accurateCheckBox.fire();
                    monthComboBox.getSelectionModel().clearSelection();
                    yearComboBox.getSelectionModel().clearSelection();
                }
                eventDate.setValue(LocalDate.parse(eventTableView.getSelectionModel().getSelectedItem().getFullDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
            addButton.setVisible(false);
            saveButton.setVisible(true);
            clearButton.setVisible(true);
        }
        else {
            eventDate.getEditor().clear();
            monthComboBox.getSelectionModel().clearSelection();
            yearComboBox.getSelectionModel().clearSelection();
        }
    }

    public void clearButtonClicked(ActionEvent actionEvent) {
        eventText.clear();
        eventDate.getEditor().clear();
        monthComboBox.getSelectionModel().clearSelection();
        yearComboBox.getSelectionModel().clearSelection();
        eventTableView.getSelectionModel().clearSelection();
        saveButton.setVisible(false);
        clearButton.setVisible(false);
        addButton.setVisible(true);
    }

    public void saveButtonClicked(ActionEvent actionEvent) {
        if((eventDate.getValue() == null && accurateCheckBox.isSelected()) || ((monthComboBox.getValue() == null || yearComboBox.getValue() == null) && !accurateCheckBox.isSelected())) {
            errorLabel.setText("Не выбрана дата");
            errorLabel.setTextFill(Color.RED);
            return;
        }


        String eventDateString;
        if (accurateCheckBox.isSelected())
            eventDateString = eventDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        else
            if (monthComboBox.getValue() < 10) {
                eventDateString = "01.0" + monthComboBox.getValue().toString() + '.' + yearComboBox.getValue().toString();
            }
            else {
                eventDateString = "01." + monthComboBox.getValue().toString() + '.' + yearComboBox.getValue().toString();
            }

        boolean exists = SQLCommands.checkEvent(eventText.getText(), eventDateString);

        if (exists) {
            errorLabel.setText("Такое событие уже есть");
            errorLabel.setTextFill(Color.RED);
        } else {
            SQLCommands.updateEvent(eventTableView.getSelectionModel().getSelectedItem().getEventId(), eventText.getText(), eventDateString, accurateCheckBox.isSelected());
            getData();
            errorLabel.setText("Событие обновлено");
            errorLabel.setTextFill(Color.GREEN);
        }

        eventText.clear();
        eventDate.getEditor().clear();
        monthComboBox.getSelectionModel().clearSelection();
        yearComboBox.getSelectionModel().clearSelection();
        eventTableView.getSelectionModel().clearSelection();
        saveButton.setVisible(false);
        clearButton.setVisible(false);
        addButton.setVisible(true);
    }
}

