package bookinguniwaapp.service;

import java.io.*;
import java.util.*;

public class CsvService {
    private final String dataDir;

    public CsvService(String dataDir) {
        this.dataDir = dataDir;
        // Δημιουργία φακέλου δεδομένων αν δεν υπάρχει
        File dir = new File(dataDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        System.out.println("Data directory: " + dataDir);
    }

    public List<String[]> readCsv(String filename) {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dataDir + File.separator + filename))) {
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataDir + File.separator + filename))) {
            for (String[] record : data) {
                bw.write(String.join(",", record));
                bw.newLine();
            }
            // System.out.println("Τα δεδομένα αποθηκεύτηκαν επιτυχώς στο αρχείο: " + filename);
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