package Controllers;

import Misc.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import sample.TestModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

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

    public void addButtonClicked(ActionEvent actionEvent) throws SQLException {
        writeData();
    }

    private void getData() {
        events.clear();
        Connection conn = TestModel.getConnection();
        String sql = "SELECT eventTitle, eventDate, isAccurate FROM event";

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                if(!rs.getBoolean("isAccurate"))
                {
                    String eventDateTemp = rs.getString("eventDate");
                    events.add(
                            new Event(rs.getString("eventTitle"),
                                     eventDateTemp.substring(eventDateTemp.indexOf('.') + 1)));
                }
                else {
                    events.add(new Event(rs.getString("eventTitle"),
                            rs.getString("eventDate")));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void writeData() throws SQLException { //todo check fill
        Connection conn = TestModel.getConnection();
        String sqlAddEvent = "INSERT INTO event(eventTitle, eventDate, isAccurate) VALUES (?,?,?)";
        String sqlCheckExist = "SELECT eventTitle FROM event WHERE (eventTitle = ?)";

        boolean exists = false;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckExist)){
            pstmt.setString(1, eventText.getText());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                exists = true;
        }
        if (exists)
        {
            errorLabel.setText("Такое событие уже есть");
            errorLabel.setTextFill(Color.RED);
        } else {

            try (
                 PreparedStatement pstmt = conn.prepareStatement(sqlAddEvent)) {
                pstmt.setString(1, eventText.getText());
                if(accurateCheckBox.isSelected())
                {
                    pstmt.setString(2, eventDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                    pstmt.setString(3, "1");
                }
                else{
                    pstmt.setString(2, "01."+ monthComboBox.getValue().toString() +'.'+yearComboBox.getValue().toString());
                    pstmt.setString(3,"0");
                }
                pstmt.executeUpdate();

                getData();
                errorLabel.setText("Событие добавлено");
                errorLabel.setTextFill(Color.GREEN);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
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

    public void fillComboBox()
    {
        monthComboBox.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();
        int currentData = Integer.parseInt(dtf.format(now));
        for (int i=currentData; i>=1800; i--)
        {
            yearComboBox.getItems().add(i);
        }
    }

    public void deleteButtonClicked(ActionEvent actionEvent) throws SQLException {
        Connection conn = TestModel.getConnection();
        String sqlDelete = "DELETE FROM event WHERE (eventTitle = ?)";
        String sqlGetEventId = "SELECT idEvent FROM event WHERE (eventTitle = ?)";
        String sqlGetPair = "SELECT idEngPhrase FROM engRuTranslation WHERE (engRuTranslation.idEvent = ?)";
        int idEvent;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetEventId)) {
            pstmt.setString(1, eventTableView.getSelectionModel().getSelectedItem().getEventTitle());
            ResultSet rs = pstmt.executeQuery();
            idEvent = rs.getInt("idEvent");
        }

        boolean pairExists = false;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPair)) {
            pstmt.setString(1, String.valueOf(idEvent));
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                pairExists = true;
        }

        if (pairExists)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выбранное событие используется");
            alert.setContentText("Данное событие используется парой фраза-перевод, пожалуйста, сперва удалите эту пару");
            alert.showAndWait();
        } else {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
                pstmt.setString(1, eventTableView.getSelectionModel().getSelectedItem().getEventTitle());
                pstmt.executeUpdate();
            }
        }

        getData();
        errorLabel.setText("Событие удалено");
        errorLabel.setTextFill(Color.GREEN);
    }
}

