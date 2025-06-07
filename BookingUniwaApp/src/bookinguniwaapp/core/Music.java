package bookinguniwaapp.core;

public class Music {
    private String code;
    private String title;
    private String singer;
    private String location;
    private String date;

    public Music(String code, String title, String singer, String location, String date) {
        this.code = code;
        this.title = title;
        this.singer = singer;
        this.location = location;
        this.date = date;
    }

    // Getters & Setters
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public String getSinger() { return singer; }
    public String getLocation() { return location; }
    public String getDate() { return date; }
    
    public void setTitle(String title) { this.title = title; }
    public void setSinger(String singer) { this.singer = singer; }
    public void setLocation(String location) { this.location = location; }
    public void setDate(String date) { this.date = date; }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s", code, title, singer, location, date);
    }
}