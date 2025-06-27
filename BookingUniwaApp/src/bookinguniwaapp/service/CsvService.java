package bookinguniwaapp.service;

import bookinguniwaapp.core.BaseEntity;
import bookinguniwaapp.exception.SingletonInitializationException;

import java.io.*;
import java.net.URL;
import java.util.*;

public class CsvService {

    private static volatile CsvService INSTANCE;
    private static final String DATA_FOLDER = "resources/data";
    private final URL resourceUrl;
    private static volatile Class<? extends BaseEntity> selectedClazz;
    private volatile String fileName;

    private CsvService(Class<? extends BaseEntity> clazz) throws SingletonInitializationException {
        try {
            this.resourceUrl = getClass().getClassLoader().getResource(DATA_FOLDER);
            this.fileName = clazz.getSimpleName();

            if (resourceUrl != null) {
                File dir = new File(resourceUrl.getFile());
                if (!dir.exists()) {
                    // Χρησιμοποιούμε mkdirs() για να είμαστε σίγουροι ότι θα δημιουργηθούν
                    // και οι γονικοί φάκελοι αν χρειάζεται (π.χ. για το ../../data).
                    dir.mkdirs();
                }
                System.out.println("CsvService is initialized. Data directory set to: " + dir.getAbsolutePath());
            }
        } catch (Exception e) {
            throw new SingletonInitializationException("The " + this.getClass().getName() + " singleton could not be initialized. Nested exception is: " + e.getMessage());
        }

    }

    public static CsvService getInstance(Class<? extends BaseEntity> clazz) throws SingletonInitializationException {
        if (INSTANCE == null || selectedClazz == null || !selectedClazz.equals(clazz)) {
            selectedClazz = clazz;
            INSTANCE = new CsvService(selectedClazz);
        }

        return INSTANCE;
    }



    /**
     * Διαβάζει ένα αρχείο CSV.
     * @return Μια λίστα με τα δεδομένα του αρχείου.
     */
    public synchronized List<String[]> readCsv() {
        List<String[]> data = new ArrayList<>();
        File file = new File(resourceUrl.getPath() + "/" + this.fileName + ".csv");
        if (!file.exists()) {
            System.err.println("Warning: Cannot read file because it was not found: " + resourceUrl.getPath() + "/" + this.fileName + ".csv");
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
     * @param data Τα δεδομένα προς εγγραφή.
     */
    public synchronized void writeCsv(List<String[]> data) {
        File file = new File(resourceUrl.getPath() + "/" + this.fileName + ".csv");
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
