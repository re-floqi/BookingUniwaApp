package bookinguniwaapp.core;

import java.util.Random;

public class Theater extends Event {
    private String protagonist;

    public Theater(String code, String title, String protagonist, String location, String date) {
        super(code, title, location, date);
        this.protagonist = protagonist;
    }

    public String getProtagonist() { return protagonist; }
    public void setProtagonist(String protagonist) { this.protagonist = protagonist; }
}
