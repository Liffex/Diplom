package misc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.SQLiteConfig;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.loader.JMorfSdkFactory;
import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.ms.internal.IOmoForm;
import db.TestModel;
import sample.Word;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class GetObservableList {

    static Connection conn = TestModel.getConnection();
    static JMorfSdk jMorfSdk = JMorfSdkFactory.loadFullLibrary();

    public static ObservableList<Word> searchByTranslation(String textToSearch) throws SQLException {
        ObservableList<Word> result = FXCollections.observableArrayList();

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
            pstmt.setString(1, '%' + textToSearch.toLowerCase() + '%');
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Word(rs.getString("engPhrase"),
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
        }
        return result;
    }
    public static ObservableList<Word> searchByPhrase(String textToSearch) throws SQLException {
        ObservableList<Word> result = FXCollections.observableArrayList();

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
            pstmt.setString(1, '%' + textToSearch.toLowerCase() + '%');
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Word(rs.getString("engPhrase"),
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
        }
        return result;
    }
    public static ObservableList<Word> searchByKeyWord(String textToSearch) throws SQLException {
        ObservableList<Word> result = FXCollections.observableArrayList();

        String sqlSearchByKeyWord = "SELECT engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource) WHERE (keyWord.keyWord = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByKeyWord)) {
            pstmt.setString(1, textToSearch);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Word(rs.getString("engPhrase"),
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
        }
        return result;
    }
    public static ObservableList<Word> searchByEvent(String textToSearch) throws SQLException {
        ObservableList<Word> result = FXCollections.observableArrayList();

        String sqlSearchByEvent = "SELECT engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource) WHERE (event.eventTitle = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByEvent)) {
            pstmt.setString(1, textToSearch);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Word(rs.getString("engPhrase"),
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
        }
        return result;
    }
    public static ObservableList<Word> searchByPerson(String textToSearch) throws SQLException {
        ObservableList<Word> result = FXCollections.observableArrayList();

        String sqlSearchByPerson = "SELECT engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource) WHERE (person.personName = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByPerson)) {
            pstmt.setString(1, textToSearch);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Word(rs.getString("engPhrase"),
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
        }
        return result;
    }
    public static ObservableList<Word> defaultList() throws SQLException {
        ObservableList<Word> result = FXCollections.observableArrayList();

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
                result.add(new Word(rs.getString("engPhrase"),
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
        }
        return result;
    }
    public static ObservableList<String> getPersonList() throws SQLException {
        String sqlGetPersonList = "SELECT personName FROM person";
        ObservableList<String> result = FXCollections.observableArrayList();

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlGetPersonList)) {
            while (rs.next()) {
                result.add(rs.getString("personName"));
            }
        }
        return result;
    }
    public static ObservableList<String> getKeyWordList() throws SQLException {
        String sqlGetKeyWordList = "SELECT keyWord FROM keyWord";
        ObservableList<String> result = FXCollections.observableArrayList();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlGetKeyWordList)) {
            while (rs.next()) {
                result.add(rs.getString("keyWord"));
            }
        }
        return result;
    }
    public static ObservableList<Event> getEventList() throws SQLException {
        String sqlGetEventList = "SELECT eventTitle, eventDate, idAccurate FROM event";
        ObservableList<Event> result = FXCollections.observableArrayList();

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlGetEventList)) {
            while (rs.next()) {
                if (!rs.getBoolean("isAccurate")) {
                    String eventDateTemp = rs.getString("eventDate");
                    result.add(
                            new Event(rs.getString("eventTitle"),
                                    eventDateTemp.substring(eventDateTemp.indexOf('.') + 1)));
                } else {
                    result.add(new Event(rs.getString("eventTitle"),
                            rs.getString("eventDate")));
                }
            }
            return result;
        }
    }
    public static ObservableList<String> getEventTitleList() throws SQLException {
        String sqlGetEventTitleList = "SELECT eventTitle FROM event";
        ObservableList<String> result = FXCollections.observableArrayList();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlGetEventTitleList)) {
            while (rs.next()) {
                result.add(rs.getString("eventTitle"));
            }
        }
        return result;
    }
    public static ObservableList<Integer> getIdPhrasesUsingKeyWordList(int idKeyWord) throws SQLException {
        String sqlGetPhrasesUsingKeyWord = "SELECT idEngPhrase FROM engPhrase WHERE (idkeyWords = ?)";
        ObservableList<Integer> result = FXCollections.observableArrayList();

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPhrasesUsingKeyWord)) {
            pstmt.setString(1, String.valueOf(idKeyWord));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
                result.add(rs.getInt("idEngPhrase"));
        }
        return result;
    }
    public static ObservableList<Word> filterList (String sqlFilter) throws SQLException {
        ObservableList<Word> result = FXCollections.observableArrayList();

        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlFilter)) {
            while (rs.next())
                result.add(new Word(rs.getString("engPhrase"),
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

        return result;
    }

    public static ObservableList<Word> searchMorphological(String textToSearch) throws Exception {
        ObservableList<Word> result = FXCollections.observableArrayList();
        String ruText;
        String engText;

        if (TranslateAPI.detectLanguage(textToSearch).equals("ru"))
        {
            ruText = textToSearch;
            engText = Translate.translateRuEn(textToSearch);
        } else {
            engText = textToSearch;
            ruText = Translate.translateEnRu(textToSearch);
        }

        String sqlSearchByTranslation = "SELECT engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource) " +
                "WHERE ((ruTranslation.ruTranslation LIKE ?) OR " +
                "(keyWord.keyWord LIKE ?) OR (person.personName LIKE ?) OR (event.eventTitle LIKE ?) OR " +
                "(engPhrase.engPhrase = ?) OR (context.contextText = ?) OR (source.sourceURL LIKE ?) OR " +
                "(source.sourceTitle LIKE ?) OR (source.sourceDescription LIKE ?))";

        List<String> formsRu = jMorfSdk.getDerivativeForm(textToSearch, MorfologyParameters.TypeOfSpeech.NOUN, MorfologyParameters.TypeOfSpeech.VERB);
        for(String str: formsRu) {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByTranslation)) {
                pstmt.setString(1, "%" + ruText + "%");
                pstmt.setString(2, "%" + ruText + "%");
                pstmt.setString(3, "%" + ruText + "%");
                pstmt.setString(4, "%" + ruText + "%");
                pstmt.setString(5, "%" + ruText + "%");
                pstmt.setString(6, "%" + ruText + "%");
                pstmt.setString(7, "%" + ruText + "%");
                pstmt.setString(8, "%" + ruText + "%");
                pstmt.setString(9, "%" + ruText + "%");
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())
                    result.add(new Word(rs.getString("engPhrase"),
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
        }
        return result;
    }
}
