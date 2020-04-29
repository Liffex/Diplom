package controllers;

import misc.FileHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import misc.SQLCommands;
import db.TestModel;

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
        stage.setOnCloseRequest(windowEvent -> fillComboBox());
        stage.show();
    }

    public void addPersonClicked(ActionEvent actionEvent) throws IOException {
        Parent addTest = FXMLLoader.load(getClass().getResource("/fxml/AddPersonForm.fxml"));
        Scene scene = new Scene(addTest);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> fillComboBox());
        stage.show();
    }

    public void addButtonClicked(ActionEvent actionEvent) throws SQLException {

        //todo Проверки
        Connection conn = TestModel.getConnection();

        int contextId = SQLCommands.addContextText(contextText.getText());
        int sourceId = SQLCommands.addSource(sourceTitleText.getText(), sourceUrlText.getText(), sourceDescriptionText.getText());
        int keyWordId = SQLCommands.getKeyWordId(keyWordComboBox.getValue());

        int engPhraseId = SQLCommands.addPhrase(engPhraseText.getText(), keyWordId);
        int ruTransId = SQLCommands.addTranslation(ruTransText.getText());

        int personId = SQLCommands.getPersonId(personComboBox.getValue());
        int eventId = SQLCommands.getEventId(eventComboBox.getValue());

        SQLCommands.addPair(engPhraseId, ruTransId, sourceId, eventId, personId, contextId);
    }

    public void setEditingMode(int idEngPhrase, int idRuTranslation) throws SQLException {
        //engPhraseText.setEditable(false);
        //ruTransText.setEditable(false);
        addButton.setVisible(false);
        editButton.setVisible(true);
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
        idPerson = SQLCommands.getPersonIdFromPair(idPhrase, idTranslation);
        idEvent = SQLCommands.getEventIdFromPair(idPhrase, idTranslation);
        idContext = SQLCommands.getContextIdFromPair(idPhrase, idTranslation);
        idSource =  SQLCommands.getSourceIdFromPair(idPhrase, idTranslation);

        engPhraseText.setText(SQLCommands.getPhrase(idPhrase));
        ruTransText.setText(SQLCommands.getTranslation(idTranslation));
        keyWordComboBox.setValue(SQLCommands.getPhraseKeyWord(idPhrase));
        eventComboBox.setValue(SQLCommands.getEventTitle(idEvent));
        sourceDescriptionText.setText(SQLCommands.getSourceDescription(idSource));
        sourceTitleText.setText(SQLCommands.getSourceTitle(idSource));
        sourceUrlText.setText(SQLCommands.getSourceURL(idSource));
        contextText.setText(SQLCommands.getContextText(idContext));
        personComboBox.setValue(SQLCommands.getPersonName(idPerson));
    }

    public void editButtonClicked(ActionEvent actionEvent) throws SQLException { //todo add event date confirmation
        Connection conn = TestModel.getConnection();

        int idKeyWord;
        int idEngPhrase;
        int idRuTranslation;
        int idSource;
        int idEvent;
        int idPerson;
        int idContext;

        idKeyWord = SQLCommands.getKeyWordId(keyWordComboBox.getValue());
        idEvent = SQLCommands.getEventId(eventComboBox.getValue());
        idPerson = SQLCommands.getPersonId(personComboBox.getValue());

        if(SQLCommands.checkPhrase(engPhraseText.getText())) {
            idEngPhrase = SQLCommands.getPhraseId(engPhraseText.getText());
        } else {
            idEngPhrase = SQLCommands.addPhrase(engPhraseText.getText(), idKeyWord);
        }

        if(SQLCommands.checkSource(sourceTitleText.getText(), sourceUrlText.getText(), sourceDescriptionText.getText()))
        {
            idSource = SQLCommands.getSourceIdFullCompare(sourceTitleText.getText(), sourceUrlText.getText(), sourceDescriptionText.getText());
        } else {
            idSource = SQLCommands.addSource(sourceTitleText.getText(), sourceUrlText.getText(), sourceDescriptionText.getText());
        }

        if(SQLCommands.checkTranslation(ruTransText.getText())) {
            idRuTranslation = SQLCommands.getTranslationId(ruTransText.getText());
        } else {
            idRuTranslation = SQLCommands.addTranslation(ruTransText.getText());
        }

        if(SQLCommands.checkContext(contextText.getText())) {
            idContext = SQLCommands.getContextId(contextText.getText());
        } else {
            idContext = SQLCommands.addContextText(contextText.getText());
        }

        SQLCommands.updatePair(idEngPhrase, idRuTranslation, idSource, idEvent, idPerson, idContext, idEngPhraseEdit, idRuTranslationEdit);
    }
}
