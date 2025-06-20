package bookinguniwaapp.service;

import bookinguniwaapp.core.Music;
import java.util.*;

public class MusicService extends CrudService<Music> {
    private final CsvService csvService;
    private final String filename;

    public MusicService(CsvService csvService, String filename) {
        this.csvService = csvService;
        this.filename = filename;
    }

    public void loadData() {
        List<String[]> records = csvService.readCsv(filename);
        for (String[] record : records) {
            if (record.length >= 5) {
                Music music = new Music(
                    record[0], record[1], record[2], record[3], record[4]
                );
                entityMap.put(record[0], music);
            }
        }
    }

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
            csvService.writeCsv(filename, data);
            System.out.println("Η Αποθήκευση δεδομένων μουσικής επιτυχής!");
        } catch (Exception e) {
            System.out.println("Σφάλμα κατά την αποθήκευση των δεδομένων: " + e.getMessage());
        }
    }
}