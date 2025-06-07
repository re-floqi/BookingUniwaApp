package bookinguniwaapp.core;

public class Client {
    private String code;
    private String name;

    public Client(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Getters & Setters
    public String getCode() { return code; }
    public String getName() { return name; }
    
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return String.format("%s,%s", code, name);
    }
}