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

    public List<String[]> readCsv(String fileName) {
        List<String[]> data = new ArrayList<>();
        File file = new File(DATA_DIR, fileName);
        if (!file.exists()) return data;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void writeCsv(String fileName, List<String[]> data) {
        File file = new File(DATA_DIR, fileName);
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