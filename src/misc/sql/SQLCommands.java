package misc.sql;
import db.DBConnection;
import misc.data.Event;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLCommands {

    private static Logger log = Logger.getLogger(SQLCommands.class.getName());
    static Connection conn = DBConnection.getConnection();

    public static int getPhraseId(String phrase) {
        String sqlGetPhraseId = "SELECT idEngPhrase FROM engPhrase WHERE (engPhrase = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPhraseId)) {
            pstmt.setString(1, phrase);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idEngPhrase");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }
    public static String getPhrase(int idPhrase) {
        String sqlGetPhrase = "SELECT engPhrase FROM engPhrase WHERE (idEngPhrase = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPhrase)) {
            pstmt.setString(1, String.valueOf(idPhrase));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("engPhrase");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return null;
    }
    public static int getTranslationId(String translation) {
        String sqlGetTranslationId = "SELECT idRuTranslation FROM ruTranslation WHERE (ruTranslation = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetTranslationId)) {
            pstmt.setString(1, translation);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idRuTranslation");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }
    public static String getTranslation(int idTranslation) {
        String sqlGetTranslation = "SELECT ruTranslation FROM ruTranslation WHERE (idRuTranslation = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetTranslation)){
            pstmt.setString(1, String.valueOf(idTranslation));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("ruTranslation");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return null;
    }
    public static int getPersonId(String person) {
        String getPersonId = "SELECT idPerson FROM person WHERE (personName = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(getPersonId)) {
            pstmt.setString(1, person);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idPerson");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static String getPersonName(int idPerson) {
        String sqlGetPersonName = "SELECT personName FROM person WHERE (idPerson = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPersonName)) {
            pstmt.setString(1, String.valueOf(idPerson));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("personName");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return null;
    }
    public static int getKeyWordId(String keyWord) {
        String sqlGetKeyWordId = "SELECT idKeyWord FROM keyWord WHERE (keyWord = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetKeyWordId)) {
            pstmt.setString(1, keyWord);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idKeyWord");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static String getPhraseKeyWord(int idPhrase) {
         String sqlGetPhraseKeyWord = "SELECT keyWord FROM engPhrase JOIN keyWord ON (engPhrase.idKeyWords = keyWord.idKeyWord) WHERE (engPhrase.idEngPhrase = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPhraseKeyWord)){
            pstmt.setString(1, String.valueOf(idPhrase));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("keyWord");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int getEventId(String eventTitle) {
         String sqlGetEventId = "SELECT idEvent FROM event WHERE (eventTitle = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetEventId)) {
            pstmt.setString(1, eventTitle);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idEvent");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }
    public static int getPairId(String phr, String tra) {
         String sqlGetPairId = "SELECT idPair FROM engRuTranslation WHERE ((idEngPhrase = ?) AND (idRuTranslation = ?))";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPairId)) {
            pstmt.setString(1, String.valueOf(getPhraseId(phr)));
            pstmt.setString(2, String.valueOf(getTranslationId(tra)));
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idPair");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }
    public static int getTypeIdFromPair(int idPair)  {
         String sqlGetTypeIdFromPair = "SELECT idType FROM engRuTranslation WHERE idPair = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetTypeIdFromPair)) {
            pstmt.setString(1, String.valueOf(idPair));
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idType");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static String getEventTitle(int idEvent)  {
         String sqlGetEventTitle = "SELECT eventTitle FROM event WHERE (idEvent = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetEventTitle)){
            pstmt.setString(1, String.valueOf(idEvent));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("eventTitle");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getSourceTitle(int idSource)  {
         String sqlGetSourceTitle = "SELECT sourceTitle FROM source WHERE (idSource = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetSourceTitle)) {
            pstmt.setString(1, String.valueOf(idSource));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("sourceTitle");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getSourceDescription(int idSource)  {
         String sqlGetSourceDescription = "SELECT sourceDescription FROM source WHERE (idSource = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetSourceDescription)) {
            pstmt.setString(1, String.valueOf(idSource));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("sourceDescription");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getSourceURL(int idSource)  {
         String sqlGetSourceURL = "SELECT sourceURL FROM source WHERE (idSource = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetSourceURL)) {
            pstmt.setString(1, String.valueOf(idSource));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("sourceURL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int getSourceIdFullCompare(String sourceTitle, String sourceURL, String sourceDesc)  {
        int sourceId = 0;
         String sqlCheckSource = "SELECT idSource, sourceTitle, sourceURL, sourceDescription FROM source WHERE ((sourceTitle = ?) AND (sourceURL = ?) AND (sourceDescription = ?))";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckSource)) {
            pstmt.setString(1, sourceTitle);
            pstmt.setString(2, sourceURL);
            pstmt.setString(3,sourceDesc);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                sourceId = rs.getInt("idSource");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return sourceId;
    }
    public static String getPassword(String username)  {
         String sqlGetPassword = "SELECT password FROM user WHERE (username = ?)";

        try(PreparedStatement pstmt = conn.prepareStatement(sqlGetPassword)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("password");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return null;
    }

    public static String getContextText(int idContext)  {
         String sqlGetContextText = "SELECT contextText FROM context WHERE (idContext = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetContextText)) {
            pstmt.setString(1, String.valueOf(idContext));
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("contextText");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return null;
    }
    public static int getContextId(String contextText)  {
         String sqlGetContextId = "SELECT idContext FROM context WHERE (contextText = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetContextId)) {
            pstmt.setString(1, contextText);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idContext");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }

    public static int getPersonIdFromPair(int idPair)  {
         String sqlGetPersonIdFromPair = "SELECT idPerson FROM engRuTranslation WHERE idPair =?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetPersonIdFromPair)) {
            pstmt.setString(1, String.valueOf(idPair));
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idPerson");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }
    public static int getEventIdFromPair(int idPair)  {
         String sqlGetEventIdFromPair = "SELECT idEvent FROM engRuTranslation WHERE idPair =?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetEventIdFromPair)) {
            pstmt.setString(1, String.valueOf(idPair));
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idEvent");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }
    public static int getContextIdFromPair(int idPair)  {
         String sqlGetContextIdFromPair = "SELECT idContext FROM engRuTranslation WHERE idPair =?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetContextIdFromPair)) {
            pstmt.setString(1, String.valueOf(idPair));
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idContext");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }
    public static int getSourceIdFromPair(int idPair)  {
         String sqlGetSourceIdFromPair = "SELECT idSource FROM engRuTranslation WHERE idPair =?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetSourceIdFromPair)) {
            pstmt.setString(1, String.valueOf(idPair));
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idSource");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }

    public static void deletePair(int idPair)  {
         String sqlDeletePair = "DELETE FROM engRuTranslation WHERE idPair = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDeletePair)) {
            pstmt.setString(1, String.valueOf(idPair));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }
    public static void deletePerson(String personName)  {
         String sqlDeletePerson = "DELETE FROM person WHERE (personName = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDeletePerson)) {
            pstmt.setString(1, personName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }
    public static void deleteEvent(String eventTitle)  {
         String sqlDeleteEvent = "DELETE FROM event WHERE (eventTitle = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteEvent)) {
            pstmt.setString(1, eventTitle);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }
    public static void deletePhrase(int idPhrase)  {
         String sqlDeletePhrase = "DELETE FROM engPhrase WHERE (idEngPhrase = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDeletePhrase)) {
            pstmt.setString(1, String.valueOf(idPhrase));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }
    public static void deleteKeyWord(int idKeyWord)  {
         String sqlDeleteKeyWord = "DELETE FROM keyWord WHERE (keyWord = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteKeyWord)) {
            pstmt.setString(1, String.valueOf(idKeyWord));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }

    public static int addSource(String sourceTitle, String sourceURL, String sourceDescription)  {
         String sqlAddSource = "INSERT INTO source(sourceTitle, sourceURL, sourceDescription) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddSource, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, sourceTitle);
            pstmt.setString(2, sourceURL);
            pstmt.setString(3, sourceDescription);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }
    public static int addContextText(String textToAdd)  {
         String sqlAddContext = "INSERT INTO context(contextText) VALUES(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddContext, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, textToAdd);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }
    public static void addPerson(String personName)  {
         String sqlAddPerson = "INSERT INTO person(PersonName) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddPerson)) {
            pstmt.setString(1, personName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }
    public static int addPersonGetId(String personName)  {
        String sqlAddPerson = "INSERT INTO person(PersonName) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddPerson, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, personName);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            return rs.getInt(1);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }
    public static int addPhrase(String phrase, int idKeyWord)  {
        String sqlAddPhrase = "INSERT INTO engPhrase(engPhrase, idKeyWords) VALUES (?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddPhrase, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, phrase);
            pstmt.setString(2, String.valueOf(idKeyWord));
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }
    public static int addTranslation(String translation)  {
         String sqlAddRuTranslation = "INSERT INTO ruTranslation(ruTranslation) VALUES(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddRuTranslation, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, translation);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }
    public static void addEvent(String eventTitle, String eventDate, boolean isAccurate)  {
         String sqlAddEvent = "INSERT INTO event(eventTitle, eventDate, isAccurate) VALUES (?,?,?)";

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
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }
    public static int addEventGetId(String eventTitle, String eventDate, boolean isAccurate)  {
        String sqlAddEvent = "INSERT INTO event(eventTitle, eventDate, isAccurate) VALUES (?,?,?)";

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
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }
    public static void addPair(int idPhrase, int idTransl, int idSource, int idEvent, int idPerson, int idContext, int idType)  {
         String sqlAddPair = "INSERT INTO engRuTranslation(idEngPhrase, idRuTranslation, idSource, idEvent, idPerson, idContext, idType) VALUES( ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddPair)) {
            pstmt.setString(1, String.valueOf(idPhrase));
            pstmt.setString(2, String.valueOf(idTransl));
            pstmt.setString(3, String.valueOf(idSource));
            pstmt.setString(4, String.valueOf(idEvent));
            pstmt.setString(5, String.valueOf(idPerson));
            pstmt.setString(6, String.valueOf(idContext));
            pstmt.setString(7, String.valueOf(idType));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }
    public static int addKeyWordGetId(String keyWord)  {
        String sqlAddKeyWord = "INSERT INTO keyWord(keyWord) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddKeyWord, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, keyWord);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            return rs.getInt(1);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }
    public static void addKeyWord(String keyWord)  {
        String sqlAddKeyWord = "INSERT INTO keyWord(keyWord) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddKeyWord)) {
            pstmt.setString(1, keyWord);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }

    public static boolean checkPairPerson(String personName)  {
        int idPerson = getPersonId(personName);
         String sqlCheckPersonINPair = "SELECT idPerson FROM engRuTranslation WHERE (engRuTranslation.idPerson = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPersonINPair)) {
            pstmt.setString(1, String.valueOf(idPerson));
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return false;
    }
    public static boolean checkSource(String sourceTitle, String sourceURL, String sourceDesc)  {
        boolean sourceExists = false;
        String sqlCheckSource = "SELECT idSource, sourceTitle, sourceURL, sourceDescription FROM source WHERE ((sourceTitle = ?) AND (sourceURL = ?) AND (sourceDescription = ?))";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckSource)) {
            pstmt.setString(1, sourceTitle);
            pstmt.setString(2, sourceURL);
            pstmt.setString(3,sourceDesc);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                sourceExists = true;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return sourceExists;
    }
    public static boolean checkPhrase(String phrase)  {
        boolean phraseExists = false;
         String sqlCheckPhrase = "SELECT idEngPhrase FROM engPhrase WHERE (engPhrase = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPhrase)) {
            pstmt.setString(1, phrase);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                phraseExists = true;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return phraseExists;
    }
    public static boolean checkKeyWord(String word)  {
        boolean keyWordExists = false;
         String sqlCheckKeyWord = "SELECT idKeyWord FROM keyWord WHERE keyWord =?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckKeyWord)) {
            pstmt.setString(1, word);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                keyWordExists = true;
            return keyWordExists;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return keyWordExists;
    }
    public static boolean checkPerson(String person)  {
        boolean personExists = false;
        String sqlCheckPerson = "SELECT idPerson FROM person WHERE personName =?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPerson)) {
            pstmt.setString(1, person);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                personExists = true;
            return personExists;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return personExists;
    }
    public static boolean checkTranslation(String translation)  {
        boolean translationExists = false;
        String sqlCheckTranslation = "SELECT idRuTranslation FROM ruTranslation WHERE (ruTranslation = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckTranslation)) {
            pstmt.setString(1, translation);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                translationExists = true;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return translationExists;


    }
    public static boolean checkContext(String context)  {
        boolean contextExists = false;
        String sqlCheckContext = "SELECT idContext FROM context WHERE (contextText = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckContext)) {
            pstmt.setString(1, context);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                contextExists = true;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return contextExists;
    }

    public static boolean checkEvent(String eventTitle, String date)  {
        boolean eventExists = false;
        String sqlCheckEvent = "SELECT eventTitle FROM event WHERE (eventTitle = ? AND eventDate = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckEvent)) {
            pstmt.setString(1, eventTitle);
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                eventExists = true;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return eventExists;
    }

    public static Event getEvent(int idEvent) {
        String sqlEvent = "SELECT eventTitle, eventDate FROM event WHERE (idEvent = ?)";
        Event event = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlEvent)){
            pstmt.setInt(1, idEvent);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                event = new Event(idEvent, rs.getString("eventTitle"), rs.getString("eventDate"));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return event;
    }

    public static boolean checkEventInPair (int idEvent)  {
        boolean eventUsed = false;
        String sqlCheckEventInPair = "SELECT idEngPhrase FROM engRuTranslation WHERE (engRuTranslation.idEvent = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckEventInPair)) {
            pstmt.setString(1, String.valueOf(idEvent));
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                eventUsed = true;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return eventUsed;
    }
    public static boolean checkPairUsingPhrase (int idPhrase)  {
        boolean pairExists = false;
        String sqlCheckPairWithPhrase = "SELECT idEngPhrase FROM engRuTranslation WHERE (idEngPhrase = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPairWithPhrase)) {
            pstmt.setString(1, String.valueOf(idPhrase));
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                pairExists = true;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return pairExists;
    }
    public static boolean checkUsername(String username)  {
        boolean userExists = false;
        String sqlCheckUsername = "SELECT idUser FROM user WHERE (username = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckUsername)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                userExists = true;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return userExists;
    }

    public static void updatePair(int idPhrase,int idTransl, int idSource, int idEvent, int idPerson, int idContext, int idType, int idPair)  {
        String sqlUpdate = "UPDATE engRuTranslation SET idEngPhrase = ?, " +
                "idRuTranslation = ?, " +
                "idSource = ?, " +
                "idEvent = ?, " +
                "idPerson = ?, " +
                "idContext = ?," +
                "idType = ? WHERE idPair =?";
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
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }

    public static void addUser(String username, String password, String role)  {
        String sqlAddUser = "INSERT INTO user(username, password, role) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddUser)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }

    public static void deleteType(String selectedItem)  {
        String sqlDeleteType = "DELETE FROM type WHERE (typeTitle = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteType)) {
            pstmt.setString(1, selectedItem);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }

    public static boolean checkPairType(String selectedItem)  {
        boolean pairType = false;
        String sqlCheckTypePair = "SELECT idType FROM engRuTranslation WHERE (idType = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckTypePair)) {
            pstmt.setString(1, String.valueOf(getTypeId(selectedItem)));
            ResultSet rs = pstmt.executeQuery();
            pairType = rs.next();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return pairType;
    }

    public static int getTypeId(String selectedItem)  {
        String sqlGetTypeId = "SELECT idType FROM type WHERE (typeTitle = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetTypeId)) {
            pstmt.setString(1, selectedItem);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idType");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }

    public static void addType(String typeTitle)  {
        String sqlAddType = "INSERT INTO type(typeTitle) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddType)){
            pstmt.setString(1, typeTitle);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }

    public static int getPhraseIdFromPair(int idPairG)  {
        String getPhraseFromPair = "SELECT idEngPhrase FROM engRuTranslation WHERE idPair =?";

        try (PreparedStatement pstmt = conn.prepareStatement(getPhraseFromPair)) {
            pstmt.setInt(1, idPairG);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idEngPhrase");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }

    public static int getTranslationIdFromPair(int idPairG)  {
        String getTranslationFromPair = "SELECT idRuTranslation FROM engRuTranslation WHERE idPair =?";

        try (PreparedStatement pstmt = conn.prepareStatement(getTranslationFromPair)) {
            pstmt.setInt(1, idPairG);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("idRuTranslation");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }

    public static String getTypeTitle(int idType)  {
        String sqlGetTypeTitle = "SELECT typeTitle FROM type WHERE idType = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlGetTypeTitle)) {
            pstmt.setInt(1, idType);
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("typeTitle");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return null;
    }

    public static boolean checkType(String type)  {
        boolean typeExists = false;
        String sqlCheckType = "SELECT idType FROM type WHERE (typeTitle = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckType)){
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                typeExists = true;
            return typeExists;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return typeExists;
    }

    public static int addTypeGetId(String typeTitle)  {
        String sqlAddType = "INSERT INTO type(typeTitle) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlAddType, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, typeTitle);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            return rs.getInt(1);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return 0;
    }

    public static boolean checkPair(int idPhrase, int idTransl)  {
        boolean pairEx = false;
        String sqlCheckPair = "SELECT idPair FROM engRuTranslation WHERE ((idEngPhrase = ?) AND (idRuTranslation = ?))";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPair)) {
            pstmt.setInt(1, idPhrase);
            pstmt.setInt(2, idTransl);
            ResultSet rs  = pstmt.executeQuery();
            if(rs.next())
                pairEx = true;
            return pairEx;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return pairEx;
    }

    public static void deleteUser (String username)  {
        String sqlDeleteUser = "DELETE FROM user WHERE (userName = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteUser)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
    }

    public static boolean checkPairFull (int idPhrase, int idTransl, int idSource, int idEvent, int idPerson, int idContext, int idType)  {
        String sqlCheckPair = "SELECT idPair FROM engRuTranslation WHERE " +
                "( idEngPhrase = ? AND " +
                "idRuTranslation = ? AND " +
                "idSource = ? AND " +
                "idEvent = ? AND " +
                "idPerson = ? AND " +
                "idContext = ? AND " +
                "idType = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPair)) {
            pstmt.setInt(1, idPhrase);
            pstmt.setInt(2, idTransl);
            pstmt.setInt(3, idSource);
            pstmt.setInt(4, idEvent);
            pstmt.setInt(5, idPerson);
            pstmt.setInt(6, idContext);
            pstmt.setInt(7, idType);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
        }
        return false;
    }
}
