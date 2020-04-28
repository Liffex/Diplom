package controllers;

import java.io.IOException;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import misc.FileHandler;
import misc.GetObservableList;
import misc.SQLCommands;
import db.TestModel;
import sample.Word;

public class ViewFormController {

    FileHandler fileHandler = new FileHandler();
    private ObservableList<Word> rawData = FXCollections.observableArrayList();
    private ObservableList<Word> displayData = FXCollections.observableArrayList();

    @FXML
    private TableView<Word> dataTableView;
    @FXML
    private TableColumn<Word, String> columnKey;
    @FXML
    private TableColumn<Word, String> columnPhrase;
    @FXML
    private TableColumn<Word, String> columnDate;
    @FXML
    private TableColumn<Word, String> columnEvent;
    @FXML
    private TableColumn<Word,String> columnTranslation;
    @FXML
    private TableColumn<Word,String> columnPerson;
    @FXML
    private TableColumn<Word,String> columnSourceTitle;

    TestModel testModel = new TestModel();

    @FXML
    void initialize() throws SQLException {
        if (TestModel.isDbConnected())
            System.out.println("Буза данных подключена");

        columnKey.setCellValueFactory(new PropertyValueFactory<>("KeyWord"));
        columnKey.setText("Ключевое слово");
        columnPhrase.setCellValueFactory(new PropertyValueFactory<>("Phrase"));
        columnPhrase.setText("Фраза");
        columnDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        columnDate.setText("Дата");
        columnEvent.setCellValueFactory(new PropertyValueFactory<>("eventTitle"));
        columnEvent.setText("Событие");
        columnTranslation.setCellValueFactory(new PropertyValueFactory<>("translation"));
        columnTranslation.setText("Перевод");
        columnPerson.setCellValueFactory(new PropertyValueFactory<>("person"));
        columnPerson.setText("Персона");
        columnSourceTitle.setCellValueFactory(new PropertyValueFactory<>("sourceTitle"));
        columnSourceTitle.setText("Источник");


        dataTableView.setItems(displayData);
        dataTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableFill(GetObservableList.defaultList());
    }

    private void tableFill(ObservableList<Word> list){
        displayData.clear();

        for (Word wd: list) {
            if(wd.getEventDate() == null)
            {
                displayData.add(new Word(wd.getPhrase(),
                        wd.getKeyWord(),
                        wd.getTranslation(),
                        wd.getPerson(),
                        wd.getContext(),
                        wd.getEventTitle(),
                        "01.01.0001",
                        wd.getIsAccurate(),
                        wd.getSourceTitle(),
                        wd.getSourceURL(),
                        wd.getSourceDescription()));
            } else {
                if (wd.getIsAccurate()) {
                    displayData.add(new Word(wd.getPhrase(),
                            wd.getKeyWord(),
                            wd.getTranslation(),
                            wd.getPerson(),
                            wd.getContext(),
                            wd.getEventTitle(),
                            wd.getEventDate(),
                            wd.getIsAccurate(),
                            wd.getSourceTitle(),
                            wd.getSourceURL(),
                            wd.getSourceDescription()));
                } else {
                    displayData.add(new Word(wd.getPhrase(),
                            wd.getKeyWord(),
                            wd.getTranslation(),
                            wd.getPerson(),
                            wd.getContext(),
                            wd.getEventTitle(),
                            wd.getEventDate().substring(wd.getEventDate().indexOf('.') + 1),
                            wd.getIsAccurate(),
                            wd.getSourceTitle(),
                            wd.getSourceURL(),
                            wd.getSourceDescription()));
                }
            }
        }
    }

    public void menuExportClicked(ActionEvent actionEvent) throws IOException {
       fileHandler.exportData(rawData);
    }

    public void menuCloseClicked(ActionEvent actionEvent) {
    }

    public void menuAddClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addingForm.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(dataTableView.getScene().getWindow());
        stage.setOnCloseRequest(windowEvent -> {
            try {
                tableFill(GetObservableList.defaultList());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stage.setScene(scene);

        AddingFormController controller = loader.getController();
        stage.show();
    }

    public void menuEditClicked(ActionEvent actionEvent) throws IOException, SQLException {
        Connection conn = TestModel.getConnection();
        int idPhrase;
        int idTranslation;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addingForm.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(dataTableView.getScene().getWindow());
        stage.setOnCloseRequest(windowEvent -> {
            try {
                tableFill(GetObservableList.defaultList());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stage.setScene(scene);

        idPhrase = SQLCommands.getPhraseId(dataTableView.getSelectionModel().getSelectedItem().getPhrase());
        idTranslation = SQLCommands.getTranslationId(dataTableView.getSelectionModel().getSelectedItem().getTranslation());

        AddingFormController controller = loader.getController();
        controller.setEditingMode(idPhrase,idTranslation);
        stage.show();
    }

    public void menuDeleteClicked(ActionEvent actionEvent) throws SQLException {
        for (Word wd: dataTableView.getSelectionModel().getSelectedItems()) {
            SQLCommands.deletePair(wd.getPhrase(), wd.getTranslation());
        }
        tableFill(GetObservableList.defaultList());
    }

    public void menuSearchKeyWordClicked(ActionEvent actionEvent) throws SQLException {
        ChoiceDialog<String> dialog = new ChoiceDialog<String>();
        String sqlGetKeyWords = "SELECT keyWord FROM keyWord";
        Connection conn = TestModel.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetKeyWords)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
                dialog.getItems().add(rs.getString("keyWord"));
        }
        dialog.showAndWait();
        String keyWord = dialog.getSelectedItem();


    }

    public void menuSearchPhraseClicked(ActionEvent actionEvent) throws SQLException {
        Connection conn = TestModel.getConnection();
        String searchPhrase;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Поиск по фразе");
        dialog.setHeaderText("Введите фразу, или чать фразы для поиска");
        dialog.setContentText("Фраза:");
        String phrase = "";

        dialog.showAndWait();
        phrase = dialog.getEditor().getText();
        tableFill(GetObservableList.searchByPhrase(phrase));
    }

    public void menuSearchTranslationClicked(ActionEvent actionEvent) throws SQLException {
        Connection conn = TestModel.getConnection();
        String searchPhrase;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Поиск по переводу");
        dialog.setHeaderText("Введите перевод, или чать перевода для поиска");
        dialog.setContentText("Перевод:");
        String phrase = "";

        dialog.showAndWait();
        phrase = dialog.getEditor().getText();

        tableFill(GetObservableList.searchByTranslation(phrase));

    }

    public void menuSearchEventClicked(ActionEvent actionEvent) {
    }

    public void menuSearchPersonClicked(ActionEvent actionEvent) {
    }

    public void menuRefreshClicked(ActionEvent actionEvent) throws SQLException {
        tableFill(GetObservableList.defaultList());
    }

    public void menuSetFilterClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FilterForm.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(dataTableView.getScene().getWindow());
        FilterFormController controller = loader.getController();
        stage.setOnCloseRequest(windowEvent -> tableFill(controller.getWordList()));
        stage.setScene(scene);
        stage.show();
    }

    public void menuSearchMorphologicClicked(ActionEvent actionEvent) {
        Connection conn = TestModel.getConnection();
        String searchPhrase;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Поиск по фразе");
        dialog.setHeaderText("Введите фразу, или чать фразы для поиска");
        dialog.setContentText("Фраза:");
        String phrase = "";

        dialog.showAndWait();
        phrase = dialog.getEditor().getText();
        GetObservableList.searchMorphological(phrase);
    }
}