package misc.sql;

import com.pullenti.morph.*;
import com.pullenti.ner.Sdk;
import com.pullenti.ner.Token;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import misc.Translate;
import misc.data.Event;
import misc.data.Word;
import org.sqlite.Function;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.loader.JMorfSdkFactory;
import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.ms.internal.IOmoForm;
import db.DBConnection;
import ru.textanalysis.tawt.ms.storage.OmoFormList;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class SQLQueriesStore {

    static Connection conn = DBConnection.getConnection();
    static JMorfSdk jMorfSdk = JMorfSdkFactory.loadFullLibrary();

    public static ObservableList<Word> searchByTranslation(String textToSearch) throws SQLException {
        ObservableList<Word> result = FXCollections.observableArrayList();

        String sqlSearchByTranslation = "SELECT idPair, typeTitle, engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource)" +
                "JOIN type ON (engRuTranslation.idType = type.idType) WHERE (ruTranslation.ruTranslation LIKE ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByTranslation)) {
            pstmt.setString(1, '%' + textToSearch.toLowerCase() + '%');
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Word(rs.getInt("idPair"),
                        rs.getString("engPhrase"),
                        rs.getString("keyWord"),
                        rs.getString("ruTranslation"),
                        rs.getString("personName"),
                        rs.getString("contextText"),
                        rs.getString("eventTitle"),
                        rs.getString("eventDate"),
                        rs.getBoolean("isAccurate"),
                        rs.getString("sourceTitle"),
                        rs.getString("sourceURL"),
                        rs.getString("sourceDescription"),
                        rs.getString("typeTitle")));
            }
        }
        return result;
    }
    public static ObservableList<Word> searchByPhrase(String textToSearch) throws SQLException {
        ObservableList<Word> result = FXCollections.observableArrayList();

        String sqlSearchByPhrase = "SELECT idPair, typeTitle, engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource)" +
                "JOIN type ON (engRuTranslation.idType = type.idType) WHERE (engPhrase.engPhrase LIKE ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByPhrase)) {
            pstmt.setString(1, '%' + textToSearch.toLowerCase() + '%');
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Word(rs.getInt("idPair"),
                        rs.getString("engPhrase"),
                        rs.getString("keyWord"),
                        rs.getString("ruTranslation"),
                        rs.getString("personName"),
                        rs.getString("contextText"),
                        rs.getString("eventTitle"),
                        rs.getString("eventDate"),
                        rs.getBoolean("isAccurate"),
                        rs.getString("sourceTitle"),
                        rs.getString("sourceURL"),
                        rs.getString("sourceDescription"),
                        rs.getString("typeTitle")));
            }
        }
        return result;
    }
    public static ObservableList<Word> filterByKeyWord(String textToSearch) throws SQLException {
        ObservableList<Word> result = FXCollections.observableArrayList();

        String sqlSearchByKeyWord = "SELECT idPair, typeTitle, engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource)" +
                "JOIN type ON (engRuTranslation.idType = type.idType) WHERE (keyWord.keyWord = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByKeyWord)) {
            pstmt.setString(1, textToSearch);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Word(rs.getInt("idPair"),
                        rs.getString("engPhrase"),
                        rs.getString("keyWord"),
                        rs.getString("ruTranslation"),
                        rs.getString("personName"),
                        rs.getString("contextText"),
                        rs.getString("eventTitle"),
                        rs.getString("eventDate"),
                        rs.getBoolean("isAccurate"),
                        rs.getString("sourceTitle"),
                        rs.getString("sourceURL"),
                        rs.getString("sourceDescription"),
                        rs.getString("typeTitle")));
            }
        }
        return result;
    }
    public static ObservableList<Word> searchByEvent(String textToSearch) throws SQLException {
        ObservableList<Word> result = FXCollections.observableArrayList();
        String sqlSearchByEvent = "idPair, typeTitle, SELECT engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource)" +
                "JOIN type ON (engRuTranslation.idType = type.idType) WHERE (event.eventTitle = ?)";


        try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByEvent)) {
            pstmt.setString(1, textToSearch);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Word(rs.getInt("idPair"),
                        rs.getString("engPhrase"),
                        rs.getString("keyWord"),
                        rs.getString("ruTranslation"),
                        rs.getString("personName"),
                        rs.getString("contextText"),
                        rs.getString("eventTitle"),
                        rs.getString("eventDate"),
                        rs.getBoolean("isAccurate"),
                        rs.getString("sourceTitle"),
                        rs.getString("sourceURL"),
                        rs.getString("sourceDescription"),
                        rs.getString("typeTitle")));
            }
        }
        return result;
    }
    public static ObservableList<Word> searchByPerson(String textToSearch) throws SQLException {
        ObservableList<Word> result = FXCollections.observableArrayList();

        String sqlSearchByPerson = "SELECT idPair, typeTitle, engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource)" +
                "JOIN type ON (engRuTranslation.idType = type.idType) WHERE (person.personName = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByPerson)) {
            pstmt.setString(1, textToSearch);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Word(rs.getInt("idPair"),
                        rs.getString("engPhrase"),
                        rs.getString("keyWord"),
                        rs.getString("ruTranslation"),
                        rs.getString("personName"),
                        rs.getString("contextText"),
                        rs.getString("eventTitle"),
                        rs.getString("eventDate"),
                        rs.getBoolean("isAccurate"),
                        rs.getString("sourceTitle"),
                        rs.getString("sourceURL"),
                        rs.getString("sourceDescription"),
                        rs.getString("typeTitle")));
            }
        }
        return result;
    }
    public static ObservableList<Word> defaultList() throws SQLException {
        ObservableList<Word> result = FXCollections.observableArrayList();

        String sql = "SELECT idPair, typeTitle, engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource)" +
                "JOIN type ON (engRuTranslation.idType = type.idType)";
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.add(new Word(rs.getInt("idPair"),
                        rs.getString("engPhrase"),
                        rs.getString("keyWord"),
                        rs.getString("ruTranslation"),
                        rs.getString("personName"),
                        rs.getString("contextText"),
                        rs.getString("eventTitle"),
                        rs.getString("eventDate"),
                        rs.getBoolean("isAccurate"),
                        rs.getString("sourceTitle"),
                        rs.getString("sourceURL"),
                        rs.getString("sourceDescription"),
                        rs.getString("typeTitle")));
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
        String sqlGetEventList = "SELECT eventTitle, eventDate, isAccurate FROM event";
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
                result.add(new Word(rs.getInt("idPair"),
                        rs.getString("engPhrase"),
                        rs.getString("keyWord"),
                        rs.getString("ruTranslation"),
                        rs.getString("personName"),
                        rs.getString("contextText"),
                        rs.getString("eventTitle"),
                        rs.getString("eventDate"),
                        rs.getBoolean("isAccurate"),
                        rs.getString("sourceTitle"),
                        rs.getString("sourceURL"),
                        rs.getString("sourceDescription"),
                        rs.getString("typeTitle")));
        }

        return result;
    }

    public static ObservableList<Word> searchMorphologicalRu(String textToSearch, boolean translation) throws Exception {
        Function.create(conn, "toLower", new Function() {
            @Override
            protected void xFunc() throws SQLException {
                String value = value_text(0);
                result(value.toLowerCase());
            }
        });

        String sqlSearchByTranslation = "SELECT idPair, typeTitle, engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent) " +
                "JOIN source ON (engRuTranslation.idSource = source.idSource)" +
                "JOIN type ON (engRuTranslation.idType = type.idType) " +
                "WHERE ((toLower(ruTranslation.ruTranslation) LIKE ?) OR " +
                "(toLower(person.personName) LIKE ?) OR (toLower(event.eventTitle) LIKE ?) OR " +
                "(toLower(context.contextText) LIKE ?) OR (toLower(source.sourceURL) LIKE ?) OR " +
                "(toLower(source.sourceTitle) LIKE ?) OR (toLower(source.sourceDescription) LIKE ?)) OR " +
                "(toLower(type.typeTitle) LIKE ?)";

        List<String> words = new ArrayList<>();
        Collections.addAll(words, textToSearch.split("\\s|,\\s*"));
        List<String> allForms = new ArrayList<>();

        words.removeIf(str -> jMorfSdk.getTypeOfSpeeches(str).contains(MorfologyParameters.TypeOfSpeech.UNION) ||
                jMorfSdk.getTypeOfSpeeches(str).contains(MorfologyParameters.TypeOfSpeech.PARTICLE) ||
                jMorfSdk.getTypeOfSpeeches(str).contains(MorfologyParameters.TypeOfSpeech.PRETEXT) ||
                jMorfSdk.getTypeOfSpeeches(str).contains(MorfologyParameters.TypeOfSpeech.INTERJECTION));


        for (String str: words) {
            OmoFormList omoForms = jMorfSdk.getAllCharacteristicsOfForm(str.toLowerCase());
            if(!omoForms.isEmpty()) {
                List<IOmoForm> iOmoForms = jMorfSdk.getAllCharacteristicsOfForm(omoForms.get(0).getInitialFormString());
                List<Byte> usedType = new ArrayList<>();
                for (IOmoForm form : iOmoForms) {
                    if (!usedType.contains(form.getTypeOfSpeech())) {
                        allForms.add(form.getInitialFormString());
                        allForms.addAll(jMorfSdk.getDerivativeForm(form.getInitialFormString(), form.getTypeOfSpeech()));
                    }
                    usedType.add(form.getTypeOfSpeech());
                }
            }
        }

        List<String> newList = allForms.stream().distinct().collect(Collectors.toList());


        ObservableList<Word> result = FXCollections.observableArrayList();
        List<Integer> addedIds = new ArrayList<>();

        for(String str: newList) {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByTranslation)) {
                pstmt.setString(1, "%" + str + "%");
                pstmt.setString(2, "%" + str + "%");
                pstmt.setString(3, "%" + str + "%");
                pstmt.setString(4, "%" + str + "%");
                pstmt.setString(5, "%" + str + "%");
                pstmt.setString(6, "%" + str + "%");
                pstmt.setString(7, "%" + str + "%");
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    if(!addedIds.contains(rs.getInt("idPair"))) {
                        addedIds.add(rs.getInt("idPair"));
                        result.add(new Word(rs.getInt("idPair"),
                                rs.getString("engPhrase"),
                                rs.getString("keyWord"),
                                rs.getString("ruTranslation"),
                                rs.getString("personName"),
                                rs.getString("contextText"),
                                rs.getString("eventTitle"),
                                rs.getString("eventDate"),
                                rs.getBoolean("isAccurate"),
                                rs.getString("sourceTitle"),
                                rs.getString("sourceURL"),
                                rs.getString("sourceDescription"),
                                rs.getString("typeTitle")));
                    }
                }
            }
        }

        if(translation)
        {
            ArrayList<String> toTrnslate = new ArrayList<>();
            for(String str: newList)
            {
                if(jMorfSdk.isInitialForm(str) == 1 || jMorfSdk.isInitialForm(str) == 0)
                {
                    toTrnslate.add(Translate.translateRuEn(str));
                }
            }
            result.addAll(searchMorphologicalEn(toTrnslate, addedIds));
        }
        return result;
    }
    public static ObservableList<Word> searchMorphologicalRu(ArrayList<String> textToSearch, List<Integer> addedIds) throws Exception {
        Function.create(conn, "toLower", new Function() {
            @Override
            protected void xFunc() throws SQLException {
                String value = value_text(0);
                result(value.toLowerCase());
            }
        });

        String sqlSearchByTranslation = "SELECT idPair, typeTitle, engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent) " +
                "JOIN source ON (engRuTranslation.idSource = source.idSource)" +
                "JOIN type ON (engRuTranslation.idType = type.idType) " +
                "WHERE ((toLower(ruTranslation.ruTranslation) LIKE ?) OR " +
                "(toLower(person.personName) LIKE ?) OR (toLower(event.eventTitle) LIKE ?) OR " +
                "(toLower(context.contextText) LIKE ?) OR (toLower(source.sourceURL) LIKE ?) OR " +
                "(toLower(source.sourceTitle) LIKE ?) OR (toLower(source.sourceDescription) LIKE ?)) OR " +
                "(toLower(type.typeTitle) LIKE ?)";

        List<String> allForms = new ArrayList<>();

        for (String str: textToSearch) {
            OmoFormList omoForms = jMorfSdk.getAllCharacteristicsOfForm(str.toLowerCase());
            if(!omoForms.isEmpty()) {
                List<IOmoForm> iOmoForms = jMorfSdk.getAllCharacteristicsOfForm(omoForms.get(0).getInitialFormString());
                List<Byte> usedType = new ArrayList<>();
                for (IOmoForm form : iOmoForms) {
                    if (!usedType.contains(form.getTypeOfSpeech())) {
                        allForms.add(form.getInitialFormString());
                        allForms.addAll(jMorfSdk.getDerivativeForm(form.getInitialFormString(), form.getTypeOfSpeech()));
                    }
                    usedType.add(form.getTypeOfSpeech());
                }
            }
        }

        List<String> newList = allForms.stream().distinct().collect(Collectors.toList());

        ObservableList<Word> result = FXCollections.observableArrayList();

        for(String str: newList) {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByTranslation)) {
                pstmt.setString(1, "%" + str + "%");
                pstmt.setString(2, "%" + str + "%");
                pstmt.setString(3, "%" + str + "%");
                pstmt.setString(4, "%" + str + "%");
                pstmt.setString(5, "%" + str + "%");
                pstmt.setString(6, "%" + str + "%");
                pstmt.setString(7, "%" + str + "%");
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    if(!addedIds.contains(rs.getInt("idPair"))) {
                        addedIds.add(rs.getInt("idPair"));
                        result.add(new Word(rs.getInt("idPair"),
                                rs.getString("engPhrase"),
                                rs.getString("keyWord"),
                                rs.getString("ruTranslation"),
                                rs.getString("personName"),
                                rs.getString("contextText"),
                                rs.getString("eventTitle"),
                                rs.getString("eventDate"),
                                rs.getBoolean("isAccurate"),
                                rs.getString("sourceTitle"),
                                rs.getString("sourceURL"),
                                rs.getString("sourceDescription"),
                                rs.getString("typeTitle")));
                    }
                }
            }
        }
        return result;
    }

    public static ObservableList<Word> searchMorphologicalEn(String textToSearch, boolean translation) throws Exception {

        Morphology.initialize(MorphLang.EN);
        List<Integer> addedIds = new ArrayList<>();

        Function.create(conn, "toLower", new Function() {
            @Override
            protected void xFunc() throws SQLException {
                String value = value_text(0);
                result(value.toLowerCase());
            }
        });

        String sqlSearchByTranslation = "SELECT idPair, typeTitle, engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent) " +
                "JOIN source ON (engRuTranslation.idSource = source.idSource)" +
                "JOIN type ON (engRuTranslation.idType = type.idType) " +
                "WHERE ((toLower(engPhrase.engPhrase) LIKE ?) OR " +
                "(toLower(keyWord.keyWord) LIKE ?) OR " +
                "(toLower(source.sourceURL) LIKE ?) OR " +
                "(toLower(source.sourceTItle) LIKE ?))";

        List<String> allForms = new ArrayList<>();

        ArrayList<MorphToken> test = Morphology.process(textToSearch, MorphLang.EN, null);

        test.removeIf(morphToken -> morphToken.charInfo.value == 0);

        test.removeIf(morphToken -> morphToken.wordForms.get(0).tag.toString().contains(MorphClass.PREPOSITION.toString()) ||
                morphToken.wordForms.get(0).tag.toString().contains(MorphClass.CONJUNCTION.toString()));

        ArrayList<MorphWordForm> testForms = new ArrayList<>();

        for (MorphToken token:test) {
            for(MorphWordForm form : Morphology.getAllWordforms(token.term, MorphLang.EN)) {
                allForms.add(form.normalCase);
                testForms.addAll(Morphology.getAllWordforms(token.term, MorphLang.EN));
            }
        }


        List<String> newList = allForms.stream().distinct().collect(Collectors.toList());

        ObservableList<Word> result = FXCollections.observableArrayList();

        for(String str: newList) {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByTranslation)) {
                pstmt.setString(1, "%" + str + "%");
                pstmt.setString(2, "%" + str + "%");
                pstmt.setString(3, "%" + str + "%");
                pstmt.setString(4, "%" + str + "%");
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())
                    if(!addedIds.contains(rs.getInt("idPair"))) {
                        addedIds.add(rs.getInt("idPair"));
                        result.add(new Word(rs.getInt("idPair"),
                                rs.getString("engPhrase"),
                                rs.getString("keyWord"),
                                rs.getString("ruTranslation"),
                                rs.getString("personName"),
                                rs.getString("contextText"),
                                rs.getString("eventTitle"),
                                rs.getString("eventDate"),
                                rs.getBoolean("isAccurate"),
                                rs.getString("sourceTitle"),
                                rs.getString("sourceURL"),
                                rs.getString("sourceDescription"),
                                rs.getString("typeTitle")));
                    }
            }
        }

        if(translation)
        {
            ArrayList<String> toTrnslate = new ArrayList<>();
            for(MorphToken token: test)
            {
                toTrnslate.add(Translate.translateEnRu(token.wordForms.get(0).normalCase));
            }
            result.addAll(searchMorphologicalRu(toTrnslate, addedIds));
        }

        return result;
    }
    public static ObservableList<Word> searchMorphologicalEn(ArrayList<String> textToSearch, List<Integer> addedIds) throws Exception {

        Morphology.initialize(MorphLang.EN);

        Function.create(conn, "toLower", new Function() {
            @Override
            protected void xFunc() throws SQLException {
                String value = value_text(0);
                result(value.toLowerCase());
            }
        });

        String sqlSearchByTranslation = "SELECT idPair, typeTitle, engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent) " +
                "JOIN source ON (engRuTranslation.idSource = source.idSource)" +
                "JOIN type ON (engRuTranslation.idType = type.idType) " +
                "WHERE ((toLower(engPhrase.engPhrase) LIKE ?) OR " +
                "(toLower(keyWord.keyWord) LIKE ?) OR " +
                "(toLower(source.sourceURL) LIKE ?) OR " +
                "(toLower(source.sourceTItle) LIKE ?))";

        List<String> allForms = new ArrayList<>();

        for (String str:textToSearch) {
            for(MorphWordForm form : Morphology.getAllWordforms(str.toUpperCase(), MorphLang.EN))
                allForms.add(form.normalCase);
        }

        List<String> newList = allForms.stream().distinct().collect(Collectors.toList());

        ObservableList<Word> result = FXCollections.observableArrayList();

        for(String str: newList) {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByTranslation)) {
                pstmt.setString(1, "%" + str + "%");
                pstmt.setString(2, "%" + str + "%");
                pstmt.setString(3, "%" + str + "%");
                pstmt.setString(4, "%" + str + "%");
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())
                    if(!addedIds.contains(rs.getInt("idPair"))) {
                        addedIds.add(rs.getInt("idPair"));
                        result.add(new Word(rs.getInt("idPair"),
                                rs.getString("engPhrase"),
                                rs.getString("keyWord"),
                                rs.getString("ruTranslation"),
                                rs.getString("personName"),
                                rs.getString("contextText"),
                                rs.getString("eventTitle"),
                                rs.getString("eventDate"),
                                rs.getBoolean("isAccurate"),
                                rs.getString("sourceTitle"),
                                rs.getString("sourceURL"),
                                rs.getString("sourceDescription"),
                                rs.getString("typeTitle")));
                    }
            }
        }
        return result;
    }

    public static ObservableList<String> getTypesList() throws SQLException {
        String sqlGetPersonList = "SELECT typeTitle FROM type";
        ObservableList<String> result = FXCollections.observableArrayList();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlGetPersonList)) {
            while (rs.next()) {
                result.add(rs.getString("typeTitle"));
            }
        }
        return result;
    }

    public static ObservableList<Word> searchByType(String type) throws SQLException {
        ObservableList<Word> result = FXCollections.observableArrayList();

        String sqlSearchByType = "SELECT idPair, typeTitle, engPhrase, keyWord, ruTranslation, personName, contextText, " +
                "eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDescription FROM engRuTranslation " +
                "JOIN engPhrase ON (engRuTranslation.idEngPhrase = engPhrase.idEngPhrase)" +
                "JOIN keyWord ON (keyWord.idKeyWord = engPhrase.idKeyWords)" +
                "JOIN ruTranslation ON (engRuTranslation.idRuTranslation = ruTranslation.idRuTranslation)" +
                "JOIN person ON (engRuTranslation.idPerson = person.idPerson)" +
                "JOIN context ON (engRuTranslation.idContext = context.idContext)" +
                "JOIN event ON (engRuTranslation.idEvent = event.idEvent)" +
                "JOIN source ON (engRuTranslation.idSource = source.idSource)" +
                "JOIN type ON (engRuTranslation.idType = type.idType) WHERE (type.typeTitle = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlSearchByType)) {
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new Word(rs.getInt("idPair"),
                        rs.getString("engPhrase"),
                        rs.getString("keyWord"),
                        rs.getString("ruTranslation"),
                        rs.getString("personName"),
                        rs.getString("contextText"),
                        rs.getString("eventTitle"),
                        rs.getString("eventDate"),
                        rs.getBoolean("isAccurate"),
                        rs.getString("sourceTitle"),
                        rs.getString("sourceURL"),
                        rs.getString("sourceDescription"),
                        rs.getString("typeTitle")));
            }
        }
        return result;
    }
}
