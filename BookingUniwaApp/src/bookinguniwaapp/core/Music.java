package bookinguniwaapp.core;

public class Music extends Event {
    private String singer;

    public Music(String code, String title, String singer, String location, String date) {
        super(code, title, location, date);
        this.singer = singer;
    }

    public String getSinger() { return singer; }
    public void setSinger(String singer) { this.singer = singer; }
}