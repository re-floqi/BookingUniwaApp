package bookinguniwaapp.service;

import java.util.*;

public abstract class EventService<T> {
    protected final CsvService csvService;
    protected final String filename;
    protected final Map<String, T> eventMap = new HashMap<>();

    public EventService(CsvService csvService, String filename) {
        this.csvService = csvService;
        this.filename = filename;
    }

    public List<T> getAllEvents() {
        return new ArrayList<>(eventMap.values());
    }

    public T getEvent(String code) {
        return eventMap.get(code);
    }
}
