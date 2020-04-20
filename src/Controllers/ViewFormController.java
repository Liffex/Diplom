package Controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.TestModel;
import sample.Word;

public class ViewFormController {

    TestModel testModel = new TestModel();
    private ObservableList<Word> rawData = FXCollections.observableArrayList();
    private ObservableList<Word> displayData = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;
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
    private Label isConnected;

    @FXML
    void initialize() throws SQLException {
        if (TestModel.isDbConnected())
            isConnected.setText("Connected");
        else isConnected.setText("Not connected");

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
        tableFill();
    }

    private void tableFill() throws SQLException {
        rawData.clear();
        displayData.clear();
        Connection conn = TestModel.getConnection();
        String sql = "SELECT engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource)";
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {

                String eventDate = rs.getString("eventDate");
                boolean isAccurate = rs.getBoolean("isAccurate");

                String newDate;

                if(eventDate == null)
                    newDate = "01.0001";
                else {
                    if (isAccurate) {
                        newDate = eventDate;
                    } else {
                        newDate = eventDate.substring(eventDate.indexOf('.') + 1);
                    }
                }

                rawData.add(new Word(rs.getString("engPhrase"),
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

                displayData.add(new Word(rs.getString("engPhrase"),
                        rs.getString("keyWord"),
                        rs.getString("ruTranslation"),
                        rs.getString("personName"),
                        rs.getString("contextText"),
                        rs.getString("eventTitle"),
                        newDate,
                        rs.getBoolean("isAccurate"),
                        rs.getString("sourceTitle"),
                        rs.getString("sourceURL"),
                        rs.getString("sourceDescription")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void tableFill(ObservableList<Word> list) throws SQLException {
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

    private void exportData() throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Лист 1");
        Row row;
        Cell cell;
        int rownum = 0;

        if(rawData.isEmpty())
        {
            System.out.println("Пусто");
        }
        else {
            for (Word wd : rawData) {
                row = sheet.createRow(rownum);

                    //KeyWord
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(wd.getKeyWord());
                    //Eng
                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellValue(wd.getPhrase());
                    //ru
                    cell = row.createCell(2, CellType.STRING);
                    cell.setCellValue(wd.getTranslation());
                    //ev
                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue(wd.getEventTitle());
                    //date
                    cell = row.createCell(4, CellType.STRING);
                    cell.setCellValue(wd.getEventDate());
                    //isAccurate
                    cell = row.createCell(5, CellType.STRING);
                    cell.setCellValue(wd.getIsAccurate());
                    //person
                    cell = row.createCell(6, CellType.STRING);
                    cell.setCellValue(wd.getPerson());
                    //sourceTitle
                    cell = row.createCell(7, CellType.STRING);
                    cell.setCellValue(wd.getSourceTitle());
                    //sourceURL
                    cell = row.createCell(8, CellType.STRING);
                    cell.setCellValue(wd.getSourceURL());
                    //sourceDesc
                    cell = row.createCell(9, CellType.STRING);
                    cell.setCellValue(wd.getSourceDescription());
                    //context
                    cell = row.createCell(10, CellType.STRING);
                    cell.setCellValue(wd.getContext());

                rownum++;
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy!MM!dd(HH!mm)");
            LocalDateTime now = LocalDateTime.now();
            String stringCurrentTime = dtf.format(now);
            File file = new File("./export/Export " + stringCurrentTime + ".xlsx");

            if (file.createNewFile()) {
                System.out.println("created");
            } else {
                System.out.println("Already exists");
            }

            FileOutputStream outFile = new FileOutputStream(file);
            wb.write(outFile);
            System.out.println("writed");
        }
    }

    public void menuExportClicked(ActionEvent actionEvent) throws IOException {
        exportData();
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
                tableFill();
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
                tableFill();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stage.setScene(scene);

        String sqlGetPhraseId = "SELECT idEngPhrase FROM engPhrase WHERE (engPhrase = ?)";
        String sqlGetTranlationId = "SELECT idRuTranslation FROM ruTranslation WHERE (ruTranslation = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPhraseId)) {
            pstmt.setString(1, dataTableView.getSelectionModel().getSelectedItem().getPhrase());
            ResultSet rs = pstmt.executeQuery();
            idPhrase = rs.getInt("idEngPhrase");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetTranlationId)) {
            pstmt.setString(1, dataTableView.getSelectionModel().getSelectedItem().getTranslation());
            ResultSet rs = pstmt.executeQuery();
            idTranslation = rs.getInt("idRuTranslation");
        }

        AddingFormController controller = loader.getController();
        controller.setEditingMode(idPhrase,idTranslation);
        stage.show();
    }

    public void menuDeleteClicked(ActionEvent actionEvent) throws SQLException {
        Connection conn = TestModel.getConnection();
        String sqlDelete = "DELETE FROM engRuTranslation " +
                "WHERE ((idEngPhrase = ?) AND (idRuTranslation = ?))";
        int idEngPhrase;
        int idRuTranslation;
        String sqlGetEngPhraseId = "SELECT idEngPhrase FROM engPhrase WHERE (engPhrase = ?)";
        String sqlGetRuTranslationId = "SELECT idRuTranslation FROM ruTranslation WHERE (ruTranslation = ?)";

        for (Word wd: dataTableView.getSelectionModel().getSelectedItems()) {
            System.out.println(wd.getPerson());
            try (PreparedStatement pstmt = conn.prepareStatement(sqlGetEngPhraseId)) {
                pstmt.setString(1, wd.getPhrase());
                ResultSet rs = pstmt.executeQuery();
                idEngPhrase = rs.getInt("idEngPhrase");
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sqlGetRuTranslationId)) {
                pstmt.setString(1, wd.getTranslation());
                ResultSet rs = pstmt.executeQuery();
                idRuTranslation = rs.getInt("idRuTranslation");
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
                pstmt.setString(1, String.valueOf(idEngPhrase));
                pstmt.setString(2, String.valueOf(idRuTranslation));
                pstmt.executeUpdate();
            }
        }

        tableFill();
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

        ObservableList<Word> list = FXCollections.observableArrayList();
        String sqlSearchByPhrase = "SELECT engPhrase, keyWord, ruTranslation, personName, contextText, " +
        "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource) WHERE (engPhrase.engPhrase LIKE ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByPhrase)) {
            pstmt.setString(1, '%' + phrase.toLowerCase() + '%');
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new Word(rs.getString("engPhrase"),
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

            tableFill(list);
        }
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

        ObservableList<Word> list = FXCollections.observableArrayList();
        String sqlSearchByTranslation = "SELECT engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource) WHERE (ruTranslation.ruTranslation LIKE ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByTranslation)) {
            pstmt.setString(1, '%' + phrase.toLowerCase() + '%');
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new Word(rs.getString("engPhrase"),
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

            tableFill(list);
        }
    }

    public void menuSearchEventClicked(ActionEvent actionEvent) {
    }

    public void menuSearchPersonClicked(ActionEvent actionEvent) {
    }

    public void menuRefreshClicked(ActionEvent actionEvent) throws SQLException {
        tableFill();
    }

    public void menuSetFilterClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FilterForm.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(dataTableView.getScene().getWindow());
        FilterFormController controller = loader.getController();
        stage.setOnCloseRequest(windowEvent -> {
            try {
                tableFill(controller.getWordList());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stage.setScene(scene);
        stage.show();
    }
}