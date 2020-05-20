package misc.sql;

import db.DBConnection;

import java.sql.*;

public class SQLCommands {
    static String sqlGetKeyWordId = "SELECT idKeyWord FROM keyWord WHERE (keyWord = ?)";
    static String sqlGetPhraseId = "SELECT idEngPhrase FROM engPhrase WHERE (engPhrase = ?)";
    static String sqlGetPhrase = "SELECT engPhrase FROM engPhrase WHERE (idEngPhrase = ?)";
    static String sqlGetTranslationId = "SELECT idRuTranslation FROM ruTranslation WHERE (ruTranslation = ?)";
    static String sqlGetTranslation = "SELECT ruTranslation FROM ruTranslation WHERE (idRuTranslation = ?)";
    static String sqlGetEventId = "SELECT idEvent FROM event WHERE (eventTitle = ?)";
    static String sqlGetEventTitle = "SELECT eventTitle FROM event WHERE (idEvent = ?)";
    static String sqlGetSourceTitle = "SELECT sourceTitle FROM source WHERE (idSource = ?)";
    static String sqlGetSourceURL = "SELECT sourceURL FROM source WHERE (idSource = ?)";
    static String sqlGetSourceDescription = "SELECT sourceDescription FROM source WHERE (idSource = ?)";
    static String sqlGetContextText = "SELECT contextText FROM context WHERE (idContext = ?)";
    static String sqlGetContextId = "SELECT idContext FROM context WHERE (contextText = ?)";
    static String sqlGetPassword = "SELECT password FROM user WHERE (username = ?)";
    static String sqlGetTypeId = "SELECT idType FROM type WHERE (typeTitle = ?)";
    static String getPhraseFromPair = "SELECT idEngPhrase FROM engRuTranslation WHERE idPair =?";
    static String getTranslationFromPair = "SELECT idRuTranslation FROM engRuTranslation WHERE idPair =?";

    static String sqlGetPhraseKeyWord = "SELECT keyWord FROM engPhrase JOIN keyWord ON (engPhrase.idKeyWords = keyWord.idKeyWord) WHERE (engPhrase.idEngPhrase = ?)";

    static String getPersonId = "SELECT idPerson FROM person WHERE (personName = ?)";
    static String sqlGetPersonName = "SELECT personName FROM person WHERE (idPerson = ?)";

    static String sqlGetPersonIdFromPair = "SELECT idPerson FROM engRuTranslation WHERE idPair =?";
    static String sqlGetEventIdFromPair = "SELECT idEvent FROM engRuTranslation WHERE idPair =?";
    static String sqlGetContextIdFromPair = "SELECT idContext FROM engRuTranslation WHERE idPair =?";
    static String sqlGetSourceIdFromPair = "SELECT idSource FROM engRuTranslation WHERE idPair =?";
    static String sqlGetTypeIdFromPair = "SELECT idType FROM engRuTranslation WHERE idPair = ?";
    static String sqlGetPairId = "SELECT idPair FROM engRuTranslation WHERE ((idEngPhrase = ?) AND (idRuTranslation = ?))";

    static String sqlDeletePerson = "DELETE FROM person WHERE (personName = ?)";
    static String sqlDeletePair = "DELETE FROM engRuTranslation WHERE idPair = ?";
    static String sqlDeleteEvent = "DELETE FROM event WHERE (eventTitle = ?)";
    static String sqlDeleteKeyWord = "DELETE FROM keyWord WHERE (keyWord = ?)";
    static String sqlDeletePhrase = "DELETE FROM engPhrase WHERE (idEngPhrase = ?)";
    static String sqlDeleteType = "DELETE FROM type WHERE (typeTitle = ?)";

    static String sqlAddPerson = "INSERT INTO person(PersonName) VALUES (?)";
    static String sqlAddContext = "INSERT INTO context(contextText) VALUES(?)";
    static String sqlAddSource = "INSERT INTO source(sourceTitle, sourceURL, sourceDescription) VALUES(?,?,?)";
    static String sqlAddPhrase = "INSERT INTO engPhrase(engPhrase, idKeyWords) VALUES (?,?)";
    static String sqlAddRuTranslation = "INSERT INTO ruTranslation(ruTranslation) VALUES(?)";
    static String sqlAddEvent = "INSERT INTO event(eventTitle, eventDate, isAccurate) VALUES (?,?,?)";
    static String sqlAddPair = "INSERT INTO engRuTranslation(idEngPhrase, idRuTranslation, idSource, idEvent, idPerson, idContext, idType) VALUES( ?, ?, ?, ?, ?, ?, ?)";
    static String sqlAddKeyWord = "INSERT INTO keyWord(keyWord) VALUES (?)";
    static String sqlAddUser = "INSERT INTO user(username, password, role) VALUES (?, ?, ?)";
    static String sqlAddType = "INSERT INTO type(typeTitle) VALUES (?)";

    static String sqlCheckPersonINPair = "SELECT idPerson FROM engRuTranslation WHERE (engRuTranslation.idPerson = ?)";
    static String sqlCheckSource = "SELECT idSource, sourceTitle, sourceURL, sourceDescription FROM source WHERE ((sourceTitle = ?) AND (sourceURL = ?) AND (sourceDescription = ?))";
    static String sqlCheckPhrase = "SELECT idEngPhrase FROM engPhrase WHERE (engPhrase = ?)";
    static String sqlCheckTranslation = "SELECT idRuTranslation FROM ruTranslation WHERE (ruTranslation = ?)";
    static String sqlCheckContext = "SELECT idContext FROM context WHERE (contextText = ?)";
    static String sqlCheckEvent = "SELECT eventTitle FROM event WHERE (eventTitle = ?)";
    static String sqlCheckEventInPair = "SELECT idEngPhrase FROM engRuTranslation WHERE (engRuTranslation.idEvent = ?)";
    static String sqlCheckPairWithPhrase = "SELECT idEngPhrase FROM engRuTranslation WHERE (idEngPhrase = ?)";
    static String sqlCheckUsername = "SELECT idUser FROM user WHERE (username = ?)";
    static String sqlCheckTypePair = "SELECT idType FROM engRuTranslation WHERE (idType = ?)";
    static String sqlGetTypeTitle = "SELECT typeTitle FROM type WHERE idType = ?";
    static String sqlCheckKeyWord = "SELECT idKeyWord FROM keyWord WHERE keyWord =?";
    static String sqlCheckPerson = "SELECT idPerson FROM person WHERE personName =?";
    static String sqlCheckPair = "SELECT idPair FROM engRuTranslation WHERE ((idEngPhrase = ?) AND (idRuTranslation = ?))";

    static String sqlUpdate = "UPDATE engRuTranslation SET idEngPhrase = ?, " +
            "idRuTranslation = ?, " +
            "idSource = ?, " +
            "idEvent = ?, " +
            "idPerson = ?, " +
            "idContext = ?," +
            "idType = ? WHERE idPair =?";

    static Connection conn = DBConnection.getConnection();

    public static int getPhraseId(String phrase) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPhraseId)) {
            pstmt.setString(1, phrase);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idEngPhrase");
        }
    }
    public static String getPhrase(int idPhrase) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPhrase)) {
            pstmt.setString(1, String.valueOf(idPhrase));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("engPhrase");
        }
    }
    public static int getTranslationId(String translation) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetTranslationId)) {
            pstmt.setString(1, translation);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idRuTranslation");
        }
    }
    public static String getTranslation(int idTranslation) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetTranslation)){
            pstmt.setString(1, String.valueOf(idTranslation));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("ruTranslation");
        }
    }
    public static int getPersonId(String person) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(getPersonId)) {
            pstmt.setString(1, person);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idPerson");
        }
    }
    public static String getPersonName(int idPerson) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPersonName)) {
            pstmt.setString(1, String.valueOf(idPerson));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("personName");
        }
    }
    public static int getKeyWordId(String keyWord) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetKeyWordId)) {
            pstmt.setString(1, keyWord);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idKeyWord");
        }
    }
    public static String getPhraseKeyWord(int idPhrase) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPhraseKeyWord)){
            pstmt.setString(1, String.valueOf(idPhrase));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("keyWord");
        }
    }
    public static int getEventId(String eventTitle) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetEventId)) {
            pstmt.setString(1, eventTitle);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idEvent");
        }
    }
    public static int getPairId(String phr, String tra) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPairId)) {
            pstmt.setString(1, String.valueOf(getPhraseId(phr)));
            pstmt.setString(2, String.valueOf(getTranslationId(tra)));
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idPair");
        }
    }
    public static int getTypeIdFromPair(int idPair) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetTypeIdFromPair)) {
            pstmt.setString(1, String.valueOf(idPair));
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idType");
        }
    }
    public static String getEventTitle(int idEvent) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetEventTitle)){
            pstmt.setString(1, String.valueOf(idEvent));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("eventTitle");
        }
    }
    public static String getSourceTitle(int idSource) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetSourceTitle)) {
            pstmt.setString(1, String.valueOf(idSource));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("sourceTitle");
        }
    }
    public static String getSourceDescription(int idSource) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetSourceDescription)) {
            pstmt.setString(1, String.valueOf(idSource));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("sourceDescription");
        }
    }
    public static String getSourceURL(int idSource) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetSourceURL)) {
            pstmt.setString(1, String.valueOf(idSource));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("sourceURL");
        }
    }
    public static int getSourceIdFullCompare(String sourceTitle, String sourceURL, String sourceDesc) throws SQLException {
        int sourceId = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckSource)) {
            pstmt.setString(1, sourceTitle);
            pstmt.setString(2, sourceURL);
            pstmt.setString(3,sourceDesc);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                sourceId = rs.getInt("idSource");
        }
        return sourceId;
    }
    public static String getPassword(String username) throws SQLException {
        try(PreparedStatement pstmt = conn.prepareStatement(sqlGetPassword)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("password");
        }
    }

    public static String getContextText(int idContext) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetContextText)) {
            pstmt.setString(1, String.valueOf(idContext));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("contextText");
        }
    }
    public static int getContextId(String contextText) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetContextId)) {
            pstmt.setString(1, contextText);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idContext");
        }
    }

    public static int getPersonIdFromPair(int idPair) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPersonIdFromPair)) {
            pstmt.setString(1, String.valueOf(idPair));
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idPerson");
        }
    }
    public static int getEventIdFromPair(int idPair) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetEventIdFromPair)) {
            pstmt.setString(1, String.valueOf(idPair));
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idEvent");
        }
    }
    public static int getContextIdFromPair(int idPair) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetContextIdFromPair)) {
            pstmt.setString(1, String.valueOf(idPair));
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idContext");
        }
    }
    public static int getSourceIdFromPair(int idPair) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetSourceIdFromPair)) {
            pstmt.setString(1, String.valueOf(idPair));
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idSource");
        }
    }

    public static void deletePair(int idPair) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDeletePair)) {
            pstmt.setString(1, String.valueOf(idPair));
            pstmt.executeUpdate();
        }
    }
    public static void deletePerson(String personName) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDeletePerson)) {
            pstmt.setString(1, personName);
            pstmt.executeUpdate();
        }
    }
    public static void deleteEvent(String eventTitle) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteEvent)) {
            pstmt.setString(1, eventTitle);
            pstmt.executeUpdate();
        }
    }
    public static void deletePhrase(int idPhrase) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDeletePhrase)) {
            pstmt.setString(1, String.valueOf(idPhrase));
            pstmt.executeUpdate();
        }
    }
    public static void deleteKeyWord(int idKeyWord) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteKeyWord)) {
            pstmt.setString(1, String.valueOf(idKeyWord));
            pstmt.executeUpdate();
        }
    }

    public static int addSource(String sourceTitle, String sourceURL, String sourceDescription) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddSource, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, sourceTitle);
            pstmt.setString(2, sourceURL);
            pstmt.setString(3, sourceDescription);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    public static int addContextText(String textToAdd) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddContext, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, textToAdd);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    public static void addPerson(String personName) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddPerson)) {
            pstmt.setString(1, personName);
            pstmt.executeUpdate();
        }
    }

    public static int addPersonGetId(String personName) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddPerson, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, personName);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            return rs.getInt(1);
        }
    }

    public static int addPhrase(String phrase, int idKeyWord) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddPhrase, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, phrase);
            pstmt.setString(2, String.valueOf(idKeyWord));
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    public static int addTranslation(String translation) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddRuTranslation, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, translation);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    public static void addEvent(String eventTitle, String eventDate, boolean isAccurate) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddEvent)) {
            pstmt.setString(1, eventTitle);
            if (isAccurate) {
                pstmt.setString(2, eventDate);
                pstmt.setString(3, "1");
            } else {
                pstmt.setString(2, eventDate);
                pstmt.setString(3, "0");
            }
            pstmt.executeUpdate();
        }
    }

    public static int addEventGetId(String eventTitle, String eventDate, boolean isAccurate) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddEvent, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, eventTitle);
            if (isAccurate) {
                pstmt.setString(2, eventDate);
                pstmt.setString(3, "1");
            } else {
                pstmt.setString(2, eventDate);
                pstmt.setString(3, "0");
            }
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            return rs.getInt(1);
        }
    }
    public static void addPair(int idPhrase, int idTransl, int idSource, int idEvent, int idPerson, int idContext, int idType) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddPair)) {
            pstmt.setString(1, String.valueOf(idPhrase));
            pstmt.setString(2, String.valueOf(idTransl));
            pstmt.setString(3, String.valueOf(idSource));
            pstmt.setString(4, String.valueOf(idEvent));
            pstmt.setString(5, String.valueOf(idPerson));
            pstmt.setString(6, String.valueOf(idContext));
            pstmt.setString(7, String.valueOf(idType));
            pstmt.executeUpdate();
        }
    }
    public static int addKeyWordGetId(String keyWord) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddKeyWord, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, keyWord);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            return rs.getInt(1);
        }
    }

    public static void addKeyWord(String keyWord) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddKeyWord)) {
            pstmt.setString(1, keyWord);
            pstmt.executeUpdate();
        }
    }

    public static boolean checkPairPerson(String personName) throws SQLException {
        int idPerson = getPersonId(personName);
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPersonINPair)) {
            pstmt.setString(1, String.valueOf(idPerson));
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return true;
        }
        return false;
    }
    public static boolean checkSource(String sourceTitle, String sourceURL, String sourceDesc) throws SQLException {
        boolean sourceExists = false;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckSource)) {
            pstmt.setString(1, sourceTitle);
            pstmt.setString(2, sourceURL);
            pstmt.setString(3,sourceDesc);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                sourceExists = true;
        }
        return sourceExists;
    }
    public static boolean checkPhrase(String phrase) throws SQLException {
        boolean phraseExists = false;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPhrase)) {
            pstmt.setString(1, phrase);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                phraseExists = true;
        }
        return phraseExists;
    }

    public static boolean checkKeyWord(String word) throws SQLException {
        boolean keyWordExists = false;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckKeyWord)) {
            pstmt.setString(1, word);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                keyWordExists = true;
            return keyWordExists;
        }
    }

    public static boolean checkPerson(String person) throws SQLException {
        boolean personExists = false;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPerson)) {
            pstmt.setString(1, person);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                personExists = true;
            return personExists;


        }
    }

    public static boolean checkTranslation(String translation) throws SQLException {
        boolean translationExists = false;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckTranslation)) {
            pstmt.setString(1, translation);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                translationExists = true;
        }
        return translationExists;


    }
    public static boolean checkContext(String context) throws SQLException {
        boolean contextExists = false;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckContext)) {
            pstmt.setString(1, context);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                contextExists = true;
        }
        return contextExists;


    }
    public static boolean checkEvent(String eventTitle) throws SQLException {
        boolean eventExists = false;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckEvent)) {
            pstmt.setString(1, eventTitle);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                eventExists = true;
        }
        return eventExists;
    }
    public static boolean checkEventInPair (int idEvent) throws SQLException {
        boolean eventUsed = false;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckEventInPair)) {
            pstmt.setString(1, String.valueOf(idEvent));
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                eventUsed = true;
        }
        return eventUsed;
    }
    public static boolean checkPairUsingPhrase (int idPhrase) throws SQLException {
        boolean pairExists = false;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPairWithPhrase)) {
            pstmt.setString(1, String.valueOf(idPhrase));
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                pairExists = true;
        }
        return pairExists;
    }
    public static boolean checkUsername(String username) throws SQLException {
        boolean userExists = false;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckUsername)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                userExists = true;
        }
        return userExists;
    }

    public static void updatePair(int idPhrase,int idTransl, int idSource, int idEvent, int idPerson, int idContext, int idType, int idPair) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            pstmt.setInt(1, idPhrase);
            pstmt.setInt(2, idTransl);
            pstmt.setInt(3, idSource);
            pstmt.setInt(4, idEvent);
            pstmt.setInt(5, idPerson);
            pstmt.setInt(6, idContext);
            pstmt.setInt(7, idType);
            pstmt.setInt(8,idPair);

            pstmt.executeUpdate();
        }
    }

    public static void addUser(String username, String password, String role) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddUser)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
        }
    }

    public static void deleteType(String selectedItem) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteType)) {
            pstmt.setString(1, selectedItem);
            pstmt.executeUpdate();
        }
    }

    public static boolean checkPairType(String selectedItem) throws SQLException {

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckTypePair)) {
            pstmt.setString(1, String.valueOf(getTypeId(selectedItem)));
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    public static int getTypeId(String selectedItem) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetTypeId)) {
            pstmt.setString(1, selectedItem);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idType");
        }
    }

    public static void addType(String typeTitle) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddType)){
            pstmt.setString(1, typeTitle);
            pstmt.executeUpdate();
        }
    }

    public static int getPhraseIdFromPair(int idPairG) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(getPhraseFromPair)) {
            pstmt.setInt(1, idPairG);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idEngPhrase");
        }
    }

    public static int getTranslationIdFromPair(int idPairG) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(getTranslationFromPair)) {
            pstmt.setInt(1, idPairG);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idRuTranslation");
        }
    }

    public static String getTypeTitle(int idType) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetTypeTitle)) {
            pstmt.setInt(1, idType);
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("typeTitle");
        }
    }

    public static boolean checkType(String type) throws SQLException {
        boolean typeExists = false;
        String sqlCheckType = "SELECT idType FROM type WHERE (typeTitle = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckType)){
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                typeExists = true;
            return typeExists;
        }
    }

    public static int addTypeGetId(String typeTitle) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddType, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, typeTitle);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            return rs.getInt(1);
        }
    }

    public static boolean checkPair(int idPhrase, int idTransl) throws SQLException {
        boolean pairEx = false;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPair)) {
            pstmt.setInt(1, idPhrase);
            pstmt.setInt(2, idTransl);
            ResultSet rs  = pstmt.executeQuery();
            if(rs.next())
                pairEx = true;
            return pairEx;
        }
    }

    public static void deleteUser (String username) throws SQLException {
        String sqlDeleteUser = "DELETE FROM user WHERE (userName = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteUser)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        }
    }
}
