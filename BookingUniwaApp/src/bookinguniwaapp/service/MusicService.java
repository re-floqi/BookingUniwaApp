package bookinguniwaapp.service;

import bookinguniwaapp.core.Music;
import bookinguniwaapp.exception.SingletonInitializationException;

import java.util.*;

public class MusicService extends CrudService<Music> {
    private final CsvService csvService;

    public MusicService() throws SingletonInitializationException {
        this.csvService = CsvService.getInstance(Music.class);
    }

    /**
     * Φορτώνει τα δεδομένα απο το αρχείο csv
     */
    public void loadData() {
        List<String[]> records = csvService.readCsv();
        for (String[] record : records) {
            if (record.length >= 5) {
                Music music = new Music(
                    record[0], record[1], record[2], record[3], record[4]
                );
                entityMap.put(record[0], music);
            }
        }
    }

    /**
     * Αποθηκεύει τα δεδομένα σε αρχείο csv
     */
    public void saveData() {
        List<String[]> data = new ArrayList<>();
        for (Music music : entityMap.values()) {
            data.add(new String[]{
                music.getCode(),
                music.getTitle(),
                music.getSinger(),
                music.getLocation(),
                music.getDate()
            });
        }
        try {
            csvService.writeCsv(data);
        } catch (Exception e) {
            System.out.println("Σφάλμα κατά την αποθήκευση των δεδομένων: " + e.getMessage());
        }
    }
}
