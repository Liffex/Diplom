package misc;

import javafx.collections.ObservableList;
import misc.data.Word;
import misc.sql.SQLCommands;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FileHandler {



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

    private ArrayList<Word> readData(String path){
        ArrayList<Word> dataVector = new ArrayList<>();
        XSSFWorkbook wb = readWorkbook(path);
        boolean isAccurate = false;
        assert wb != null; //todo if null exception
        XSSFSheet sheet = wb.getSheetAt(0);
        for (Row row: sheet) {
                String keyWord = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String phrase = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String translation = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String eventTitle = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String eventDate = row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                if (row.getCell(5) != null && row.getCell(5).getCellType() == CellType.NUMERIC) {
                    if ( row.getCell(5).getNumericCellValue() == 0) {
                        isAccurate = false;
                    } else {
                        isAccurate = true;
                    }
                } else {
                    isAccurate = (row.getCell(5,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getBooleanCellValue());
                }
                String person = row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String sourceTitle = row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String sourceURL = row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String sourceDesc = row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String context = row.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                String typeTitle = row.getCell(11, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
                System.out.println(phrase);
                dataVector.add(new Word(0, phrase, keyWord, translation, person, context, eventTitle, eventDate, isAccurate, sourceTitle, sourceURL, sourceDesc, typeTitle));
            }
        return dataVector;

    }

    public void importData(String path) throws SQLException {
        ArrayList<Word> wordVector = readData(path);

        for (Word wd : wordVector) {
            int keyWordId = 0;
            int phraseId = 0;
            int personId = 0;
            int eventId = 0;
            int translationId = 0;
            int sourceId = 0;
            int contextId = 0;
            int typeId = 0;

            //addKeyWords
            if (!SQLCommands.checkKeyWord(wd.getKeyWord()))
                keyWordId = SQLCommands.addKeyWordGetId(wd.getKeyWord());
            else
                keyWordId = SQLCommands.getKeyWordId(wd.getKeyWord());


            //addEngPhrase
            if (!SQLCommands.checkPhrase(wd.getPhrase()))
                phraseId = SQLCommands.addPhrase(wd.getPhrase(), keyWordId);
            else
                phraseId = SQLCommands.getPhraseId(wd.getPhrase());

            //addPersons
            if(wd.getPerson() == null || wd.getPerson().equals(""))
                wd.setPerson(" ");

            if(!SQLCommands.checkPerson(wd.getPerson()))
                personId = SQLCommands.addPersonGetId(wd.getPerson());
            else
                personId = SQLCommands.getPersonId(wd.getPerson());


            //addEvent
            if (wd.getEventDate() == null || wd.getEventDate().equals(""))
                wd.setEventDate(" ");

            if(!SQLCommands.checkEvent(wd.getEventTitle()))
                eventId = SQLCommands.addEventGetId(wd.getEventTitle(), wd.getEventDate(), wd.getIsAccurate());
            else
                eventId = SQLCommands.getEventId(wd.getEventTitle());

            //addContext
            if(wd.getContext() == null || wd.getContext().equals(""))
                wd.setContext(" ");

            if(!SQLCommands.checkContext(wd.getContext()))
                contextId = SQLCommands.addContextText(wd.getContext());
            else
                contextId = SQLCommands.getContextId(wd.getContext());

            /* addSource */
            if(wd.getSourceTitle() == null || wd.getSourceTitle().equals(""))
                wd.setSourceTitle(" ");
            if(wd.getSourceDescription() == null || wd.getSourceDescription().equals(""))
                wd.setSourceDescription(" ");
            if(wd.getSourceURL() == null || wd.getSourceURL().equals(""))
                wd.setSourceURL(" ");

            if(!SQLCommands.checkSource(wd.getSourceTitle(),wd.getSourceURL(),wd.getSourceDescription()))
                sourceId = SQLCommands.addSource(wd.getSourceTitle(), wd.getSourceURL(), wd.getSourceDescription());
            else
                sourceId = SQLCommands.getSourceIdFullCompare(wd.getSourceTitle(), wd.getSourceURL(), wd.getSourceDescription());


            /* addTranslation */
            if(wd.getTranslation() == null || wd.getTranslation().equals(""))
                wd.setTranslation(" ");

            if(!SQLCommands.checkTranslation(wd.getTranslation()))
                translationId = SQLCommands.addTranslation(wd.getTranslation());
            else
                translationId = SQLCommands.getTranslationId(wd.getTranslation());

            //addtype
            if(wd.getTypeTitle() == null || wd.getTypeTitle().equals(""))
                wd.setTypeTitle(" ");

            if(!SQLCommands.checkType(wd.getTypeTitle()))
                typeId = SQLCommands.addTypeGetId(wd.getTypeTitle());
            else
                typeId = SQLCommands.getTypeId(wd.getTypeTitle());

            //addEnRu
            if(!SQLCommands.checkPair(phraseId, translationId))
                SQLCommands.addPair(phraseId,
                        translationId,
                        sourceId,
                        eventId,
                        personId,
                        contextId,
                        typeId);

        }
    }

    public void exportData(ObservableList<Word> listData, String path) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Лист 1");
        Row row;
        Cell cell;
        int rownum = 0;

        if(listData.isEmpty())
        {
            System.out.println("Пусто");
        }
        else {
            for (Word wd : listData) {
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
                //type
                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue(wd.getTypeTitle());

                rownum++;
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd(HH_mm)");
            LocalDateTime now = LocalDateTime.now();
            String stringCurrentTime = dtf.format(now);
            File file = new File(path + "\\Export"+ stringCurrentTime + ".xlsx");

            if (file.createNewFile()) {
                System.out.println("created");
            } else {
                System.out.println("Already exists");
            }
            FileOutputStream outFile = new FileOutputStream(file);
            wb.write(outFile);
            System.out.println("writed");
            outFile.close();
            }
    }
}
