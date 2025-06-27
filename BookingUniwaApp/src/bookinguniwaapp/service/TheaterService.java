package bookinguniwaapp.service;

import bookinguniwaapp.core.Theater;
import bookinguniwaapp.exception.SingletonInitializationException;

import java.util.*;

public class TheaterService extends CrudService<Theater> {
    private final CsvService csvService;

    public TheaterService() throws SingletonInitializationException {
        this.csvService = CsvService.getInstance(Theater.class);
    }

    public void loadData() {
        List<String[]> records = csvService.readCsv();
        try {
            for (String[] record : records) {
                if (record.length >= 5) {
                    Theater theater = new Theater(
                        record[0], record[1], record[2], record[3], record[4]
                    );
                    entityMap.put(record[0], theater);
                }
            }
        } catch (Exception e) {
            System.out.println("Σφάλμα κατά την ανάγνωση των θεατρικών παραστάσεων: " + e.getMessage());
        }
    }

    public void saveData() {
        List<String[]> data = new ArrayList<>();
        for (Theater theater : entityMap.values()) {
            data.add(new String[]{
                theater.getCode(),
                theater.getTitle(),
                theater.getProtagonist(),
                theater.getLocation(),
                theater.getDate()
            });
        }
        csvService.writeCsv(data);
    }
}
