package bookinguniwaapp.service;

import bookinguniwaapp.core.Music;
import java.util.*;

public class MusicService {
    private final CsvService csvService;
    private final String filename;
    private final Map<String, Music> musicMap = new HashMap<>();

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
                musicMap.put(record[0], music);
            }
        }
    }

    public void saveData() {
        List<String[]> data = new ArrayList<>();
        for (Music music : musicMap.values()) {
            data.add(new String[]{
                music.getCode(),
                music.getTitle(),
                music.getSinger(),
                music.getLocation(),
                music.getDate()
            });
        }
        try {
            csvService.writeCsv(filename, data); // Αποθήκευση δεδομένων μουσικής
            System.out.println("Η Αποθήκευση δεδομένων μουσικής επιτυχής!");
        } catch (Exception e) {
            System.out.println("Σφάλμα κατά την αποθήκευση των δεδομένων: " + e.getMessage());
        }
    }

    public void addMusic(Music music) {
        if (!musicMap.containsKey(music.getCode())) {
            try {
            musicMap.put(music.getCode(), music);
            System.out.println("Προστέθηκε επιτυχώς!"); 
        }
            catch (Exception e) {
                System.out.println("Σφάλμα κατά την προσθήκη της μουσικής παράστασης: " + e.getMessage());
            }
        }
    }

    public void updateMusic(String code, String title, String singer, String location, String date) {
        try {
        if (musicMap.containsKey(code)) {
            Music music = musicMap.get(code);
            music.setTitle(title);
            music.setSinger(singer);
            music.setLocation(location);
            music.setDate(date);
            System.out.println("Ενημερώθηκε επιτυχώς!"); }
        } catch (Exception e) {
            System.out.println("Σφάλμα κατά την ενημέρωση της μουσικής παράστασης: " + e.getMessage());
        }
    }

    public void deleteMusic(String code) {
        try {
        if (musicMap.containsKey(code)) {
            musicMap.remove(code);
            System.out.println("Διαγράφηκε επιτυχώς!");
        } 
        } catch (Exception e) {
            System.out.println("Σφάλμα κατά τη διαγραφή της μουσικής παράστασης: " + e.getMessage());
        }
    }

    public List<Music> getAllMusic() {
        return new ArrayList<>(musicMap.values());
    }

    public Music getMusic(String code) {
        return musicMap.get(code);
    }
}