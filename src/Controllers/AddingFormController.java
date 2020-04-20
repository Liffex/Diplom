package Controllers;

import Misc.Reader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.TestModel;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class AddingFormController {

    @FXML
    public TextField sourceTitleText;
    @FXML
    public TextField sourceUrlText;
    @FXML
    public TextArea sourceDescriptionText;
    @FXML
    public Button importButton;
    @FXML
    public Label errorLabel;
    @FXML
    private ComboBox<String> keyWordComboBox;
    @FXML
    private ComboBox<String> eventComboBox;
    @FXML
    private TextField engPhraseText;
    @FXML
    private ComboBox<String> personComboBox;
    @FXML
    private TextArea contextText;
    @FXML
    private TextField ruTransText;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;

    @FXML
    void initialize() {
        fillComboBox();
        editButton.setVisible(false);
    }

    int idEngPhraseEdit;
    int idRuTranslationEdit;

    private void fillComboBox() {
        keyWordComboBox.getItems().clear();
        eventComboBox.getItems().clear();
        personComboBox.getItems().clear();
        Connection conn = TestModel.getConnection();
        String sqlKeyWord = "SELECT keyWord FROM keyWord";
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlKeyWord)) {
            while (rs.next()) {
                keyWordComboBox.getItems().addAll(rs.getString("keyWord"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sqlEvent = "SELECT eventTitle FROM event";
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlEvent)) {
            while (rs.next()) {
                eventComboBox.getItems().addAll(rs.getString("eventTitle"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sqlPerson = "SELECT personName FROM person";
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlPerson)) {
            while (rs.next()) {
                personComboBox.getItems().addAll(rs.getString("personName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addKeyWordClicked(ActionEvent actionEvent) throws IOException {
        Parent addTest = FXMLLoader.load(getClass().getResource("/fxml/AddKeyWordForm.fxml"));
        Scene scene = new Scene(addTest);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(addButton.getScene().getWindow());
        stage.setOnCloseRequest(windowEvent -> fillComboBox());
        stage.show();
    }

    public void addEventClicked(ActionEvent actionEvent) throws IOException {
        Parent addTest = FXMLLoader.load(getClass().getResource("/fxml/AddEventForm.fxml"));
        Scene scene = new Scene(addTest);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void addPersonClicked(ActionEvent actionEvent) throws IOException {
        Parent addTest = FXMLLoader.load(getClass().getResource("/fxml/AddPersonForm.fxml"));
        Scene scene = new Scene(addTest);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void addButtonClicked(ActionEvent actionEvent) {

        //todo Проверки
        Connection conn = TestModel.getConnection();
        int contextId = 0;
        int sourceId = 0;
        int ruTransId = 0;
        int engPhraseId = 0;

        String sqlAddContext = "INSERT INTO context(contextText) VALUES(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddContext, PreparedStatement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, contextText.getText());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) { contextId = rs.getInt(1);}

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlAddSource = "INSERT INTO source(sourceTitle, sourceURL, sourceDescription) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddSource, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, sourceTitleText.getText());
            pstmt.setString(2, sourceUrlText.getText());
            pstmt.setString(3, sourceDescriptionText.getText());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) { sourceId = rs.getInt(1);}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Get keyWord id
        int keyWordId = 0;
        String sqlGetKeyWordId = "SELECT idKeyWord FROM keyWord WHERE keyWord = '" + keyWordComboBox.getValue() + "'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlGetKeyWordId)){
            keyWordId = rs.getInt("idKeyWord");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Write engPhrase
        String sqlAddEngPhrase = "INSERT INTO engPhrase(engPhrase, idKeyWords) VALUES (?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddEngPhrase, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, engPhraseText.getText());
            pstmt.setString(2, Integer.toString(keyWordId));
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) { engPhraseId = rs.getInt(1);}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //write eu trans
        String sqlAddRuTrans = "INSERT INTO ruTranslation(ruTranslation) VALUES(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddRuTrans, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, ruTransText.getText());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) { ruTransId = rs.getInt(1);}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //get person id
        int personId = 0;
        String sqlGetPersonId = "SELECT idPerson FROM person WHERE personName = '" + personComboBox.getValue() + "'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlGetPersonId)) {
            personId = rs.getInt("idPerson");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //get event id
        int eventId = 0;
        String sqlGetEventId = "SELECT idEvent FROM event WHERE eventTitle = '" + eventComboBox.getValue() + "'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlGetEventId)) {
            eventId = rs.getInt("idEvent");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //insert engRuTrans
        String sqlAddEngRuTrans = "INSERT INTO engRuTranslation" +
                "(idEngPhrase, idRuTranslation, idSource, idEvent, idPerson, idContext) " +
                "VALUES( ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddEngRuTrans)) {
            pstmt.setString(1, String.valueOf(engPhraseId));
            pstmt.setString(2, String.valueOf(ruTransId));
            pstmt.setString(3, String.valueOf(sourceId));
            pstmt.setString(4, String.valueOf(eventId));
            pstmt.setString(5, String.valueOf(personId));
            pstmt.setString(6, String.valueOf(contextId));
            pstmt.executeUpdate();
        } catch (SQLException e) {e.printStackTrace();}

    }

    public void importButtonClicked(ActionEvent actionEvent) throws SQLException {
        Reader reader = new Reader();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл импорта");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLSX","*.xlsx"));
        File file = fileChooser.showOpenDialog(importButton.getScene().getWindow());
        System.out.println(file.getPath());
        reader.importData(file.getPath());
    }

    public void setEditingMode(int idEngPhrase, int idRuTranslation) throws SQLException {
        Connection conn = TestModel.getConnection();
        engPhraseText.setEditable(false);
        ruTransText.setEditable(false);
        addButton.setVisible(false);
        editButton.setVisible(true);
        importButton.setVisible(false);
        idRuTranslationEdit = idRuTranslation;
        idEngPhraseEdit = idEngPhrase;
        int idPhrase;
        int idTranslation;
        int idEvent;
        int idPerson;
        int idContext;
        int idSource;

        idPhrase = idEngPhrase;
        idTranslation = idRuTranslation;

        String sqlGetInfo = "SELECT idEvent, idPerson, idContext, idSource FROM engRuTranslation" +
                " WHERE ((idEngPhrase = ?) AND (idRuTranslation = ?))";
        String getPhrase = "SELECT engPhrase FROM engPhrase WHERE (idEngPhrase = ?)";
        String getTranslation = "SELECT ruTranslation FROM ruTranslation WHERE (idRuTranslation = ?)";
        String sqlGetKeyWord = "SELECT keyWord FROM engPhrase JOIN keyWord ON (engPhrase.idKeyWords = keyWord.idKeyWord) " +
                "WHERE (engPhrase.idEngPhrase = ?)";
        String sqlGetEventData = "SELECT eventTitle, eventDate, isAccurate FROM event WHERE (idEvent = ?)";
        String sqlGetSourceData = "SELECT sourceTitle, sourceURL, sourceDescription FROM source WHERE (idSource = ?)";
        String sqlGetContextData = "SELECT contextText FROM context WHERE (idContext = ?)";
        String sqlGetPersonData = "SELECT personName FROM person WHERE (idPerson = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetInfo)) {
            pstmt.setString(1, String.valueOf(idPhrase));
            pstmt.setString(2,String.valueOf(idTranslation));
            ResultSet rs = pstmt.executeQuery();
            idEvent =  rs.getInt("idEvent");
            idPerson = rs.getInt("idPerson");
            idContext = rs.getInt("idContext");
            idSource = rs.getInt("idSource");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(getPhrase)) {
            pstmt.setString(1, String.valueOf(idPhrase));
            ResultSet rs = pstmt.executeQuery();
            engPhraseText.setText(rs.getString("engPhrase"));
        }

        try (PreparedStatement pstmt = conn.prepareStatement(getTranslation)){
            pstmt.setString(1, String.valueOf(idTranslation));
            ResultSet rs = pstmt.executeQuery();
            ruTransText.setText(rs.getString("ruTranslation"));
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetKeyWord)){
            pstmt.setString(1, String.valueOf(idPhrase));
            ResultSet rs = pstmt.executeQuery();
            keyWordComboBox.setValue(rs.getString("keyWord"));
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetEventData)){
            pstmt.setString(1, String.valueOf(idEvent));
            ResultSet rs = pstmt.executeQuery();
            eventComboBox.setValue(rs.getString("eventTitle"));
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetSourceData)) {
            pstmt.setString(1, String.valueOf(idSource));
            ResultSet rs = pstmt.executeQuery();

            sourceDescriptionText.setText(rs.getString("sourceDescription"));
            sourceTitleText.setText(rs.getString("sourceTitle"));
            sourceUrlText.setText(rs.getString("sourceURL"));
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetContextData)) {
            pstmt.setString(1, String.valueOf(idContext));
            ResultSet rs = pstmt.executeQuery();
            contextText.setText(rs.getString("contextText"));
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPersonData)) {
            pstmt.setString(1, String.valueOf(idPerson));
            ResultSet rs = pstmt.executeQuery();
            personComboBox.setValue(rs.getString("personName"));
        }
    }

    public void editButtonClicked(ActionEvent actionEvent) throws SQLException { //todo add event date confirmation
        Connection conn = TestModel.getConnection();

        String sqlCheckPhrase = "SELECT idEngPhrase FROM engPhrase WHERE (engPhrase = ?)";
        String sqlAddPhrase = "INSERT INTO engPhrase(engPhrase, idKeyWords) VALUES (?, ?)";
        String sqlCheckTranslation = "SELECT idRuTranslation FROM ruTranslation WHERE (ruTranslation = ?)";
        String sqlAddTranslation = "INSERT INTO ruTranslation(ruTranslation) VALUES (?)";
        String sqlCheckSource = "SELECT idSource, sourceTitle, sourceURL, sourceDescription FROM source WHERE ((sourceTitle = ?) AND (sourceURL = ?) AND (sourceDescription = ?))";
        String sqlAddSource = "INSERT INTO source(sourceTitle, sourceURL, sourceDescription) VALUES(?,?,?)";
        String sqlGetEventId = "SELECT idEvent FROM event WHERE (eventTitle = ?)";
        String sqlGetPersonId = "SELECT idPerson FROM person WHERE (personName = ?)";
        String sqlCheckContext = "SELECT idContext FROM context WHERE (contextText = ?)";
        String sqlAddContext = "INSERT INTO context(contextText) VALUES (?)";
        String sqlGetKeyWordId = "SELECT idKeyWord FROM keyWord WHERE (keyWord = ?)";
        String sqlCheckPair = "SELECT * FROM engRuTranslation WHERE ((idEngPhrase = ?) AND (idRuTranslation = ?))";

        String sqlUpdate = "UPDATE engRuTranslation SET idEngPhrase = ?, " +
                "idRuTranslation = ?" +
                "idSource = ?, " +
                "idEvent = ?, " +
                "idPerson = ?, " +
                "idContext = ? WHERE (( idEngPhrase = ? ) AND ( idRuTranslation = ?))";

        int idKeyWord;
        int idEngPhrase;
        int idRuTranslation;
        int idSource;
        int idEvent;
        int idPerson;
        int idContext;

        //get key word id
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetKeyWordId)) {
            pstmt.setString(1, keyWordComboBox.getValue());
            ResultSet rs = pstmt.executeQuery();
            idKeyWord = rs.getInt("idKeyWord");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPhrase)) {
            pstmt.setString(1, engPhraseText.getText());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
                idEngPhrase = rs.getInt("idEngPhrase");
            else
            {
                try (PreparedStatement pstmt1 = conn.prepareStatement(sqlAddPhrase, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    pstmt1.setString(1, engPhraseText.getText());
                    pstmt1.setString(2, String.valueOf(idKeyWord));
                    pstmt1.executeUpdate();
                    ResultSet rs1 = pstmt1.getGeneratedKeys();
                    idEngPhrase = rs1.getInt(1);
                }
            }
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckSource)) {
            pstmt.setString(1, sourceTitleText.getText());
            pstmt.setString(2, sourceUrlText.getText());
            pstmt.setString(3, sourceDescriptionText.getText());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
                idSource = rs.getInt("idSource");
            else
            {
                try (PreparedStatement pstmt1 = conn.prepareStatement(sqlAddSource, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    pstmt1.setString(1, sourceTitleText.getText());
                    pstmt1.setString(2, sourceUrlText.getText());
                    pstmt1.setString(3, sourceDescriptionText.getText());
                    pstmt1.executeUpdate();
                    ResultSet rs1 = pstmt1.getGeneratedKeys();
                    idSource = rs1.getInt(1);
                }
            }
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckTranslation)) {
            pstmt.setString(1, ruTransText.getText());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
                idRuTranslation = rs.getInt("idRuTranslation");
            else
            {
                try (PreparedStatement pstmt1 = conn.prepareStatement(sqlAddTranslation, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    pstmt1.setString(1, ruTransText.getText());
                    pstmt1.executeUpdate();
                    ResultSet rs1 = pstmt1.getGeneratedKeys();
                    idRuTranslation = rs1.getInt(1);
                }
            }
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetEventId)) {
            pstmt.setString(1, eventComboBox.getValue());
            ResultSet rs = pstmt.executeQuery();
            idEvent = rs.getInt("idEvent");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPersonId)) {
            pstmt.setString(1, personComboBox.getValue());
            ResultSet rs = pstmt.executeQuery();
            idPerson = rs.getInt("idPerson");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckContext)) {
            pstmt.setString(1, contextText.getText());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
                idContext = rs.getInt("idContext");
            else
            {
                try (PreparedStatement pstmt1 = conn.prepareStatement(sqlAddContext, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    pstmt1.setString(1, contextText.getText());
                    pstmt1.executeUpdate();
                    ResultSet rs1 = pstmt1.getGeneratedKeys();
                    idContext = rs1.getInt(1);
                }
            }
        }

        boolean pairExists = false;

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPair)) {
            pstmt.setInt(1,idEngPhrase);
            pstmt.setInt(2, idRuTranslation);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                pairExists = true;
        }

        if(!pairExists) {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
                pstmt.setInt(1, idEngPhrase);
                pstmt.setInt(2, idRuTranslation);
                pstmt.setInt(3, idSource);
                pstmt.setInt(4, idEvent);
                pstmt.setInt(5, idPerson);
                pstmt.setInt(6, idContext);
                pstmt.setInt(7, idEngPhraseEdit);
                pstmt.setInt(8, idRuTranslationEdit);

                pstmt.executeUpdate();
            }
        } else
        {
            errorLabel.setText("Такая пара уже существует");
            errorLabel.setTextFill(Color.RED);
        }
    }
}
