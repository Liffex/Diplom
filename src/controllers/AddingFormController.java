package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import misc.data.Event;
import misc.sql.SQLCommands;
import db.DBConnection;
import misc.sql.SQLQueriesStore;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddingFormController {

    private static Logger log = Logger.getLogger(AddingFormController.class.getName());

    @FXML
    public TextField sourceTitleText;
    @FXML
    public TextField sourceUrlText;
    @FXML
    public TextArea sourceDescriptionText;
    @FXML
    public Label errorLabel;
    public TextField dateTextField;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private ComboBox<String> keyWordComboBox;
    @FXML
    private ComboBox<Event> eventComboBox;
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

    private ObservableList<Event> events = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        fillComboBox();
        editButton.setVisible(false);
    }

    int idPairG;

    private void fillComboBox() {
        keyWordComboBox.getItems().clear();
        eventComboBox.getItems().clear();
        personComboBox.getItems().clear();
        typeComboBox.getItems().clear();

        events = SQLQueriesStore.getEventList();
        eventComboBox.setItems(events);
        keyWordComboBox.getItems().addAll(SQLQueriesStore.getKeyWordList());
        personComboBox.getItems().addAll(SQLQueriesStore.getPersonList());
        typeComboBox.getItems().addAll(SQLQueriesStore.getTypesList());


    }

    public void addKeyWordClicked(ActionEvent actionEvent) {
        Parent addTest = null;
        try {
            addTest = FXMLLoader.load(getClass().getResource("/fxml/AddKeyWordForm.fxml"));
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        Scene scene = new Scene(addTest);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(addButton.getScene().getWindow());
        stage.setOnCloseRequest(windowEvent -> {
            keyWordComboBox.getItems().clear();
            keyWordComboBox.getItems().addAll(SQLQueriesStore.getKeyWordList());
        });
        stage.setTitle("Добавление ключевого слова");
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        stage.show();
    }

    public void addEventClicked(ActionEvent actionEvent) {
        Parent addTest = null;
        try {
            addTest = FXMLLoader.load(getClass().getResource("/fxml/AddEventForm.fxml"));
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        Scene scene = new Scene(addTest);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            eventComboBox.getItems().clear();
            dateTextField.clear();
            eventComboBox.getItems().addAll(SQLQueriesStore.getEventList());
        });
        stage.setTitle("Добавление событий");
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        stage.show();
    }

    public void addPersonClicked(ActionEvent actionEvent) {
        Parent addTest = null;
        try {
            addTest = FXMLLoader.load(getClass().getResource("/fxml/AddPersonForm.fxml"));
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        assert addTest != null;
        Scene scene = new Scene(addTest);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            personComboBox.getItems().clear();
            personComboBox.getItems().addAll(SQLQueriesStore.getPersonList());
        });
        stage.setTitle("Добавление персон");
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        stage.show();
    }

    public void addButtonClicked(ActionEvent actionEvent) {
        int contextId;

        if(engPhraseText.getText().trim().length() == 0 ||
                ruTransText.getText().trim().length() == 0 ||
                keyWordComboBox.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка заполнения");
            alert.setHeaderText("Пожалуйста, заполните поля");
            alert.setContentText("Заполните поля: фраза, перево, ключевое слово");
            alert.showAndWait();
            return;
        }


        if (!contextText.getText().isEmpty()) {
            if(SQLCommands.checkContext(contextText.getText()))
                contextId = SQLCommands.getContextId(contextText.getText());
            else
                contextId = SQLCommands.addContextText(contextText.getText());
        } else
            contextId = SQLCommands.getContextId("Не задано");

        String sourceTitle;
        String sourceUrl;
        String sourceDesc;
        if(sourceTitleText.getText().isEmpty())
            sourceTitle = "Не задано";
        else sourceTitle = sourceTitleText.getText();

        if(sourceUrlText.getText().isEmpty())
            sourceUrl = "Не задано";
        else sourceUrl = sourceUrlText.getText();

        if (sourceDescriptionText.getText().isEmpty())
            sourceDesc = "Не задано";
        else sourceDesc = sourceDescriptionText.getText();

        boolean existsSrc = false;
        existsSrc = SQLCommands.checkSource(sourceTitle, sourceUrl, sourceDesc);

        int sourceId;

        if(!existsSrc)
            sourceId = SQLCommands.addSource(sourceTitle, sourceUrl, sourceDesc);
        else sourceId = SQLCommands.getSourceIdFullCompare(sourceTitle, sourceUrl, sourceDesc);

        int keyWordId = SQLCommands.getKeyWordId(keyWordComboBox.getValue());

        int engPhraseId = SQLCommands.addPhrase(engPhraseText.getText(), keyWordId);
        int ruTransId = SQLCommands.addTranslation(ruTransText.getText());

        int personId;
        if (!personComboBox.getSelectionModel().isEmpty()) {
            personId = SQLCommands.getPersonId(personComboBox.getValue());
        } else {
            personId = SQLCommands.getPersonId("Не задано");
        }

        int eventId;
        if (!eventComboBox.getSelectionModel().isEmpty()) {
            eventId = eventComboBox.getValue().getEventId();
        } else {
            eventId = SQLCommands.getEventId("Не задано");
        }

        int typeId;
        if (!typeComboBox.getSelectionModel().isEmpty()) {
            typeId = SQLCommands.getTypeId(typeComboBox.getValue());
        } else {
            typeId = SQLCommands.getTypeId("Не задано");
        }

        if(SQLCommands.checkPairFull(engPhraseId, ruTransId, sourceId, eventId,personId,contextId, typeId)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Такой элемент уже есть");
            alert.showAndWait();
        } else {
            SQLCommands.addPair(engPhraseId, ruTransId, sourceId, eventId, personId, contextId, typeId);
            Stage window = (Stage)addButton.getScene().getWindow();
            window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
        }

    }

    public void setEditingMode(int idPair) {
        //engPhraseText.setEditable(false);
        //ruTransText.setEditable(false);
        idPairG = idPair;
        addButton.setVisible(false);
        editButton.setVisible(true);
        int idPhrase;
        int idTranslation;
        int idEvent;
        int idPerson;
        int idContext;
        int idSource;
        int idType;

        idPhrase = SQLCommands.getPhraseIdFromPair(idPair);
        idTranslation = SQLCommands.getTranslationIdFromPair(idPair);
        idPerson = SQLCommands.getPersonIdFromPair(idPair);
        idEvent = SQLCommands.getEventIdFromPair(idPair);
        idContext = SQLCommands.getContextIdFromPair(idPair);
        idSource =  SQLCommands.getSourceIdFromPair(idPair);
        idType = SQLCommands.getTypeIdFromPair(idPair);

        engPhraseText.setText(SQLCommands.getPhrase(idPhrase));
        ruTransText.setText(SQLCommands.getTranslation(idTranslation));
        keyWordComboBox.setValue(SQLCommands.getPhraseKeyWord(idPhrase));
        eventComboBox.setValue(SQLCommands.getEvent(idEvent));
        sourceDescriptionText.setText(SQLCommands.getSourceDescription(idSource));
        sourceTitleText.setText(SQLCommands.getSourceTitle(idSource));
        sourceUrlText.setText(SQLCommands.getSourceURL(idSource));
        contextText.setText(SQLCommands.getContextText(idContext));
        personComboBox.setValue(SQLCommands.getPersonName(idPerson));
        typeComboBox.setValue(SQLCommands.getTypeTitle(idType));
        dateTextField.setText(eventComboBox.getValue().getEventDate());
    }

    public void editButtonClicked(ActionEvent actionEvent) { //todo add event date confirmation
        int idKeyWord;
        int idEngPhrase;
        int idRuTranslation;
        int idSource;
        int idEvent;
        int idPerson;
        int idContext;
        int idType;

        if(engPhraseText.getText().trim().length() == 0 ||
                ruTransText.getText().trim().length() == 0 ||
                keyWordComboBox.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка заполнения");
            alert.setHeaderText("Пожалуйста, заполните поля");
            alert.setContentText("Заполните поля: фраза, перевод, ключевое слово");
            alert.showAndWait();
            return;
        }

        idKeyWord = SQLCommands.getKeyWordId(keyWordComboBox.getValue());

        if(!eventComboBox.getSelectionModel().isEmpty()) {
            idEvent = eventComboBox.getValue().getEventId();
        } else {
            idEvent = SQLCommands.getEventId("Не задано");
        }

        if(!personComboBox.getSelectionModel().isEmpty()) {
            idPerson = SQLCommands.getPersonId(personComboBox.getValue());
        } else {
            idPerson = SQLCommands.getPersonId("Не задано");
        }

        if(!typeComboBox.getSelectionModel().isEmpty()) {
            idType = SQLCommands.getTypeId(typeComboBox.getValue());
        } else {
            idType = SQLCommands.getTypeId("Не задано");
        }

        if(SQLCommands.checkPhrase(engPhraseText.getText())) {
            idEngPhrase = SQLCommands.getPhraseId(engPhraseText.getText());
        } else {
            idEngPhrase = SQLCommands.addPhrase(engPhraseText.getText(), idKeyWord);
        }

        String sourceTitle;
        String sourceUrl;
        String sourceDesc;

        if(sourceTitleText.getText().isEmpty())
            sourceTitle = "Не задано";
        else sourceTitle = sourceTitleText.getText();

        if(sourceUrlText.getText().isEmpty())
            sourceUrl = "Не задано";
        else sourceUrl = sourceUrlText.getText();

        if (sourceDescriptionText.getText().isEmpty())
            sourceDesc = "Не задано";
        else sourceDesc = sourceDescriptionText.getText();

        if(SQLCommands.checkSource(sourceTitle, sourceUrl, sourceDesc))
        {
            idSource = SQLCommands.getSourceIdFullCompare(sourceTitle, sourceUrl, sourceDesc);
        } else {
            idSource = SQLCommands.addSource(sourceTitle, sourceUrl, sourceDesc);
        }

        if(SQLCommands.checkTranslation(ruTransText.getText())) {
            idRuTranslation = SQLCommands.getTranslationId(ruTransText.getText());
        } else {
            idRuTranslation = SQLCommands.addTranslation(ruTransText.getText());
        }

        if (!contextText.getText().isEmpty()) {
            if(SQLCommands.checkContext(contextText.getText()))
                idContext = SQLCommands.getContextId(contextText.getText());
            else
                idContext = SQLCommands.addContextText(contextText.getText());
        } else
            idContext = SQLCommands.getContextId("Не задано");

        SQLCommands.updateKeyWord(idEngPhrase, idKeyWord);

        SQLCommands.updatePair(idEngPhrase, idRuTranslation, idSource, idEvent, idPerson, idContext, idType, idPairG);
        Stage window = (Stage)addButton.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void addTypeClicked(ActionEvent actionEvent) {
        Parent addTest = null;
        try {
            addTest = FXMLLoader.load(getClass().getResource("/fxml/AddTypeForm.fxml"));
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        Scene scene = new Scene(addTest);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(addButton.getScene().getWindow());
        stage.setOnCloseRequest(windowEvent -> {
            typeComboBox.getItems().clear();
            typeComboBox.getItems().addAll(SQLQueriesStore.getTypesList());
        });
        stage.setTitle("Добавление типов");
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        stage.show();
    }

    public void eventChanged(ActionEvent actionEvent) {
        if(!eventComboBox.getSelectionModel().isEmpty())
            dateTextField.setText(eventComboBox.getValue().getEventDate());
    }
}
