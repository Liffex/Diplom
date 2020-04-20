package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import sample.TestModel;
import sample.Word;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    private List<String> keyWords = new ArrayList<>();
    private List<String> persons = new ArrayList<>();
    private List<String> events = new ArrayList<>();

    @FXML
    void initialize() throws SQLException {
        String sqlGetKeyWords = "SELECT keyWord FROM keyWord";
        String sqlGetPersons = "SELECT personName FROM person";
        String sqlGetEvents = "SELECT eventTitle FROM event";
        Connection conn = TestModel.getConnection();

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlGetKeyWords)) {
            while (rs.next()) {
                keyWords.add(rs.getString("keyWord"));
                comboBoxKeyWord1.getItems().add(rs.getString("keyWord"));
            }
        }

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlGetPersons)) {
            while (rs.next()) {
                persons.add(rs.getString("personName"));
                comboBoxPerson1.getItems().add(rs.getString("personName"));
            }
        }

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlGetEvents)) {
            while (rs.next()) {
                events.add(rs.getString("eventTitle"));
                comboBoxEvent1.getItems().add(rs.getString("eventTitle"));
            }
        }

    }

    public void filterButtonPressed(ActionEvent actionEvent) throws SQLException {
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
        String newstring = sqlFilter;

        boolean isKeyWordSelected  = false;
        boolean isPersonSelected = false;
        boolean isEventSelected = false;

        //getKeyWords
        for (Node keyWordBox: paneKeyWord.getChildren()) {
            if(keyWordBox instanceof ComboBox)
            {
                if(!((ComboBox) keyWordBox).getSelectionModel().isEmpty()) {
                    isKeyWordSelected = true;
                    newstring = newstring.concat("'" + ((ComboBox) keyWordBox).getSelectionModel().getSelectedItem().toString() + "', ");
                }
            }
        }
        if(!isKeyWordSelected) {
            newstring = String.format(newstring, "NOT IN");
            newstring = newstring.concat("'textTHATdoesNOTexist'");
            newstring = newstring.concat(")) AND (person.personName %s (");
        }
        else {
            newstring = String.format(newstring, "IN");
            newstring = newstring.substring(0, newstring.length() - 2);
            newstring = newstring.concat(")) AND (person.personName %s (");
        }

        //getPersons
        for (Node personBox: panePerson.getChildren()) {
            if(personBox instanceof ComboBox)
            {
                if(!((ComboBox) personBox).getSelectionModel().isEmpty()) {
                    isPersonSelected = true;
                    newstring = newstring.concat("'" + ((ComboBox) personBox).getSelectionModel().getSelectedItem().toString() + "', ");
                }
            }
        }
        if(!isPersonSelected) {
            newstring = String.format(newstring, "NOT IN");
            newstring = newstring.concat("'textTHATdoesNOTexist'");
            newstring = newstring.concat(")) AND (event.eventTitle %s (");
        }
        else {
            newstring = String.format(newstring, "IN");
            newstring = newstring.substring(0, newstring.length() - 2);
            newstring = newstring.concat(")) AND (event.eventTitle %s (");
        }

        //getEvent
        for (Node eventBox: paneEvent.getChildren()) {
            if(eventBox instanceof ComboBox)
            {
                if(!((ComboBox) eventBox).getSelectionModel().isEmpty()) {
                    isEventSelected = true;
                    newstring = newstring.concat("'" + ((ComboBox) eventBox).getSelectionModel().getSelectedItem().toString() + "', ");
                }
            }
        }
        if(!isEventSelected) {
            newstring = String.format(newstring, "NOT IN");
            newstring = newstring.concat("'textTHATdoesNOTexist'");
            newstring = newstring.concat("))");
        }
        else {
            newstring = String.format(newstring, "IN");
            newstring = newstring.substring(0, newstring.length() - 2);
            newstring = newstring.concat("))");
        }



        System.out.println(newstring);
        Connection conn = TestModel.getConnection();

        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(newstring)) {
            while (rs.next())
                wordList.add(new Word(rs.getString("engPhrase"),
                        rs.getString("keyWord"),
                        rs.getString("ruTranslation"),
                        rs.getString("personName"),
                        rs.getString("contextText"),
                        rs.getString("eventTitle"),
                        rs.getString("eventDate"),
                        rs.getBoolean("isAccurate"),
                        rs.getString("sourceTitle"),
                        rs.getString("sourceURL"),
                        rs.getString("sourceDescription")));
        }
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

    public void refreshButtonClicked(ActionEvent actionEvent) throws SQLException {
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
