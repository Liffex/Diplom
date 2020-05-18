package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import misc.sql.SQLQueriesStore;
import misc.data.Word;

import java.sql.*;

public class FilterFormController {

    public ListView<String> listViewKeyWordsIn;
    public ListView<String> listViewKeyWordOut;
    public ListView<String> listViewEventIn;
    public ListView<String> listViewEventOut;
    public ListView<String> listViewPersonIn;
    public ListView<String> listViewPersonOut;
    public ListView<String> listViewTypeIn;
    public ListView<String> listViewTypeOut;

    private ObservableList<Word> wordList = FXCollections.observableArrayList();

    private ObservableList<String> keyWords = FXCollections.observableArrayList();
    private ObservableList<String> persons = FXCollections.observableArrayList();
    private ObservableList<String> events = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException {
        listViewEventIn.getItems().addAll(SQLQueriesStore.getEventTitleList());
        listViewPersonIn.getItems().addAll(SQLQueriesStore.getPersonList());
        listViewTypeIn.getItems().addAll(SQLQueriesStore.getTypesList());
        listViewKeyWordsIn.getItems().addAll(SQLQueriesStore.getKeyWordList());
    }

    public ObservableList<Word> getWordList(){
        return wordList;
    }

    public void buttonKeyWordAdd(ActionEvent actionEvent) {
        listViewKeyWordOut.getItems().add(listViewKeyWordsIn.getSelectionModel().getSelectedItem());
        listViewKeyWordsIn.getItems().remove(listViewKeyWordsIn.getSelectionModel().getSelectedItem());
    }
    public void buttonKeyWordDelete(ActionEvent actionEvent) {
        listViewKeyWordsIn.getItems().add(listViewKeyWordOut.getSelectionModel().getSelectedItem());
        listViewKeyWordOut.getItems().remove(listViewKeyWordOut.getSelectionModel().getSelectedItem());
    }
    public void buttonEventAdd(ActionEvent actionEvent) {
        listViewEventOut.getItems().add(listViewEventIn.getSelectionModel().getSelectedItem());
        listViewEventIn.getItems().remove(listViewEventIn.getSelectionModel().getSelectedItem());
    }
    public void buttonEventDelete(ActionEvent actionEvent) {
        listViewEventIn.getItems().add(listViewEventOut.getSelectionModel().getSelectedItem());
        listViewEventOut.getItems().remove(listViewEventOut.getSelectionModel().getSelectedItem());
    }
    public void buttonPersonAdd(ActionEvent actionEvent) {
        listViewPersonOut.getItems().add(listViewPersonIn.getSelectionModel().getSelectedItem());
        listViewPersonIn.getItems().remove(listViewPersonIn.getSelectionModel().getSelectedItem());
    }
    public void buttonPersonDelete(ActionEvent actionEvent) {
        listViewPersonIn.getItems().add(listViewPersonOut.getSelectionModel().getSelectedItem());
        listViewPersonOut.getItems().remove(listViewPersonOut.getSelectionModel().getSelectedItem());
    }
    public void buttonTypeAdd(ActionEvent actionEvent) {
        listViewTypeOut.getItems().add(listViewTypeIn.getSelectionModel().getSelectedItem());
        listViewTypeIn.getItems().remove(listViewTypeIn.getSelectionModel().getSelectedItem());
    }
    public void buttonTypeDelete(ActionEvent actionEvent) {
        listViewTypeIn.getItems().add(listViewTypeOut.getSelectionModel().getSelectedItem());
        listViewTypeOut.getItems().remove(listViewTypeOut.getSelectionModel().getSelectedItem());
    }

    public void buttonApplyClicked(ActionEvent actionEvent) throws SQLException {
        String sqlFilter = "SELECT idPair, typeTitle, engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource)" +
                "JOIN type ON (engRuTranslation.idType = type.idType) " +
                "WHERE ";
        if(!listViewKeyWordOut.getItems().isEmpty())
        {
            sqlFilter = sqlFilter.concat("(keyWord.keyWord IN ( ");
            for (String str: listViewKeyWordOut.getItems()) {
                sqlFilter = sqlFilter.concat("'" +str+"', ");
            }
            sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 2);
            sqlFilter = sqlFilter.concat(" ))");
        }

        if(!listViewPersonOut.getItems().isEmpty())
        {
            if(!listViewKeyWordOut.getItems().isEmpty()) {
                sqlFilter = sqlFilter.concat(" AND ");
            }

            sqlFilter = sqlFilter.concat("(person.personName IN ( ");
            for (String str: listViewPersonOut.getItems()) {
                sqlFilter = sqlFilter.concat("'" +str+"', ");
            }
            sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 2);
            sqlFilter = sqlFilter.concat(" ))");
        }

        if(!listViewEventOut.getItems().isEmpty()) {
            if (!(listViewKeyWordOut.getItems().isEmpty() && listViewPersonOut.getItems().isEmpty())) {
                sqlFilter = sqlFilter.concat(" AND ");
            }

            sqlFilter = sqlFilter.concat("(event.eventTitle IN ( ");
            for(String str: listViewEventOut.getItems()) {
                sqlFilter = sqlFilter.concat("'" +str+"', ");
            }
            sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 2);
            sqlFilter = sqlFilter.concat(" ))");
        }

        if(!listViewTypeOut.getItems().isEmpty()) {
            if (!(listViewKeyWordOut.getItems().isEmpty() && listViewPersonOut.getItems().isEmpty() && listViewEventOut.getItems().isEmpty())) {
                sqlFilter = sqlFilter.concat(" AND ");
            }
            sqlFilter = sqlFilter.concat("(type.typeTitle IN ( ");
            for (String str: listViewTypeOut.getItems()) {
                sqlFilter = sqlFilter.concat("'" +str+"', ");
            }
            sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 2);
            sqlFilter = sqlFilter.concat(" ))");
        }

        if(listViewTypeOut.getItems().isEmpty() && listViewEventOut.getItems().isEmpty() &&
        listViewPersonOut.getItems().isEmpty() && listViewKeyWordOut.getItems().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не выбрано ни одного параметра");
            alert.showAndWait();
        } else {
        System.out.println(sqlFilter);
        wordList = SQLQueriesStore.filterList(sqlFilter);

        Stage window = (Stage)listViewTypeOut.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
    }

    public void buttonClearClicked(ActionEvent actionEvent) {
    }
}
