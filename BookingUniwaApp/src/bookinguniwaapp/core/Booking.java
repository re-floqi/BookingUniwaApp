package bookinguniwaapp.core;

public class Booking extends BaseEntity {
    private String clientCode;
    private String eventCode;
    private String eventType; // "THEATER" or "MUSIC"

    public Booking(String code, String clientCode, String eventCode, String eventType) {
        super();
        super.setCode(code);
        this.clientCode = clientCode;
        this.eventCode = eventCode;
        this.eventType = eventType;
    }

    // Getters
    public String getClientCode() { return clientCode; }
    public String getEventCode() { return eventCode; }
    public String getEventType() { return eventType; }

    @Override
    public String toString() {
        return String.format("%s,%s,%s", clientCode, eventCode, eventType);
    }
}
