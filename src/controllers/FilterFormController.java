package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import misc.GetObservableList;
import sample.Word;

import java.sql.*;

public class FilterFormController {

    public Pane paneKeyWord;
    public Pane panePerson;
    public Pane paneEvent;
    public Label errorLabel;
    public CheckBox checkBoxKeyWord1;
    public CheckBox checkBoxKeyWord2;
    public CheckBox checkBoxKeyWord3;
    public CheckBox checkBoxKeyWord4;
    public ComboBox<String> comboBoxKeyWord2;
    public ComboBox<String> comboBoxKeyWord1;
    public ComboBox<String> comboBoxKeyWord3;
    public ComboBox<String> comboBoxKeyWord4;
    public ComboBox<String> comboBoxKeyWord5;

    public CheckBox checkBoxPerson1;
    public CheckBox checkBoxPerson2;
    public CheckBox checkBoxPerson3;
    public CheckBox checkBoxPerson4;
    public ComboBox<String> comboBoxPerson1;
    public ComboBox<String> comboBoxPerson2;
    public ComboBox<String> comboBoxPerson3;
    public ComboBox<String> comboBoxPerson4;
    public ComboBox<String> comboBoxPerson5;

    public CheckBox checkBoxEvent1;
    public CheckBox checkBoxEvent2;
    public CheckBox checkBoxEvent3;
    public CheckBox checkBoxEvent4;
    public ComboBox<String> comboBoxEvent2;
    public ComboBox<String> comboBoxEvent1;
    public ComboBox<String> comboBoxEvent3;
    public ComboBox<String> comboBoxEvent4;
    public ComboBox<String> comboBoxEvent5;
    public AnchorPane Anchor;

    private ObservableList<Word> wordList = FXCollections.observableArrayList();

    private ObservableList<String> keyWords = FXCollections.observableArrayList();
    private ObservableList<String> persons = FXCollections.observableArrayList();
    private ObservableList<String> events = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException {
        comboBoxKeyWord1.getItems().addAll(GetObservableList.getKeyWordList());
        comboBoxPerson1.getItems().addAll(GetObservableList.getPersonList());
        comboBoxEvent1.getItems().addAll(GetObservableList.getEventTitleList());
    }

    public void filterButtonPressed(ActionEvent actionEvent) {
        String sqlFilter = "SELECT engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource) " +
                "WHERE (keyWord.keyWord %s ( ";

        boolean isKeyWordSelected  = false;
        boolean isPersonSelected = false;
        boolean isEventSelected = false;

        //getKeyWords
        for (Node keyWordBox: paneKeyWord.getChildren()) {
            if(keyWordBox instanceof ComboBox)
            {
                if(!((ComboBox) keyWordBox).getSelectionModel().isEmpty()) {
                    isKeyWordSelected = true;
                    sqlFilter = sqlFilter.concat("'" + ((ComboBox) keyWordBox).getSelectionModel().getSelectedItem().toString() + "', ");
                }
            }
        }
        if(!isKeyWordSelected) {
            sqlFilter = String.format(sqlFilter, "NOT IN");
            sqlFilter = sqlFilter.concat("'textTHATdoesNOTexist'");
            sqlFilter = sqlFilter.concat(")) AND (person.personName %s (");
        }
        else {
            sqlFilter = String.format(sqlFilter, "IN");
            sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 2);
            sqlFilter = sqlFilter.concat(")) AND (person.personName %s (");
        }

        //getPersons
        for (Node personBox: panePerson.getChildren()) {
            if(personBox instanceof ComboBox)
            {
                if(!((ComboBox) personBox).getSelectionModel().isEmpty()) {
                    isPersonSelected = true;
                    sqlFilter = sqlFilter.concat("'" + ((ComboBox) personBox).getSelectionModel().getSelectedItem().toString() + "', ");
                }
            }
        }
        if(!isPersonSelected) {
            sqlFilter = String.format(sqlFilter, "NOT IN");
            sqlFilter = sqlFilter.concat("'textTHATdoesNOTexist'");
            sqlFilter = sqlFilter.concat(")) AND (event.eventTitle %s (");
        }
        else {
            sqlFilter = String.format(sqlFilter, "IN");
            sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 2);
            sqlFilter = sqlFilter.concat(")) AND (event.eventTitle %s (");
        }

        //getEvent
        for (Node eventBox: paneEvent.getChildren()) {
            if(eventBox instanceof ComboBox)
            {
                if(!((ComboBox) eventBox).getSelectionModel().isEmpty()) {
                    isEventSelected = true;
                    sqlFilter = sqlFilter.concat("'" + ((ComboBox) eventBox).getSelectionModel().getSelectedItem().toString() + "', ");
                }
            }
        }
        if(!isEventSelected) {
            sqlFilter = String.format(sqlFilter, "NOT IN");
            sqlFilter = sqlFilter.concat("'textTHATdoesNOTexist'");
            sqlFilter = sqlFilter.concat("))");
        }
        else {
            sqlFilter = String.format(sqlFilter, "IN");
            sqlFilter = sqlFilter.substring(0, sqlFilter.length() - 2);
            sqlFilter = sqlFilter.concat("))");
        }

        System.out.println(sqlFilter);

        Stage window = (Stage) Anchor.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public ObservableList<Word> getWordList(){
        return wordList;
    }

    public void checkBoxKeyWord1Selected(ActionEvent actionEvent) {
        if(checkBoxKeyWord1.isSelected()) {
            comboBoxKeyWord1.setDisable(true);
            comboBoxKeyWord2.setVisible(true);
            checkBoxKeyWord2.setVisible(true);
            for (String str: comboBoxKeyWord1.getItems()) {
                if(!str.equals(comboBoxKeyWord1.getSelectionModel().getSelectedItem()))
                comboBoxKeyWord2.getItems().add(str);
            }
        }
        else {
            comboBoxKeyWord2.setVisible(false);
            comboBoxKeyWord1.setDisable(false);
            checkBoxKeyWord2.setVisible(false);
            comboBoxKeyWord2.getItems().clear();
        }
    }

    public void checkBoxKeyWord2Selected(ActionEvent actionEvent) {
        if(checkBoxKeyWord2.isSelected()) {
            checkBoxKeyWord1.setDisable(true);
            comboBoxKeyWord2.setDisable(true);
            comboBoxKeyWord3.setVisible(true);
            checkBoxKeyWord3.setVisible(true);
            for (String str: comboBoxKeyWord2.getItems()) {
                if(!str.equals(comboBoxKeyWord2.getSelectionModel().getSelectedItem()))
                    comboBoxKeyWord3.getItems().add(str);
            }

        }
        else {
            checkBoxKeyWord1.setDisable(false);
            comboBoxKeyWord2.setDisable(false);
            comboBoxKeyWord3.setVisible(false);
            checkBoxKeyWord3.setVisible(false);
            comboBoxKeyWord3.getItems().clear();

        }
    }

    public void checkBoxKeyWord3Selected(ActionEvent actionEvent) {
        if(checkBoxKeyWord3.isSelected()) {
            checkBoxKeyWord2.setDisable(true);
            comboBoxKeyWord3.setDisable(true);
            comboBoxKeyWord4.setVisible(true);
            checkBoxKeyWord4.setVisible(true);
            for (String str: comboBoxKeyWord3.getItems()) {
                if(!str.equals(comboBoxKeyWord3.getSelectionModel().getSelectedItem()))
                    comboBoxKeyWord4.getItems().add(str);
            }

        }
        else {
            checkBoxKeyWord2.setDisable(false);
            comboBoxKeyWord3.setDisable(false);
            comboBoxKeyWord4.setVisible(false);
            checkBoxKeyWord4.setVisible(false);
            comboBoxKeyWord4.getItems().clear();
        }
    }

    public void checkBoxKeyWord4Selected(ActionEvent actionEvent) {
        if(checkBoxKeyWord4.isSelected()) {
            checkBoxKeyWord3.setDisable(true);
            comboBoxKeyWord4.setDisable(true);
            comboBoxKeyWord5.setVisible(true);
            for (String str: comboBoxKeyWord4.getItems()) {
                if(!str.equals(comboBoxKeyWord4.getSelectionModel().getSelectedItem()))
                    comboBoxKeyWord5.getItems().add(str);
            }
        }
        else {
            checkBoxKeyWord3.setDisable(false);
            comboBoxKeyWord4.setDisable(false);
            comboBoxKeyWord5.setVisible(false);
            comboBoxKeyWord5.getItems().clear();
        }
    }

    public void checkBoxPerson1Selected(ActionEvent actionEvent) {
        if( checkBoxPerson1.isSelected()) {
            comboBoxPerson1.setDisable(true);
            comboBoxPerson2.setVisible(true);
            checkBoxPerson2.setVisible(true);
            for (String str: comboBoxPerson1.getItems()) {
                if(!str.equals(comboBoxPerson1.getSelectionModel().getSelectedItem()))
                    comboBoxPerson2.getItems().add(str);
            }
        }
        else {
            comboBoxPerson1.setDisable(false);
            comboBoxPerson2.setVisible(false);
            checkBoxPerson2.setVisible(false);
            comboBoxPerson2.getItems().clear();
        }
    }

    public void checkBoxPerson2Selected(ActionEvent actionEvent) {
        if( checkBoxPerson2.isSelected()) {
            checkBoxPerson1.setDisable(true);
            comboBoxPerson2.setDisable(true);
            comboBoxPerson3.setVisible(true);
            checkBoxPerson3.setVisible(true);
            for (String str: comboBoxPerson2.getItems()) {
                if(!str.equals(comboBoxPerson2.getSelectionModel().getSelectedItem()))
                    comboBoxPerson3.getItems().add(str);
            }
        }
        else {
            checkBoxPerson1.setDisable(false);
            comboBoxPerson2.setDisable(false);
            comboBoxPerson3.setVisible(false);
            checkBoxPerson3.setVisible(false);
            comboBoxPerson3.getItems().clear();
        }
    }

    public void checkBoxPerson3Selected(ActionEvent actionEvent) {
        if( checkBoxPerson3.isSelected()) {
            checkBoxPerson2.setDisable(true);
            comboBoxPerson3.setDisable(true);
            comboBoxPerson4.setVisible(true);
            checkBoxPerson4.setVisible(true);
            for (String str: comboBoxPerson3.getItems()) {
                if(!str.equals(comboBoxPerson3.getSelectionModel().getSelectedItem()))
                    comboBoxPerson4.getItems().add(str);
            }
        }
        else {
            checkBoxPerson2.setDisable(false);
            comboBoxPerson3.setDisable(false);
            comboBoxPerson4.setVisible(false);
            checkBoxPerson4.setVisible(false);
            comboBoxPerson4.getItems().clear();
        }
    }

    public void checkBoxPerson4Selected(ActionEvent actionEvent) {
        if( checkBoxPerson4.isSelected()) {
            checkBoxPerson3.setDisable(true);
            comboBoxPerson4.setDisable(true);
            comboBoxPerson5.setVisible(true);
            for (String str: comboBoxPerson4.getItems()) {
                if(!str.equals(comboBoxPerson4.getSelectionModel().getSelectedItem()))
                    comboBoxPerson5.getItems().add(str);
            }
        }
        else {
            checkBoxPerson3.setDisable(false);
            comboBoxPerson4.setDisable(false);
            comboBoxPerson5.setVisible(false);
            comboBoxPerson5.getItems().clear();
        }
    }

    public void checkBoxEvent1Selected(ActionEvent actionEvent) {
        if( checkBoxEvent1.isSelected()) {
            comboBoxEvent1.setDisable(true);
            comboBoxEvent2.setVisible(true);
            checkBoxEvent2.setVisible(true);
            for (String str: comboBoxEvent1.getItems()) {
                if(!str.equals(comboBoxEvent1.getSelectionModel().getSelectedItem()))
                    comboBoxEvent2.getItems().add(str);
            }
        }
        else {
            comboBoxEvent1.setDisable(false);
            comboBoxEvent2.setVisible(false);
            checkBoxEvent2.setVisible(false);
            comboBoxEvent2.getItems().clear();
        }
    }

    public void checkBoxEvent2Selected(ActionEvent actionEvent) {
        if( checkBoxEvent2.isSelected()) {
            checkBoxEvent1.setDisable(true);
            comboBoxEvent2.setDisable(true);
            comboBoxEvent3.setVisible(true);
            checkBoxEvent3.setVisible(true);
            for (String str: comboBoxEvent2.getItems()) {
                if(!str.equals(comboBoxEvent2.getSelectionModel().getSelectedItem()))
                    comboBoxEvent3.getItems().add(str);
            }

        }
        else {
            checkBoxEvent1.setDisable(false);
            comboBoxEvent2.setDisable(false);
            comboBoxEvent3.setVisible(false);
            checkBoxEvent3.setVisible(false);
            comboBoxEvent3.getItems().clear();
        }
    }

    public void checkBoxEvent3Selected(ActionEvent actionEvent) {
        if( checkBoxEvent3.isSelected()) {
            checkBoxEvent2.setDisable(true);
            comboBoxEvent3.setDisable(true);
            comboBoxEvent4.setVisible(true);
            checkBoxEvent4.setVisible(true);
            for (String str: comboBoxEvent3.getItems()) {
                if(!str.equals(comboBoxEvent3.getSelectionModel().getSelectedItem()))
                    comboBoxEvent4.getItems().add(str);
            }
        }
        else {
            checkBoxEvent2.setDisable(false);
            comboBoxEvent3.setDisable(false);
            comboBoxEvent4.setVisible(false);
            checkBoxEvent4.setVisible(false);
            comboBoxEvent4.getItems().clear();
        }
    }

    public void checkBoxEvent4Selected(ActionEvent actionEvent) {
        if( checkBoxEvent4.isSelected()) {
            checkBoxEvent3.setDisable(true);
            comboBoxEvent4.setDisable(true);
            comboBoxEvent5.setVisible(true);
            for (String str: comboBoxEvent4.getItems()) {
                if(!str.equals(comboBoxEvent4.getSelectionModel().getSelectedItem()))
                    comboBoxEvent5.getItems().add(str);
            }
        }
        else {
            checkBoxEvent3.setDisable(false);
            comboBoxEvent4.setDisable(false);
            comboBoxEvent5.setVisible(false);
            comboBoxEvent5.getItems().clear();
        }
    }

    public void refreshButtonClicked(ActionEvent actionEvent) {
        for (Node node: Anchor.getChildren()) {
            if(node instanceof Pane)
                for (Node node1: ((Pane) node).getChildren()) {
                    if(node1 instanceof ComboBox)
                        ((ComboBox) node1).getSelectionModel().clearSelection();
                    if(node1 instanceof CheckBox)
                        ((CheckBox) node1).setSelected(false);
                }
        }
        checkBoxKeyWord1.setDisable(false);
        comboBoxKeyWord1.setDisable(false);
        comboBoxPerson1.setDisable(false);
        checkBoxPerson1.setDisable(false);
        checkBoxEvent1.setDisable(false);
        comboBoxEvent1.setDisable(false);

        checkBoxKeyWord2.setVisible(false);
        comboBoxKeyWord2.setVisible(false);
        comboBoxPerson2.setVisible(false);
        checkBoxPerson2.setVisible(false);
        checkBoxEvent2.setVisible(false);
        comboBoxEvent2.setVisible(false);

        checkBoxKeyWord3.setVisible(false);
        comboBoxKeyWord3.setVisible(false);
        comboBoxPerson3.setVisible(false);
        checkBoxPerson3.setVisible(false);
        checkBoxEvent3.setVisible(false);
        comboBoxEvent3.setVisible(false);

        checkBoxKeyWord4.setVisible(false);
        comboBoxKeyWord4.setVisible(false);
        comboBoxPerson4.setVisible(false);
        checkBoxPerson4.setVisible(false);
        checkBoxEvent4.setVisible(false);
        comboBoxEvent4.setVisible(false);

        comboBoxKeyWord5.setVisible(false);
        comboBoxPerson5.setVisible(false);
        comboBoxEvent5.setVisible(false);
    }
}
