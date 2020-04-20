package Misc;

public class Event {
    String eventTitle;
    String eventDate;

    public Event(String title, String date)
    {
        eventDate = date;
        eventTitle = title;
    }

    public String getEventTitle() {return eventTitle;}
    public String getEventDate() {return eventDate;}
}
