package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import misc.*;
import db.DBConnection;
import misc.data.Word;
import misc.sql.SQLQueriesStore;
import misc.sql.SQLCommands;

public class ViewFormController {

    public ContextMenu contextMenu;
    public MenuItem menuImport;
    public Menu menuEdit;
    public MenuItem menuLogin;
    @FXML
    private TableColumn<Word, String> columnType;

    FileHandler fileHandler = new FileHandler();
    private ObservableList<Word> displayData = FXCollections.observableArrayList();
    private ObservableList<Word> currentList = FXCollections.observableArrayList();

    @FXML
    private Label labelAmount;
    @FXML
    private Button refreshButton;
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
    @FXML
    private TableColumn<Word,String> columnContext;

    DBConnection DBConnection = new DBConnection();

    @FXML
    void initialize() throws SQLException {
        if (db.DBConnection.isDbConnected())
            System.out.println("База данных подключена");

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
        columnContext.setCellValueFactory(new PropertyValueFactory<>("context"));
        columnContext.setText("Контекст");
        columnType.setCellValueFactory(new PropertyValueFactory<>("typeTitle"));
        columnType.setText("Тип");

        dataTableView.setItems(displayData);
        dataTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableFill(SQLQueriesStore.defaultList());
    }

    private void tableFill(ObservableList<Word> list){
        displayData.clear();

        for (Word wd: list) {
            if(wd.getEventDate() == null)
            {
                displayData.add(new Word(wd.getIdPair(),
                        wd.getPhrase(),
                        wd.getKeyWord(),
                        wd.getTranslation(),
                        wd.getPerson(),
                        wd.getContext(),
                        wd.getEventTitle(),
                        "01.01.0001",
                        wd.getIsAccurate(),
                        wd.getSourceTitle(),
                        wd.getSourceURL(),
                        wd.getSourceDescription(),
                        wd.getTypeTitle()));
            } else {
                if (wd.getIsAccurate()) {
                    displayData.add(new Word(wd.getIdPair(),
                            wd.getPhrase(),
                            wd.getKeyWord(),
                            wd.getTranslation(),
                            wd.getPerson(),
                            wd.getContext(),
                            wd.getEventTitle(),
                            wd.getEventDate(),
                            wd.getIsAccurate(),
                            wd.getSourceTitle(),
                            wd.getSourceURL(),
                            wd.getSourceDescription(),
                            wd.getTypeTitle()));
                } else {
                    displayData.add(new Word(
                            wd.getIdPair(),
                            wd.getPhrase(),
                            wd.getKeyWord(),
                            wd.getTranslation(),
                            wd.getPerson(),
                            wd.getContext(),
                            wd.getEventTitle(),
                            wd.getEventDate().substring(wd.getEventDate().indexOf('.') + 1),
                            wd.getIsAccurate(),
                            wd.getSourceTitle(),
                            wd.getSourceURL(),
                            wd.getSourceDescription(),
                            wd.getTypeTitle()));
                }
            }
        }

        labelAmount.setText("Записей найдено: " + displayData.size());
    }

    public void menuExportClicked(ActionEvent actionEvent) throws IOException, SQLException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(dataTableView.getScene().getWindow());
        fileHandler.exportData(SQLQueriesStore.defaultList(), selectedDirectory.getAbsolutePath());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Экспорт");
        alert.setHeaderText("Файл успешо создан");
        alert.showAndWait();
    }

    public void menuCloseClicked(ActionEvent actionEvent) {
        Stage stage = (Stage)dataTableView.getScene().getWindow();
        stage.close();
    }

    public void menuAddClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addingForm.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Добавить элемент");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(dataTableView.getScene().getWindow());
        stage.setOnCloseRequest(windowEvent -> {
            try {
                tableFill(SQLQueriesStore.defaultList());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stage.setScene(scene);

        AddingFormController controller = loader.getController();
        stage.show();
    }

    public void menuEditClicked(ActionEvent actionEvent) throws IOException, SQLException {
        if(dataTableView.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выбрано некорректное количество элементов");
            alert.setContentText("Пожалуйста, выберите один элемент для редактирования.");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addingForm.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Редактировать элемент");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(dataTableView.getScene().getWindow());
        stage.setOnCloseRequest(windowEvent -> {
            try {
                tableFill(SQLQueriesStore.defaultList());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stage.setScene(scene);

       int idPair = SQLCommands.getPairId(dataTableView.getSelectionModel().getSelectedItem().getPhrase(),
                dataTableView.getSelectionModel().getSelectedItem().getTranslation());

        AddingFormController controller = loader.getController();
        controller.setEditingMode(idPair);
        stage.show();
    }

    public void menuDeleteClicked(ActionEvent actionEvent) throws SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Подтверждение удаления элементов");
        alert.setContentText("Вы уверены, что хотите удалить " + dataTableView.getSelectionModel().getSelectedItems().size() + " элемента(ов)");

        ImageView img = new ImageView(this.getClass().getResource("/deleteIcon.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        alert.setGraphic(img);

        Optional<ButtonType> optional = alert.showAndWait();

        if(optional.isPresent() && optional.get() == ButtonType.OK) {
            for (Word wd : dataTableView.getSelectionModel().getSelectedItems()) {
                SQLCommands.deletePair(SQLCommands.getPairId(wd.getPhrase(), wd.getTranslation()));
            }
            tableFill(SQLQueriesStore.defaultList());
        }
    }

    public void menuFilterKeyWordClicked(ActionEvent actionEvent) throws SQLException {
        ChoiceDialog<String> dialog = new ChoiceDialog<String>();
        dialog.getItems().addAll(SQLQueriesStore.getKeyWordList());
        dialog.setTitle("Фильтр по ключевому слову");
        dialog.setHeaderText("Выберите ключевое слово для фильтра");

        ImageView img = new ImageView(this.getClass().getResource("/filterIcon.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        dialog.setGraphic(img);

        dialog.showAndWait();

        String keyWord = dialog.getSelectedItem();
        if(keyWord != null) {
            currentList = SQLQueriesStore.filterByKeyWord(keyWord);
            tableFill(currentList);
            refreshButton.setVisible(true);
        }

    }

    public void menuFilterPhraseClicked(ActionEvent actionEvent) throws SQLException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Поиск по фразе");
        dialog.setHeaderText("Введите фразу, или чать фразы для поиска");
        dialog.setContentText("Фраза:");
        String phrase = "";

        ImageView img = new ImageView(this.getClass().getResource("/filterIcon.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        dialog.setGraphic(img);

        dialog.showAndWait();
        phrase = dialog.getEditor().getText();
        currentList = SQLQueriesStore.searchByPhrase(phrase);
        tableFill(currentList);
        refreshButton.setVisible(true);
    }

    public void menuFilterTranslationClicked(ActionEvent actionEvent) throws SQLException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Поиск по переводу");
        dialog.setHeaderText("Введите перевод, или чать перевода для поиска");
        dialog.setContentText("Перевод:");
        String phrase = "";

        ImageView img = new ImageView(this.getClass().getResource("/filterIcon.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        dialog.setGraphic(img);

        dialog.showAndWait();
        phrase = dialog.getEditor().getText();

        currentList = SQLQueriesStore.searchByPhrase(phrase);
        tableFill(currentList);
        refreshButton.setVisible(true);
    }

    public void menuFilterEventClicked(ActionEvent actionEvent) throws SQLException {
        ChoiceDialog<String> dialog = new ChoiceDialog<String>();
        dialog.getItems().addAll(SQLQueriesStore.getEventTitleList());
        ImageView img = new ImageView(this.getClass().getResource("/filterIcon.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        dialog.setGraphic(img);
        dialog.showAndWait();

        String event = dialog.getSelectedItem();

        currentList = SQLQueriesStore.searchByEvent(event);
        tableFill(currentList);
        refreshButton.setVisible(true);
    }

    public void menuFilterPersonClicked(ActionEvent actionEvent) throws SQLException {
        ChoiceDialog<String> dialog = new ChoiceDialog<String>();
        dialog.getItems().addAll(SQLQueriesStore.getPersonList());
        ImageView img = new ImageView(this.getClass().getResource("/filterIcon.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        dialog.setGraphic(img);
        dialog.showAndWait();

        String person = dialog.getSelectedItem();

        currentList = SQLQueriesStore.searchByPerson(person);
        tableFill(currentList);
        refreshButton.setVisible(true);
    }

    public void menuSetCustomFilterClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FilterForm.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Составной фильтр");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(dataTableView.getScene().getWindow());
        FilterFormController controller = loader.getController();
        stage.setOnCloseRequest(windowEvent -> {
            currentList = controller.getWordList();
            tableFill(currentList);
        });
        stage.setScene(scene);
        stage.show();
        refreshButton.setVisible(true);
    }

    public void menuSearchMorphologicClicked() throws Exception {

        Dialog<Pair<Boolean, String>> dialog = new Dialog<>();
        dialog.setTitle("Поиск");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Поиск", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, cancelButton);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 10, 10, 10));


        ToggleGroup gr = new ToggleGroup();
        RadioButton rb1 = new RadioButton();
        rb1.setToggleGroup(gr);
        rb1.setSelected(true);
        rb1.setText("Русский");
        RadioButton rb2 = new RadioButton();
        rb2.setToggleGroup(gr);
        rb2.setText("Английский");


        TextField to = new TextField();
        to.setPromptText("Поиск: ");
        to.setPrefWidth(350);

        gridPane.add(rb1, 0, 0);
        gridPane.add(rb2, 1,0);
        gridPane.add(new Label("Поиск:"), 0, 1);
        gridPane.add(to, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(rb1.isSelected(), to.getText());
            }
            return null;
        });

        Optional<Pair<Boolean, String>> result1 = dialog.showAndWait();

        result1.ifPresent(pair -> {
            if(pair.getKey()) {
                System.out.println("Русский");
                try {
                    if(!pair.getValue().isEmpty()) {
                        currentList = SQLQueriesStore.searchMorphologicalRu(pair.getValue(), false);
                        tableFill(currentList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButton.setVisible(true);
            }
            else {
                System.out.println("Английский");
                try {
                    if(!pair.getValue().isEmpty()) {
                        currentList = SQLQueriesStore.searchMorphologicalEn(pair.getValue(), false);
                        tableFill(currentList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButton.setVisible(true);
            }
        });
    }

    public void menuImportClicked(ActionEvent actionEvent) throws SQLException {
        FileHandler fileHandler = new FileHandler();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл импорта");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLSX","*.xlsx"));
        File file = fileChooser.showOpenDialog(dataTableView.getScene().getWindow());
        System.out.println(file.getPath());
        fileHandler.importData(file.getPath());
        tableFill(SQLQueriesStore.defaultList());
    }

    public void refreshButtonClicked(ActionEvent actionEvent) throws SQLException {
        tableFill(SQLQueriesStore.defaultList());
        currentList = SQLQueriesStore.defaultList();
        refreshButton.setVisible(false);
    }

    public void menuLogin(ActionEvent actionEvent) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Аутентификация");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Войти", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField from = new TextField();
        from.setPromptText("Логин");
        PasswordField to = new PasswordField();
        to.setPromptText("Пароль");

        gridPane.add(new Label("Логин"), 0,0);
        gridPane.add(from, 1, 0);
        gridPane.add(new Label("Пароль:"), 0, 1);
        gridPane.add(to, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(from::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(from.getText(), to.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            try {
                if(Authentication.checkUser(pair.getKey(), pair.getValue()))
                    showAdminFunctions();
            } catch (NoSuchAlgorithmException | SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void showAdminFunctions() {
        menuImport.setVisible(true);
        menuEdit.setVisible(true);
        contextMenu.removeEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
        menuLogin.setVisible(false);
    }

    public void menuFilterTypeClicked(ActionEvent actionEvent) throws SQLException {
        ChoiceDialog<String> dialog = new ChoiceDialog<String>();
        dialog.getItems().addAll(SQLQueriesStore.getTypesList());
        dialog.showAndWait();

        String person = dialog.getSelectedItem();

        tableFill(SQLQueriesStore.searchByType(person));
        refreshButton.setVisible(true);
    }

    public void setMode(List<String> args) {
        if(args.contains("-admin")) {
            contextMenu.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
            menuEdit.setVisible(false);
            menuImport.setVisible(false);
            menuLogin.setVisible(true);
        } else {
            contextMenu.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
            menuEdit.setVisible(false);
            menuImport.setVisible(false);
            menuLogin.setVisible(false);
        }
    }

    public void menuSearchMorphologicalTranslatr(ActionEvent actionEvent) {
        Dialog<Pair<Boolean, String>> dialog = new Dialog<>();
        dialog.setTitle("Поиск");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Поиск", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, cancelButton);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 10, 10, 10));


        ToggleGroup gr = new ToggleGroup();
        RadioButton rb1 = new RadioButton();
        rb1.setToggleGroup(gr);
        rb1.setSelected(true);
        rb1.setText("Русский");
        RadioButton rb2 = new RadioButton();
        rb2.setToggleGroup(gr);
        rb2.setText("Английский");


        TextField to = new TextField();
        to.setPromptText("Поиск: ");
        to.setPrefWidth(350);

        gridPane.add(rb1, 0, 0);
        gridPane.add(rb2, 1,0);
        gridPane.add(new Label("Поиск:"), 0, 1);
        gridPane.add(to, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(rb1.isSelected(), to.getText());
            }
            return null;
        });

        Optional<Pair<Boolean, String>> result1 = dialog.showAndWait();

        result1.ifPresent(pair -> {
            if(pair.getKey()) {
                System.out.println("Русский");
                try {
                    if(!pair.getValue().isEmpty()) {
                        currentList = SQLQueriesStore.searchMorphologicalRu(pair.getValue(), true);
                        tableFill(currentList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButton.setVisible(true);
            }
            else {
                System.out.println("Английский");
                try {
                    if(!pair.getValue().isEmpty()) {
                        currentList = SQLQueriesStore.searchMorphologicalEn(pair.getValue(), true);
                        tableFill(currentList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButton.setVisible(true);
            }
        });
    }

    public void menuExportCurrent(ActionEvent actionEvent) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(dataTableView.getScene().getWindow());
        fileHandler.exportData(currentList, selectedDirectory.getAbsolutePath());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Экспорт");
        alert.setHeaderText("Файл успешо создан");
        alert.showAndWait();
    }
}