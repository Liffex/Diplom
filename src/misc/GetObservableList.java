package misc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.pullenti.morph.*;
import org.sqlite.Function;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.loader.JMorfSdkFactory;
import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.ms.internal.IOmoForm;
import db.TestModel;
import ru.textanalysis.tawt.ms.storage.OmoFormList;
import sample.Word;

import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GetObservableList {

    static Connection conn = TestModel.getConnection();
    static JMorfSdk jMorfSdk = JMorfSdkFactory.loadFullLibrary();
    static {
        try {
            Morphology.initialize(MorphLang.EN);
            Morphology.initialize(MorphLang.RU);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public static ObservableList<Word> searchMorphologicalRu(String textToSearch) throws Exception {
        Function.create(conn, "REGEXP", new Function() {
            @Override
            protected void xFunc() throws SQLException {
                String expression = value_text(0);
                String value = value_text(1);
                if (value == null)
                    value = "";

                Pattern pattern=Pattern.compile(expression);
                result(pattern.matcher(value).find() ? 1 : 0);
            }
        });

        Function.create(conn, "toLower", new Function() {
            @Override
            protected void xFunc() throws SQLException {
                String value = value_text(0);
                result(value.toLowerCase());
            }
        });

        String sqlSearchByTranslation = "SELECT engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent) " +
                "JOIN source ON (engRuTranslation.idSource = source.idSource) " +
                "WHERE ((' ' || toLower(ruTranslation.ruTranslation) || ' ' LIKE ?) OR " +
                "(' ' || toLower(person.personName) || ' ' LIKE ?) OR (' ' || toLower(event.eventTitle) || ' ' LIKE ?) OR " +
                "(' ' || context.contextText || ' ' LIKE ?) OR (' ' || source.sourceURL || ' ' LIKE ?) OR " +
                "(' ' || source.sourceTitle || ' ' LIKE ?) OR (' ' || source.sourceDescription || ' ' LIKE ?))";

        List<String> words = new ArrayList<>();
        Collections.addAll(words, textToSearch.split("\\s|,\\s*"));
        List<String> allForms = new ArrayList<>();

        words.removeIf(str -> jMorfSdk.getTypeOfSpeeches(str).contains(MorfologyParameters.TypeOfSpeech.UNION) ||
                jMorfSdk.getTypeOfSpeeches(str).contains(MorfologyParameters.TypeOfSpeech.PARTICLE) ||
                jMorfSdk.getTypeOfSpeeches(str).contains(MorfologyParameters.TypeOfSpeech.PRETEXT) ||
                jMorfSdk.getTypeOfSpeeches(str).contains(MorfologyParameters.TypeOfSpeech.INTERJECTION));


        for (String str: words) {

            OmoFormList omoForms = jMorfSdk.getAllCharacteristicsOfForm(str.toLowerCase());
            List<IOmoForm> iOmoForms = jMorfSdk.getAllCharacteristicsOfForm(omoForms.get(0).getInitialFormString());
            List<Byte> usedType = new ArrayList<>();
            for (IOmoForm form: iOmoForms) {
                if(!usedType.contains(form.getTypeOfSpeech())) {
                    allForms.add(form.getInitialFormString());
                    allForms.addAll(jMorfSdk.getDerivativeForm(form.getInitialFormString(), form.getTypeOfSpeech()));
                }
                usedType.add(form.getTypeOfSpeech());
            }
        }

        List<String> newList = allForms.stream().distinct().collect(Collectors.toList());


        ObservableList<Word> result = FXCollections.observableArrayList();
        String ruText;
        String engText;

        //if (TranslateAPI.detectLanguage(textToSearch).equals("ru"))
        //{
        //    ruText = textToSearch;
        //    engText = Translate.translateRuEn(textToSearch);
        //} else {
        //    engText = textToSearch;
        //    ruText = Translate.translateEnRu(textToSearch);
        //}


        for(String str: newList) {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByTranslation)) {
                pstmt.setString(1, "% " + str + " %");
                pstmt.setString(2, "% " + str + " %");
                pstmt.setString(3, "% " + str + " %");
                pstmt.setString(4, "% " + str + " %");
                pstmt.setString(5, "% " + str + " %");
                pstmt.setString(6, "% " + str + " %");
                pstmt.setString(7, "% " + str + " %");
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
    public static ObservableList<Word> searchMorphologicalEn(String textToSearch) throws Exception {
        Function.create(conn, "REGEXP", new Function() {
            @Override
            protected void xFunc() throws SQLException {
                String expression = value_text(0);
                String value = value_text(1);
                if (value == null)
                    value = "";

                Pattern pattern=Pattern.compile(expression);
                result(pattern.matcher(value).find() ? 1 : 0);
            }
        });

        Function.create(conn, "toLower", new Function() {
            @Override
            protected void xFunc() throws SQLException {
                String value = value_text(0);
                result(value.toLowerCase());
            }
        });

        String sqlSearchByTranslation = "SELECT engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent) " +
                "JOIN source ON (engRuTranslation.idSource = source.idSource) " +
                "WHERE ((' ' || toLower(ruTranslation.ruTranslation) || ' ' LIKE ?) OR " +
                "(' ' || toLower(person.personName) || ' ' LIKE ?) OR (' ' || toLower(event.eventTitle) || ' ' LIKE ?) OR " +
                "(' ' || context.contextText || ' ' LIKE ?) OR (' ' || source.sourceURL || ' ' LIKE ?) OR " +
                "(' ' || source.sourceTitle || ' ' LIKE ?) OR (' ' || source.sourceDescription || ' ' LIKE ?))";

        List<String> words = new ArrayList<>();
        Collections.addAll(words, textToSearch.split("\\s|,\\s*"));
        List<String> allForms = new ArrayList<>();

        //words.removeIf(str -> );

        ArrayList<MorphWordForm> forms = Morphology.getAllWordforms("отомстить", MorphLang.RU);
        for(MorphWordForm form: forms)
            System.out.println(form.toString());

        for (String str: words) {

            OmoFormList omoForms = jMorfSdk.getAllCharacteristicsOfForm(str.toLowerCase());
            List<IOmoForm> iOmoForms = jMorfSdk.getAllCharacteristicsOfForm(omoForms.get(0).getInitialFormString());
            List<Byte> usedType = new ArrayList<>();
            for (IOmoForm form: iOmoForms) {
                if(!usedType.contains(form.getTypeOfSpeech())) {
                    allForms.add(form.getInitialFormString());
                    allForms.addAll(jMorfSdk.getDerivativeForm(form.getInitialFormString(), form.getTypeOfSpeech()));
                }
                usedType.add(form.getTypeOfSpeech());
            }
        }

        List<String> newList = allForms.stream().distinct().collect(Collectors.toList());


        ObservableList<Word> result = FXCollections.observableArrayList();
        String ruText;
        String engText;

        //if (TranslateAPI.detectLanguage(textToSearch).equals("ru"))
        //{
        //    ruText = textToSearch;
        //    engText = Translate.translateRuEn(textToSearch);
        //} else {
        //    engText = textToSearch;
        //    ruText = Translate.translateEnRu(textToSearch);
        //}


        for(String str: newList) {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByTranslation)) {
                pstmt.setString(1, "% " + str + " %");
                pstmt.setString(2, "% " + str + " %");
                pstmt.setString(3, "% " + str + " %");
                pstmt.setString(4, "% " + str + " %");
                pstmt.setString(5, "% " + str + " %");
                pstmt.setString(6, "% " + str + " %");
                pstmt.setString(7, "% " + str + " %");
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
