package bookinguniwaapp.service;

import java.io.*;
import java.util.*;

public class CsvService {
    private static final String DATA_DIR = "data";

    public CsvService() {
        // Create data directory if it doesn't exist
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
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
        } catch (IOException e) {
            System.out.println("Σφάλμα εγγραφής αρχείου: " + e.getMessage());
        }
    }
}