package misc.data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Word {

    private SimpleIntegerProperty idPair;
    private SimpleStringProperty typeTitle;
    private SimpleStringProperty phrase;
    private SimpleStringProperty keyWord;
    private SimpleStringProperty translation;
    private SimpleStringProperty personName;
    private SimpleStringProperty context;
    private SimpleStringProperty eventTitle;
    private SimpleStringProperty eventDate;
    private boolean isAccurate;
    private SimpleStringProperty sourceTitle;
    private SimpleStringProperty sourceURL;
    private SimpleStringProperty sourceDescription;

    public Word(int id, String phr, String key, String transl, String person, String cont,
                String evTitle, String evDate, boolean isAcc, String sourTitle, String sourUrl, String sourDesc, String type)
    {
        idPair = new SimpleIntegerProperty(id);
        typeTitle = new SimpleStringProperty(type);
        phrase = new SimpleStringProperty(phr);
        keyWord = new SimpleStringProperty(key);
        translation = new SimpleStringProperty(transl);
        personName = new SimpleStringProperty(person);
        context = new SimpleStringProperty(cont);
        eventTitle = new SimpleStringProperty(evTitle);
        eventDate = new SimpleStringProperty(evDate);
        isAccurate = isAcc;
        sourceTitle = new SimpleStringProperty(sourTitle);
        sourceURL = new SimpleStringProperty(sourUrl);
        sourceDescription = new SimpleStringProperty(sourDesc);

    }

    public String getPhrase(){ return phrase.get(); }
    public String getKeyWord(){ return keyWord.get(); }
    public String getTranslation(){ return translation.get(); }
    public void setTranslation(String translation) {this.translation = new SimpleStringProperty(translation);}
    public String getPerson() { return personName.get();}
    public void setPerson(String person) { this.personName = new SimpleStringProperty(person); }
    public String getContext() { return context.get(); }
    public void setContext(String context) {this.context = new SimpleStringProperty(context); }
    public String getEventTitle() {return eventTitle.get(); }
    public String getEventDate() { return eventDate.get(); }
    public boolean getIsAccurate() {return isAccurate; }
    public String getSourceTitle() {return sourceTitle.get(); }
    public void setSourceTitle(String title) {this.sourceTitle = new SimpleStringProperty(title); }
    public String getSourceURL() {return sourceURL.get(); }
    public void setEventDate(String date) {this.eventDate = new SimpleStringProperty(date); }
    public void setSourceURL(String url) {this.sourceURL = new SimpleStringProperty(url); }
    public void setTypeTitle(String type) {this.typeTitle = new SimpleStringProperty(type);}
    public String getSourceDescription() {return sourceDescription.get(); }
    public void setSourceDescription(String description) {this.sourceDescription = new SimpleStringProperty(description); }
    public int getIdPair() {
        return idPair.get();
    }
    public String getTypeTitle() { return typeTitle.get(); }
}
