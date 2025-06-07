package bookinguniwaapp.service;

import bookinguniwaapp.core.Theater;
import java.util.*;

public class TheaterService {
    private final CsvService csvService;
    private final String filename;
    private final Map<String, Theater> theaterMap = new HashMap<>();

    public TheaterService(CsvService csvService, String filename) {
        this.csvService = csvService;
        this.filename = filename;
    }

    public void loadData() {
        List<String[]> records = csvService.readCsv(filename);
        for (String[] record : records) {
            if (record.length >= 5) {
                Theater theater = new Theater(
                    record[0], record[1], record[2], record[3], record[4]
                );
                theaterMap.put(record[0], theater);
            }
        }
    }

    public void saveData() {
        List<String[]> data = new ArrayList<>();
        for (Theater theater : theaterMap.values()) {
            data.add(new String[]{
                theater.getCode(),
                theater.getTitle(),
                theater.getProtagonist(),
                theater.getLocation(),
                theater.getDate()
            });
        }
        csvService.writeCsv(filename, data);
    }

    public void addTheater(Theater theater) {
        if (!theaterMap.containsKey(theater.getCode())) {
            theaterMap.put(theater.getCode(), theater);
            System.out.println("Προστέθηκε επιτυχώς!");
        } else {
            System.out.println("Υπάρχει ήδη θεατρική παράσταση με αυτόν τον κωδικό!");
        }
    }

    public void updateTheater(String code, String title, String protagonist, String location, String date) {
        if (theaterMap.containsKey(code)) {
            Theater theater = theaterMap.get(code);
            theater.setTitle(title);
            theater.setProtagonist(protagonist);
            theater.setLocation(location);
            theater.setDate(date);
            System.out.println("Ενημερώθηκε επιτυχώς!");
        } else {
            System.out.println("Δεν βρέθηκε παράσταση με αυτόν τον κωδικό!");
        }
    }

    public void deleteTheater(String code) {
        if (theaterMap.containsKey(code)) {
            theaterMap.remove(code);
            System.out.println("Διαγράφηκε επιτυχώς!");
        } else {
            System.out.println("Δεν βρέθηκε παράσταση με αυτόν τον κωδικό!");
        }
    }

    public List<Theater> getAllTheaters() {
        return new ArrayList<>(theaterMap.values());
    }

    public Theater getTheater(String code) {
        return theaterMap.get(code);
    }
}