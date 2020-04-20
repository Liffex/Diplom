package Misc;

import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.TestModel;
import sample.Word;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

public class Reader {

    private XSSFWorkbook readWorkbook(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            return wb;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Vector<Word> readData(String path)
    {
        Vector<Word> dataVector = new Vector<Word>();
        XSSFWorkbook wb = readWorkbook(path);
        assert wb != null; //todo if null exception
        XSSFSheet sheet = wb.getSheetAt(0);
        for (Row row: sheet) {
                String keyWord = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String phrase = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String translation = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String eventTitle = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String eventDate = row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                boolean isAccurate = (row.getCell(5,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getBooleanCellValue());
                String person = row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String sourceTitle = row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String sourceURL = row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String sourceDesc = row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String context = row.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                System.out.println(phrase);
                dataVector.add(new Word(phrase, keyWord, translation, person, context, eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDesc));
            }

        return dataVector;

    }

    public void importData(String path) throws SQLException {
        Connection conn = TestModel.getConnection();
        Vector<Word> wordVector = readData(path);

        String sqlAddEvent = "INSERT INTO event(eventTitle, eventDate) VALUES(?, ?)";
        String sqlAddPerson = "INSERT INTO person(personName) VALUES(?)";
        String sqlAddContext = "INSERT INTO context(contextText) VALUES(?)";
        String sqlAddSource = "INSERT INTO source(sourceTitle, sourceURL, sourceDescription) VALUES(?, ?, ?)";
        String sqlAddKeyWord = "INSERT INTO keyWord(keyWord) VALUES(?)";
        String sqlAddEngPhrase = "INSERT INTO engPhrase(engPhrase, idKeyWords) VALUES(?, ?)";
        String sqlAddRuTranslation = "INSERT INTO ruTranslation(ruTranslation) VALUES(?)";
        String sqlAddEnRu = "INSERT INTO engRuTranslation(idEngPhrase, idRuTranslation, idSource, idEvent," +
                " idPerson, idContext) VALUES(?, ?, ?, ?, ?, ?)";


        //todo Переписать поиск существующих как человек (запросами)
        for (Word wd : wordVector) {
            int keyWordId = 0;
            int phraseId = 0;
            int personId = 0;
            int eventId = 0;
            int translationId = 0;
            int sourceId = 0;
            int contextId = 0;

            //addKeyWords
            String sqlGetKeyWordId = "SELECT idKeyWord FROM keyWord WHERE (keyWord.keyWord='" + wd.getKeyWord().toLowerCase() + "')";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlGetKeyWordId)) {
                if (rs.next()) {
                    keyWordId = rs.getInt("idKeyWord");
                } else {
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlAddKeyWord, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        pstmt.setString(1, wd.getKeyWord().toLowerCase());
                        pstmt.executeUpdate();
                        ResultSet rs1 = pstmt.getGeneratedKeys();
                        if (rs1.next()) {
                            keyWordId = rs1.getInt(1);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //addEngPhrase
            String sqlCheckPhrase = "SELECT idEngPhrase, engPhrase, idKeyWords FROM engPhrase WHERE ((engPhrase.engPhrase= ?) AND (engPhrase.idKeyWords='" + keyWordId + "'))";

            try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckPhrase)) {
                 pstmt.setString(1,wd.getPhrase());
                 ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    phraseId = rs.getInt("idEngPhrase");
            } else {
                    try (PreparedStatement pstmt1 = conn.prepareStatement(sqlAddEngPhrase, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        pstmt1.setString(1, wd.getPhrase());
                        pstmt1.setString(2, String.valueOf(keyWordId));
                        pstmt1.executeUpdate();
                        ResultSet rs1 = pstmt1.getGeneratedKeys();
                        if (rs1.next()) {
                            phraseId = rs1.getInt(1);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //todo delete NO PERSON IN SQL
            //addPersons
            if(wd.getPerson() == null || wd.getPerson().equals(""))
            {wd.setPerson("NO_PERSON");}
            String sqlGetPersonId = "SELECT idPerson FROM person WHERE (person.personName='" + wd.getPerson() + "')";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlGetPersonId)) {
                if (rs.next()) {
                    personId = rs.getInt("idPerson");
                } else {
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlAddPerson, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        //delete
                        if (wd.getPerson() == null || wd.getPerson().equals("")) {
                            pstmt.setString(1, "NO_PERSON");
                        } else {
                            pstmt.setString(1, wd.getPerson());
                        }
                        pstmt.executeUpdate();
                        ResultSet rs1 = pstmt.getGeneratedKeys();
                        if (rs1.next()) {
                            personId = rs1.getInt(1);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            //addEvent
            String sqlGetEventId = "SELECT idEvent FROM event WHERE (event.eventTitle='" + wd.getEventTitle() + "')";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlGetEventId)) {

                if (rs.next()) {
                    eventId = rs.getInt("idEvent");
                } else {
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlAddEvent, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        pstmt.setString(1, wd.getEventTitle());
                        if (wd.getEventDate() == null || wd.getEventDate().equals("")) {
                            pstmt.setString(2, "01.01.0001");
                        } else {
                            pstmt.setString(2, wd.getEventDate());
                        }
                        pstmt.executeUpdate();
                        ResultSet rs1 = pstmt.getGeneratedKeys();
                        if (rs1.next()) {
                            eventId = rs1.getInt(1);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            //todo delete NO_CONTEXT in SQL
            //addContext
            if(wd.getContext() == null || wd.getContext().equals(""))
            {wd.setContext("NO_CONTEXT");}
            String sqlCheckContext = "SELECT contextText, idContext FROM context WHERE (context.contextText ='" + wd.getContext() + "')";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlCheckContext)) {
                if (rs.next()) {
                    contextId = rs.getInt("idContext");
                } else {
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlAddContext, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        //delete
                        if (wd.getContext() == null || wd.getContext().equals("")) {
                            pstmt.setString(1, "NO_CONTEXT");
                        } else {
                            pstmt.setString(1, wd.getContext());
                        }
                        pstmt.executeUpdate();
                        ResultSet rs1 = pstmt.getGeneratedKeys();
                        if (rs1.next()) {
                            contextId = rs1.getInt(1);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //todo delete NO from SQL
            /* addSource */
            if(wd.getSourceTitle() == null || wd.getSourceTitle().equals(""))
            {wd.setSourceTitle("NO_SOURCE");}
            if(wd.getSourceDescription() == null || wd.getSourceDescription().equals(""))
            {wd.setSourceDescription("NO_DESC");}
            if(wd.getSourceURL() == null || wd.getSourceURL().equals(""))
            {wd.setSourceURL("NO_URL");}
            String sqlCheckSource = "SELECT idSource, sourceTitle, sourceURL, sourceDescription FROM source WHERE " +
                    "((source.sourceTitle ='" + wd.getSourceTitle() + "') AND (source.sourceURL='" + wd.getSourceURL() + "') AND (source.sourceDescription='" + wd.getSourceDescription() + "'))";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlCheckSource)) {
                if (rs.next()) {
                    sourceId = rs.getInt("idSource");
                } else {
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlAddSource, PreparedStatement.RETURN_GENERATED_KEYS)) {

                        //delete
                        if (wd.getSourceTitle() == null || wd.getSourceTitle().equals("")) {
                            pstmt.setString(1, "NO_SOURCE");
                        } else {
                            pstmt.setString(1, wd.getSourceTitle());
                        }

                        if (wd.getSourceURL() == null || wd.getSourceURL().equals("")) {
                            pstmt.setString(2, "NO_URL");
                        } else {
                            pstmt.setString(2, wd.getSourceURL());
                        }

                        if (wd.getSourceDescription() == null || wd.getSourceDescription().equals("")) {
                            pstmt.setString(3, "NO_DESC");
                        } else {
                            pstmt.setString(3, wd.getSourceDescription());
                        }

                        pstmt.executeUpdate();
                        ResultSet rs1 = pstmt.getGeneratedKeys();
                        if (rs1.next()) {
                            sourceId = rs1.getInt(1);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //todo delete NO from SQL
            /* addTranslation */
            if(wd.getTranslation() == null || wd.getTranslation().equals(""))
            {wd.setTranslation("NO_TRANSL");}
            String sqlCheckTransl = "SELECT idRuTranslation, ruTranslation FROM ruTranslation " +
                    "WHERE (ruTranslation='"+ wd.getTranslation() +"')";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlCheckTransl)) {
                if (rs.next()) {
                    translationId = rs.getInt("idRuTranslation");
                } else {

                    try (PreparedStatement pstmt = conn.prepareStatement(sqlAddRuTranslation, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        //delete
                        if (wd.getTranslation() == null || wd.getTranslation().equals("")) {
                            pstmt.setString(1, "NO_TRANSL");
                        } else {
                            pstmt.setString(1, wd.getTranslation());
                        }
                        pstmt.executeUpdate();
                        ResultSet rs1 = pstmt.getGeneratedKeys();
                        if (rs1.next()) {
                            translationId = rs1.getInt(1);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //addEnRu
            String sqlCheckEnRU = "SELECT * FROM engRuTranslation WHERE" +
                    "((engRuTranslation.idEngPhrase='"+ phraseId +"') AND " +
                    "(engRuTranslation.idRuTranslation='"+ translationId +"') AND " +
                    "(engRuTranslation.idSource='"+ sourceId +"') AND " +
                    "(engRuTranslation.idEvent='"+ eventId +"') AND " +
                    "(engRuTranslation.idPerson='"+ personId +"') AND " +
                    "(engRuTranslation.idContext='"+ contextId +"'))";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlCheckEnRU)) {
                if (rs.next()) {}
                else {
                    try (PreparedStatement pstmt = conn.prepareStatement(sqlAddEnRu)) {
                        pstmt.setString(1, String.valueOf(phraseId));
                        pstmt.setString(2, String.valueOf(translationId));
                        pstmt.setString(3, String.valueOf(sourceId));
                        pstmt.setString(4, String.valueOf(eventId));
                        pstmt.setString(5, String.valueOf(personId));
                        pstmt.setString(6, String.valueOf(contextId));
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
