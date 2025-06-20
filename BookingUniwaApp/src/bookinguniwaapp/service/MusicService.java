package bookinguniwaapp.service;

import bookinguniwaapp.core.Music;
import java.util.*;

public class MusicService extends EventService<Music> {
    public MusicService(CsvService csvService, String filename) {
        super(csvService, filename);
    }

    public void loadData() {
        List<String[]> records = csvService.readCsv(filename);
        for (String[] record : records) {
            if (record.length >= 5) {
                Music music = new Music(
                    record[0], record[1], record[2], record[3], record[4]
                );
                eventMap.put(record[0], music);
            }
        }
    }

    public void saveData() {
        List<String[]> data = new ArrayList<>();
        for (Music music : eventMap.values()) {
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
        if (!eventMap.containsKey(music.getCode())) {
            try {
            eventMap.put(music.getCode(), music);
            System.out.println("Προστέθηκε επιτυχώς!"); 
        }
            catch (Exception e) {
                System.out.println("Σφάλμα κατά την προσθήκη της μουσικής παράστασης: " + e.getMessage());
            }
        }
    }

    public void updateMusic(String code, String title, String singer, String location, String date) {
        try {
        if (eventMap.containsKey(code)) {
            Music music = eventMap.get(code);
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
        if (eventMap.containsKey(code)) {
            eventMap.remove(code);
            System.out.println("Διαγράφηκε επιτυχώς!");
        } 
        } catch (Exception e) {
            System.out.println("Σφάλμα κατά τη διαγραφή της μουσικής παράστασης: " + e.getMessage());
        }
    }

    public List<Music> getAllMusic() {
        return new ArrayList<>(eventMap.values());
    }

    public Music getMusic(String code) {
        return eventMap.get(code);
    }
}