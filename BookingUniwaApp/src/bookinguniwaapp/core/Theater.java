package bookinguniwaapp.core;

public class Theater {
    private String code;
    private String title;
    private String protagonist;
    private String location;
    private String date;

    public Theater(String code, String title, String protagonist, String location, String date) {
        this.code = code;
        this.title = title;
        this.protagonist = protagonist;
        this.location = location;
        this.date = date;
    }

    // Getters & Setters
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public String getProtagonist() { return protagonist; }
    public String getLocation() { return location; }
    public String getDate() { return date; }
    
    public void setTitle(String title) { this.title = title; }
    public void setProtagonist(String protagonist) { this.protagonist = protagonist; }
    public void setLocation(String location) { this.location = location; }
    public void setDate(String date) { this.date = date; }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s", code, title, protagonist, location, date);
    }
}