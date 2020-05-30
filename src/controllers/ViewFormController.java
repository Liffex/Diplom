package controllers;

import com.pullenti.morph.MorphLang;
import com.pullenti.morph.Morphology;
import db.DBConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import misc.Authentication;
import misc.FileHandler;
import misc.Translate;
import misc.data.Word;
import misc.sql.SQLCommands;
import misc.sql.SQLQueriesStore;
import ru.textanalysis.tawt.jmorfsdk.loader.JMorfSdkFactory;
import org.apache.commons.lang3.tuple.Triple;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ViewFormController {

    public ContextMenu contextMenu;
    public MenuItem menuImport;
    public Menu menuEdit;
    public MenuItem menuLogin;
    public MenuItem searchNoTranslation;
    public MenuItem searchTranslation;
    public Label labelLoad;
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
    EventHandler filter = Event::consume;
    Logger log = Logger.getLogger(ViewFormController.class.getName());

    @FXML
    void initialize() {
        if (db.DBConnection.isDbConnected())
            log.log(Level.INFO, "База данных подключена");

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
        currentList = SQLQueriesStore.defaultList();
        tableFill(currentList);
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                SQLQueriesStore.setjMorfSdk(JMorfSdkFactory.loadFullLibrary());
                try {
                    Morphology.initialize(MorphLang.EN);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                labelLoad.setVisible(false);
                searchNoTranslation.setDisable(false);
                searchTranslation.setDisable(false);
            }
        });
        myThread.start();
    }

    public void tableFill(ObservableList<Word> list){
        displayData.clear();

        for (Word wd: list) {
            int idPair = wd.getIdPair();
            String phrase = wd.getPhrase();
            String keyWord = wd.getKeyWord();
            String translation = wd.getTranslation();
            String person = wd.getPerson();
            String context = wd.getContext();
            String eventTitle = wd.getEventTitle();
            String eventDate = wd.getEventDate();
            boolean isAccurate = wd.getIsAccurate();
            String sourceTitle = wd.getSourceTitle();
            String sourceURL = wd.getSourceURL();
            String sourceDescription = wd.getSourceDescription();
            String typeTitle = wd.getTypeTitle();

            if (translation.equals("Не задано")) {
                translation = " ";
            }

            if (person.equals("Не задано")) {
                person = " ";
            }

            if (context.equals("Не задано")) {
                context = " ";
            }

            if (eventTitle.equals("Не задано")) {
                eventTitle = " ";
            }

            if (eventDate.equals("Не задано")) {
                eventDate = " ";
            }

            if (sourceTitle.equals("Не задано")) {
                sourceTitle = " ";
            }

            if (sourceURL.equals("Не задано")) {
                sourceURL = " ";
            }

            if (sourceDescription.equals("Не задано")) {
                sourceDescription = " ";
            }

            if (typeTitle.equals("Не задано")) {
                typeTitle = " ";
            }

                if (wd.getIsAccurate()) {
                    displayData.add(new Word(idPair,
                            phrase,
                            keyWord,
                            translation,
                            person,
                            context,
                            eventTitle,
                            eventDate,
                            isAccurate,
                            sourceTitle,
                            sourceURL,
                            sourceDescription,
                            typeTitle));
                } else {
                    displayData.add(new Word(idPair,
                            phrase,
                            keyWord,
                            translation,
                            person,
                            context,
                            eventTitle,
                            eventDate.substring(eventDate.indexOf('.') + 1),
                            isAccurate,
                            sourceTitle,
                            sourceURL,
                            sourceDescription,
                            typeTitle));
                }
            }
        columnPhrase.setSortType(TableColumn.SortType.ASCENDING);
        dataTableView.getSortOrder().add(columnPhrase);
        labelAmount.setText("Записей найдено: " + displayData.size());
    }

    public void menuExportClicked(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(dataTableView.getScene().getWindow());

        ObservableList<Word> exportAll = SQLQueriesStore.defaultList();

        if(exportAll.size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не найдены данные");
            alert.setContentText("Отсутствуют данные для экспорта");
            alert.showAndWait();
            return;
        }

        if (selectedDirectory != null) {
            fileHandler.exportData(exportAll, selectedDirectory.getAbsolutePath());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Экспорт");
            alert.setHeaderText("Файл успешо создан");
            alert.showAndWait();
        }
    }

    public void menuCloseClicked(ActionEvent actionEvent) {
        Stage stage = (Stage)dataTableView.getScene().getWindow();
        stage.close();
    }

    public void menuAddClicked(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addingForm.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        Stage stage = new Stage();
        stage.setTitle("Добавить элемент");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(dataTableView.getScene().getWindow());
        stage.setOnCloseRequest(windowEvent -> {
            tableFill(SQLQueriesStore.defaultList());
        });
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));
        stage.setScene(scene);

        AddingFormController controller = loader.getController();
        stage.show();
    }

    public void menuEditClicked(ActionEvent actionEvent) {
        if(dataTableView.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выбрано некорректное количество элементов");
            alert.setContentText("Пожалуйста, выберите один элемент для редактирования.");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addingForm.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        Stage stage = new Stage();
        stage.setTitle("Редактировать элемент");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(dataTableView.getScene().getWindow());
        stage.setOnCloseRequest(windowEvent -> {
            tableFill(SQLQueriesStore.defaultList());
        });
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        stage.setScene(scene);

       int idPair = SQLCommands.getPairId(dataTableView.getSelectionModel().getSelectedItem().getPhrase(),
                dataTableView.getSelectionModel().getSelectedItem().getTranslation());

        AddingFormController controller = loader.getController();
        controller.setEditingMode(idPair);
        stage.show();
    }

    public void menuDeleteClicked(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Подтверждение удаления элементов");
        alert.setContentText("Вы уверены, что хотите удалить " + dataTableView.getSelectionModel().getSelectedItems().size() + " элемента(ов)");

        ImageView img = new ImageView(this.getClass().getResource("/images/deleteIcon.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        alert.setGraphic(img);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));


        Optional<ButtonType> optional = alert.showAndWait();

        if(optional.isPresent() && optional.get() == ButtonType.OK) {
            for (Word wd : dataTableView.getSelectionModel().getSelectedItems()) {
                SQLCommands.deletePair(wd.getIdPair());
            }
            currentList = SQLQueriesStore.defaultList();
            tableFill(currentList);
        }
    }

    public void menuFilterKeyWordClicked(ActionEvent actionEvent) {
        ChoiceDialog<String> dialog = new ChoiceDialog<String>();
        dialog.getItems().addAll(SQLQueriesStore.getKeyWordList());
        dialog.setTitle("Фильтр по ключевому слову");
        dialog.setHeaderText("Выберите ключевое слово для фильтра");

        ImageView img = new ImageView(this.getClass().getResource("/images/filterIcon.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        dialog.setGraphic(img);
        ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        ((Button)(dialog.getDialogPane().lookupButton(ButtonType.CANCEL))).setText("Отмена");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            String keyWord = dialog.getSelectedItem();
            if(keyWord != null) {
                currentList = SQLQueriesStore.filterByKeyWord(keyWord);
                tableFill(currentList);
                refreshButton.setVisible(true);
            }
        });



    }

    public void menuFilterPhraseClicked(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Поиск по фразе");
        dialog.setHeaderText("Введите фразу, или чать фразы для поиска");
        dialog.setContentText("Фраза:");

        ImageView img = new ImageView(this.getClass().getResource("/images/filterIcon.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        dialog.setGraphic(img);
        ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        ((Button)(dialog.getDialogPane().lookupButton(ButtonType.CANCEL))).setText("Отмена");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            String phrase = dialog.getEditor().getText();
            currentList = SQLQueriesStore.searchByPhrase(phrase);
            tableFill(currentList);
            refreshButton.setVisible(true);
        });

    }

    public void menuFilterTranslationClicked(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Поиск по переводу");
        dialog.setHeaderText("Введите перевод, или чать перевода для поиска");
        dialog.setContentText("Перевод:");

        ImageView img = new ImageView(this.getClass().getResource("/images/filterIcon.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        dialog.setGraphic(img);
        ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        ((Button)(dialog.getDialogPane().lookupButton(ButtonType.CANCEL))).setText("Отмена");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            String phrase = dialog.getEditor().getText();
            currentList = SQLQueriesStore.searchByTranslation(phrase);
            tableFill(currentList);
            refreshButton.setVisible(true);
        });
    }

    public void menuFilterEventClicked(ActionEvent actionEvent) {
        ChoiceDialog<String> dialog = new ChoiceDialog<String>();
        dialog.getItems().addAll(SQLQueriesStore.getEventTitleList());
        ImageView img = new ImageView(this.getClass().getResource("/images/filterIcon.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        dialog.setGraphic(img);
        dialog.setTitle("Фильтр по событиям");
        dialog.setHeaderText("Выберите событие для фильтра");
        ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        ((Button)(dialog.getDialogPane().lookupButton(ButtonType.CANCEL))).setText("Отмена");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            String event = dialog.getSelectedItem();
            if(event != null) {
                currentList = SQLQueriesStore.searchByEvent(event);
                tableFill(currentList);
                refreshButton.setVisible(true);
            }
        });
    }

    public void menuFilterPersonClicked(ActionEvent actionEvent) {
        ChoiceDialog<String> dialog = new ChoiceDialog<String>();
        dialog.getItems().addAll(SQLQueriesStore.getPersonList());
        ImageView img = new ImageView(this.getClass().getResource("/images/filterIcon.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        dialog.setGraphic(img);
        dialog.setTitle("Фильтр по персонам");
        dialog.setHeaderText("Выберите персону для фильтра");
        ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        ((Button)(dialog.getDialogPane().lookupButton(ButtonType.CANCEL))).setText("Отмена");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            String person = dialog.getSelectedItem();

            if(person != null) {
                currentList = SQLQueriesStore.searchByPerson(person);
                tableFill(currentList);
                refreshButton.setVisible(true);
            }
        });
    }

    public void menuSetCustomFilterClicked(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FilterForm.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        Stage stage = new Stage();
        stage.setTitle("Составной фильтр");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(dataTableView.getScene().getWindow());
        FilterFormController controller = loader.getController();
        controller.setParentController(this);
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        stage.show();
        refreshButton.setVisible(true);
    }

    public void menuSearchMorphologicClicked() {

        Dialog<Triple<Boolean, Boolean, String>> dialog = new Dialog<>();
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
        CheckBox cb = new CheckBox("Точный поиск");

        TextField to = new TextField();
        to.setPromptText("Поиск: ");
        to.setPrefWidth(350);
        Label label = new Label("Язык запроса:");

        gridPane.add(label,0,0);
        gridPane.add(rb1, 1, 0);
        gridPane.add(rb2, 2,0);
        gridPane.add(cb, 0, 1);
        gridPane.add(new Label("Поиск"), 0, 2);
        gridPane.add(to, 1, 2, 2,1);

        dialog.getDialogPane().setContent(gridPane);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return Triple.of(rb1.isSelected(),cb.isSelected(), to.getText());
            }
            return null;
        });
        ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));


        Optional<Triple<Boolean, Boolean, String>> result1 = dialog.showAndWait();

        result1.ifPresent(triple -> {
            if(triple.getLeft()) {
                if(triple.getMiddle()) {
                    if(!triple.getRight().isEmpty()) {
                        try {
                            currentList = SQLQueriesStore.searchAccurateRu(triple.getRight(), false, new ArrayList<>());
                            tableFill(currentList);
                            refreshButton.setVisible(true);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    if(!triple.getRight().isEmpty()) {
                        currentList = SQLQueriesStore.searchMorphologicalRu(triple.getRight(), false);
                        tableFill(currentList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButton.setVisible(true);
            }
            else {
                if(triple.getMiddle()) {
                    if(!triple.getRight().isEmpty()) {
                        try {
                            currentList = SQLQueriesStore.searchAccurateEn(triple.getRight(), false, new ArrayList<>());
                            tableFill(currentList);
                            refreshButton.setVisible(true);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    if(!triple.getRight().isEmpty()) {
                        currentList = SQLQueriesStore.searchMorphologicalEn(triple.getRight(), false);
                        tableFill(currentList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButton.setVisible(true);
            }
        });
    }

    public void menuImportClicked(ActionEvent actionEvent) {
        FileHandler fileHandler = new FileHandler();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл импорта");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLSX","*.xlsx"));
        File file = fileChooser.showOpenDialog(dataTableView.getScene().getWindow());
        fileHandler.importData(file.getPath());
        tableFill(SQLQueriesStore.defaultList());
    }

    public void refreshButtonClicked(ActionEvent actionEvent) {
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
        ((Button)(dialog.getDialogPane().lookupButton(ButtonType.CANCEL))).setText("Отмена");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField from = new TextField();
        from.setPromptText("Логин");
        PasswordField to = new PasswordField();
        to.setPromptText("Пароль");
        from.textProperty().addListener(
                ((observableValue, s, t1) -> {
                    if (from.getText().isEmpty() || to.getText().isEmpty())
                        dialog.getDialogPane().lookupButton(loginButtonType).setDisable(true);
                    else dialog.getDialogPane().lookupButton(loginButtonType).setDisable(false);
                })
        );

        to.textProperty().addListener(
                ((observableValue, s, t1) -> {
                    if (from.getText().isEmpty() || to.getText().isEmpty())
                        dialog.getDialogPane().lookupButton(loginButtonType).setDisable(true);
                    else dialog.getDialogPane().lookupButton(loginButtonType).setDisable(false);
                })
        );

        gridPane.add(new Label("Логин"), 0,0);
        gridPane.add(from, 1, 0);
        gridPane.add(new Label("Пароль:"), 0, 1);
        gridPane.add(to, 1, 1);

        dialog.getDialogPane().setContent(gridPane);
        dialog.getDialogPane().lookupButton(loginButtonType).setDisable(true);

        Platform.runLater(from::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(from.getText(), to.getText());
            }
            return null;
        });
        ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));


        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            if(Authentication.checkUser(pair.getKey(), pair.getValue())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Вход");
                alert.setHeaderText("Успешный вход");
                alert.showAndWait();
                showAdminFunctions();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Вход");
                alert.setHeaderText("Неправильные данные");
                alert.showAndWait();
            }
        });
    }

    public void showAdminFunctions() {
        menuImport.setVisible(true);
        menuEdit.setVisible(true);
        dataTableView.removeEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, filter);
        menuLogin.setVisible(false);
    }

    public void menuFilterTypeClicked(ActionEvent actionEvent) {
        ChoiceDialog<String> dialog = new ChoiceDialog<String>();
        dialog.getItems().addAll(SQLQueriesStore.getTypesList());
        ImageView img = new ImageView(this.getClass().getResource("/images/filterIcon.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        dialog.setGraphic(img);
        dialog.setTitle("Фильтр по типам");
        dialog.setHeaderText("Выберите тип для фильтра");
        ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

        ((Button)(dialog.getDialogPane().lookupButton(ButtonType.CANCEL))).setText("Отмена");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            String person = dialog.getSelectedItem();
            if(person != null) {
                currentList = SQLQueriesStore.searchByType(person);
                tableFill(currentList);
                refreshButton.setVisible(true);
            }
        });
    }

    public void setMode(List<String> args) {
        if(args.contains("-admin")) {
            dataTableView.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, filter);
            menuEdit.setVisible(false);
            menuImport.setVisible(false);
            menuLogin.setVisible(true);
            if(args.size() == 3)
            {
                if(Authentication.checkUser(args.get(1), args.get(2)))
                {
                    showAdminFunctions();
                }
            }
        } else {
            dataTableView.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, filter);
            menuEdit.setVisible(false);
            menuImport.setVisible(false);
            menuLogin.setVisible(false);
        }
    }

    public void menuSearchMorphologicalTranslatr(ActionEvent actionEvent) {
        Translate translate = new Translate();

        if(!translate.checkConnection())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Отсутствует подключение к сети.");
            alert.setContentText("Для использования поиска с переводом, необходимо наличие подключения к сети Интернет");
            alert.showAndWait();
            return;
        }

        Dialog<Triple<Boolean, Boolean, String>> dialog = new Dialog<>();
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
        CheckBox cb = new CheckBox("Точный поиск");


        TextField to = new TextField();
        to.setPromptText("Поиск: ");
        to.setPrefWidth(350);
        Label label = new Label("Язык запроса:");

        gridPane.add(label,0,0);
        gridPane.add(rb1, 1, 0);
        gridPane.add(rb2, 2,0);
        gridPane.add(cb, 0, 1);
        gridPane.add(new Label("Поиск"), 0, 2);
        gridPane.add(to, 1, 2,2,1);

        dialog.getDialogPane().setContent(gridPane);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return Triple.of(rb1.isSelected(),cb.isSelected(), to.getText());
            }
            return null;
        });
        ((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));


        Optional<Triple<Boolean, Boolean, String>> result1 = dialog.showAndWait();

        result1.ifPresent(triple -> {
            if(triple.getLeft()) {
                if(triple.getMiddle()) {
                    if(!triple.getRight().isEmpty()) {
                        try {
                            currentList = SQLQueriesStore.searchAccurateRu(triple.getRight(), true, new ArrayList<>());
                            tableFill(currentList);
                            refreshButton.setVisible(true);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    if(!triple.getRight().isEmpty()) {
                        currentList = SQLQueriesStore.searchMorphologicalRu(triple.getRight(), true);
                        tableFill(currentList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButton.setVisible(true);
            }
            else {
                if(triple.getMiddle()) {
                    if(!triple.getRight().isEmpty()) {
                        try {
                            currentList = SQLQueriesStore.searchAccurateRu(triple.getRight(), true, new ArrayList<>());
                            tableFill(currentList);
                            refreshButton.setVisible(true);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    if(!triple.getRight().isEmpty()) {
                        currentList = SQLQueriesStore.searchMorphologicalEn(triple.getRight(), true);
                        tableFill(currentList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshButton.setVisible(true);
            }
        });
    }

    public void menuExportCurrent(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(dataTableView.getScene().getWindow());

        if(currentList.size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не найдены данные");
            alert.setContentText("Отсутствуют данные для экспорта");
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

            alert.showAndWait();
            return;
        }

        if (selectedDirectory != null) {
            fileHandler.exportData(currentList, selectedDirectory.getAbsolutePath());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Экспорт");
            alert.setHeaderText("Файл успешо создан");
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("images/icon.png"))));

            alert.showAndWait();
        }
    }
}