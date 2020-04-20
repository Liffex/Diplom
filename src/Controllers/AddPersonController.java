package Controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import sample.TestModel;

import java.sql.*;
import java.util.Vector;

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

    @FXML
    private Button addPersonButton;

    private ObservableList<String> persons = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        getData();
        personColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        personTableView.setItems(persons);
    }

    @FXML
    void addPersonButtonClicked(ActionEvent event) throws SQLException {
        writeData();
    }

    private void getData() {
        persons.clear();
        Connection conn = TestModel.getConnection();
        String sql = "SELECT personName FROM person";

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                persons.add(rs.getString("personName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void writeData() throws SQLException { //todo AddDate
        Connection conn = TestModel.getConnection();
        String sqlAddPerson = "INSERT INTO person(PersonName) VALUES (?)";

        if (persons.contains(personText.getText())) {
            errorLabel.setText("Такая персона уже есть");
            errorLabel.setTextFill(Color.RED);
        } else {
            try (
                    PreparedStatement pstmt = conn.prepareStatement(sqlAddPerson)) {
                pstmt.setString(1, personText.getText());
                pstmt.executeUpdate();
            } catch (SQLException e) {System.out.println(e.getMessage());}

            errorLabel.setText("Персона добавлена!");
            errorLabel.setTextFill(Color.GREEN);
            getData();
        }
    }

    public void deletePersonButtonClicked(ActionEvent actionEvent) throws SQLException {
        Connection conn = TestModel.getConnection();
        String sqlDelete = "DELETE FROM person WHERE (personName = ?)";
        String getPersonId = "SELECT idPerson FROM person WHERE (personName = ?)";
        String sqlCheckPair = "SELECT idPerson FROM engRuTranslation WHERE (engRuTranslation.idPerson = ?)";
        int idPerson;

        try (PreparedStatement pstmt = conn.prepareStatement(getPersonId)) {
            pstmt.setString(1, personTableView.getSelectionModel().getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            idPerson = rs.getInt("idPerson");
        }

        boolean pairExists = false;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPair)) {
            pstmt.setString(1, String.valueOf(idPerson));
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                pairExists = true;
        }

        if(pairExists)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выбранное персона используется");
            alert.setContentText("Данная персона используется парой фраза-перевод, пожалуйста, сперва удалите эту пару");
            alert.showAndWait();
        }else
        {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
                pstmt.setString(1,personTableView.getSelectionModel().getSelectedItem());
                pstmt.executeUpdate();
            }
            getData();
            errorLabel.setText("Персона удалена");
            errorLabel.setTextFill(Color.GREEN);
        }
    }
}
