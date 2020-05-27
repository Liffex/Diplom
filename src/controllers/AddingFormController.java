package controllers;

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
import misc.sql.SQLCommands;
import db.DBConnection;
import misc.sql.SQLQueriesStore;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

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
    private ComboBox<String> typeComboBox;
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
    void initialize() throws SQLException {
        fillComboBox();
        editButton.setVisible(false);
    }

    int idPairG;

    private void fillComboBox() throws SQLException {
        keyWordComboBox.getItems().clear();
        eventComboBox.getItems().clear();
        personComboBox.getItems().clear();
        typeComboBox.getItems().clear();

        keyWordComboBox.getItems().addAll(SQLQueriesStore.getKeyWordList());
        eventComboBox.getItems().addAll(SQLQueriesStore.getEventTitleList());
        personComboBox.getItems().addAll(SQLQueriesStore.getPersonList());
        typeComboBox.getItems().addAll(SQLQueriesStore.getTypesList());
    }

    public void addKeyWordClicked(ActionEvent actionEvent) throws IOException {
        Parent addTest = FXMLLoader.load(getClass().getResource("/fxml/AddKeyWordForm.fxml"));
        Scene scene = new Scene(addTest);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(addButton.getScene().getWindow());
        stage.setOnCloseRequest(windowEvent -> {
            try {
                fillComboBox();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stage.setTitle("Добавление ключевого слова");
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        stage.show();
    }

    public void addEventClicked(ActionEvent actionEvent) throws IOException {
        Parent addTest = FXMLLoader.load(getClass().getResource("/fxml/AddEventForm.fxml"));
        Scene scene = new Scene(addTest);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            try {
                fillComboBox();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stage.setTitle("Добавление событий");
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        stage.show();
    }

    public void addPersonClicked(ActionEvent actionEvent) throws IOException {
        Parent addTest = FXMLLoader.load(getClass().getResource("/fxml/AddPersonForm.fxml"));
        Scene scene = new Scene(addTest);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            try {
                fillComboBox();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stage.setTitle("Добавление персон");
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        stage.show();
    }

    public void addButtonClicked(ActionEvent actionEvent) throws SQLException {
        int contextId;
        //todo check eng ru text

        if(engPhraseText.getText().trim().length() == 0 ||
                ruTransText.getText().trim().length() == 0 ||
                typeComboBox.getSelectionModel().isEmpty() ||
                personComboBox.getSelectionModel().isEmpty() ||
                eventComboBox.getSelectionModel().isEmpty() ||
                keyWordComboBox.getSelectionModel().isEmpty() ||
                sourceTitleText.getText().trim().length() == 0 ||
                sourceDescriptionText.getText().trim().length() == 0 ||
                sourceUrlText.getText().trim().length() == 0 ||
                contextText.getText().trim().length() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка заполнения");
            alert.setHeaderText("Пожалуйста, заполните все поля");
            alert.setContentText("В случае отсутствия информации, выберите, или введите \"Не задано\"");
            alert.showAndWait();
            return;//todo errormessage
        }


        if (!contextText.getText().isEmpty()) {
            if(SQLCommands.checkContext(contextText.getText()))
                contextId = SQLCommands.getContextId(contextText.getText());
            else
                contextId = SQLCommands.addContextText(contextText.getText());
        } else
            contextId = SQLCommands.getContextId("NO_CONTEXT");

        String sourceTitle;
        String sourceUrl;
        String sourceDesc;
        if(sourceTitleText.getText().isEmpty())
            sourceTitle = "NO_TITLE";
        else sourceTitle = sourceTitleText.getText();

        if(sourceUrlText.getText().isEmpty())
            sourceUrl = "NO_URL";
        else sourceUrl = sourceUrlText.getText();

        if (sourceDescriptionText.getText().isEmpty())
            sourceDesc = "NO_DESC";
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

        int personId = SQLCommands.getPersonId(personComboBox.getValue());
        int eventId = SQLCommands.getEventId(eventComboBox.getValue());
        int typeId = SQLCommands.getTypeId(typeComboBox.getValue());

        SQLCommands.addPair(engPhraseId, ruTransId, sourceId, eventId, personId, contextId, typeId);
    }

    public void setEditingMode(int idPair) throws SQLException {
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
        eventComboBox.setValue(SQLCommands.getEventTitle(idEvent));
        sourceDescriptionText.setText(SQLCommands.getSourceDescription(idSource));
        sourceTitleText.setText(SQLCommands.getSourceTitle(idSource));
        sourceUrlText.setText(SQLCommands.getSourceURL(idSource));
        contextText.setText(SQLCommands.getContextText(idContext));
        personComboBox.setValue(SQLCommands.getPersonName(idPerson));
        typeComboBox.setValue(SQLCommands.getTypeTitle(idType));
    }

    public void editButtonClicked(ActionEvent actionEvent) throws SQLException { //todo add event date confirmation
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
                typeComboBox.getSelectionModel().isEmpty() ||
                personComboBox.getSelectionModel().isEmpty() ||
                eventComboBox.getSelectionModel().isEmpty() ||
                keyWordComboBox.getSelectionModel().isEmpty() ||
                sourceTitleText.getText().trim().length() == 0 ||
                sourceDescriptionText.getText().trim().length() == 0 ||
                sourceUrlText.getText().trim().length() == 0 ||
                contextText.getText().trim().length() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка заполнения");
            alert.setHeaderText("Пожалуйста, заполните все поля");
            alert.setContentText("В случае отсутствия информации, выберите, или введите \"Не задано\"");
            alert.showAndWait();
            return;//todo errormessage
        }

        idKeyWord = SQLCommands.getKeyWordId(keyWordComboBox.getValue());
        idEvent = SQLCommands.getEventId(eventComboBox.getValue());
        idPerson = SQLCommands.getPersonId(personComboBox.getValue());
        idType = SQLCommands.getTypeId(typeComboBox.getValue());

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

        System.out.println(contextText.getText());

        if(SQLCommands.checkContext(contextText.getText())) {
            idContext = SQLCommands.getContextId(contextText.getText());
        } else {
            idContext = SQLCommands.addContextText(contextText.getText());
        }

        SQLCommands.updatePair(idEngPhrase, idRuTranslation, idSource, idEvent, idPerson, idContext, idType, idPairG);
        Stage window = (Stage)addButton.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void addTypeClicked(ActionEvent actionEvent) throws IOException {
        Parent addTest = FXMLLoader.load(getClass().getResource("/fxml/AddTypeForm.fxml"));
        Scene scene = new Scene(addTest);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(addButton.getScene().getWindow());
        stage.setOnCloseRequest(windowEvent -> {
            try {
                fillComboBox();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stage.setTitle("Добавление типов");
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        stage.show();
    }
}
