package bookinguniwaapp.service;

import java.io.*;
import java.util.*;

public class CsvService {

    /**
     * Αυτή η μεταβλητή θα αποθηκεύει το μονοπάτι του φακέλου δεδομένων
     * που θα της δώσει η App.java κατά τη δημιουργία της.
     */
    private final String dataDirectory;

    /**
     * Ο constructor πλέον δέχεται μια παράμετρο τύπου String.
     * Αυτό διορθώνει το αρχικό σφάλμα "constructor not found".
     *
     * @param dataDir Το μονοπάτι προς τον φάκελο δεδομένων (π.χ., "../../data/").
     */
    public CsvService(String dataDir) {
        this.dataDirectory = dataDir;
        File dir = new File(this.dataDirectory);
        if (!dir.exists()) {
            // Χρησιμοποιούμε mkdirs() για να είμαστε σίγουροι ότι θα δημιουργηθούν
            // και οι γονικοί φάκελοι αν χρειάζεται (π.χ. για το ../../data).
            dir.mkdirs();
        }
        System.out.println("CsvService is initialized. Data directory set to: " + dir.getAbsolutePath());
    }

    /**
     * Διαβάζει ένα αρχείο CSV.
     * @param fileName Το πλήρες μονοπάτι προς το αρχείο (π.χ., "../../data/theater.csv").
     * @return Μια λίστα με τα δεδομένα του αρχείου.
     */
    public List<String[]> readCsv(String fileName) {
        List<String[]> data = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("Warning: Cannot read file because it was not found: " + file.getPath());
            return data;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) { // Αποφεύγουμε την προσθήκη κενών γραμμών
                    data.add(line.split(","));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Γράφει δεδομένα σε ένα αρχείο CSV.
     * @param fileName Το πλήρες μονοπάτι προς το αρχείο (π.χ., "../../data/bookings.csv").
     * @param data Τα δεδομένα προς εγγραφή.
     */
    public void writeCsv(String fileName, List<String[]> data) {
        File file = new File(fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}