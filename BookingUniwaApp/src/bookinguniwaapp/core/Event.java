package bookinguniwaapp.core;

public class Event {
    private String code;
    private String title;
    private String location;
    private String date;

    public Event(String code, String title, String location, String date) {
        this.code = code;
        this.title = title;
        this.location = location;
        this.date = date;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}