package bookinguniwaapp.service;

import java.io.*;
import java.util.*;

public class CsvService {
    private static final String DATA_DIR = "data";

    public CsvService() {
        // Δημιουργία φακέλου δεδομένων "data" αν δεν υπάρχει
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        System.out.println("Working directory: " + System.getProperty("user.dir"));
    }

    public List<String[]> readCsv(String filename) {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_DIR + File.separator + filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα ανάγνωσης αρχείου: " + e.getMessage());
        }
        return records;
    }

    public void writeCsv(String filename, List<String[]> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_DIR + File.separator + filename))) {
            for (String[] record : data) {
                bw.write(String.join(",", record));
                bw.newLine();
            }
            System.out.println("Τα δεδομένα αποθηκεύτηκαν επιτυχώς στο αρχείο: " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("Το αρχείο δεν βρέθηκε: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("Δεν έχετε δικαιώματα εγγραφής στο αρχείο: " + e.getMessage());
        } catch (IOException e) {
            if (e.getMessage().contains("No space left on device")) {
                System.out.println("Δεν υπάρχει αρκετός χώρος στο δίσκο για την εγγραφή του αρχείου.");
            } else {
                System.out.println("Σφάλμα εγγραφής αρχείου: " + e.getMessage());
            }
        }
    }
}