package bookinguniwaapp.service;

import java.io.*;
import java.util.*;

public class CsvService {
    public List<String[]> readCsv(String filename) {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String[] record : data) {
                bw.write(String.join(",", record));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Σφάλμα εγγραφής αρχείου: " + e.getMessage());
        }
    }
}