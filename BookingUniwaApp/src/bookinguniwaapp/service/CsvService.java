package bookinguniwaapp.service;

import bookinguniwaapp.core.BaseEntity;
import bookinguniwaapp.exception.SingletonInitializationException;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
                    // Ensure the directory exists or create it (for dev mode)
                    dir.mkdirs();
                }
                System.out.println("CsvService is initialized. Data directory set to: " + dir.getAbsolutePath());
            }
        } catch (Exception e) {
            throw new SingletonInitializationException("The " + this.getClass().getName() + " singleton could not be initialized. Nested exception is: " + e.getMessage());
        }
    }

    private static boolean checkIfRunningInsideJar() {
        String className = CsvService.class.getName().replace('.', '/') + ".class";
        String classPath = CsvService.class.getClassLoader().getResource(className).toString();

        return classPath != null && classPath.startsWith("jar:file:");
    }

    public static CsvService getInstance(Class<? extends BaseEntity> clazz) throws SingletonInitializationException {
        if (INSTANCE == null || selectedClazz == null || !selectedClazz.equals(clazz)) {
            selectedClazz = clazz;
            INSTANCE = new CsvService(selectedClazz);
        }

        return INSTANCE;
    }

    /**
     * Reads a CSV file.
     * @return A list with the file's data.
     */
    public synchronized List<String[]> readCsv() {
        List<String[]> data = new ArrayList<>();
        File file;

        if (checkIfRunningInsideJar()) {
            // Inside a JAR, use getResourceAsStream
            String path = DATA_FOLDER + "/" + this.fileName + ".csv";
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
                if (inputStream == null) {
                    System.err.println("Resource not found inside JAR: " + path);
                    return data;
                }
                try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.trim().isEmpty()) { // Avoid empty lines
                            data.add(line.split(","));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // In development mode, check if file exists outside the JAR
            file = new File(resourceUrl.getFile(), this.fileName + ".csv");
            if (!file.exists()) {
                System.err.println("Warning: Cannot read file because it was not found: " + file.getAbsolutePath());
                return data;
            }
            try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty()) { // Avoid empty lines
                        data.add(line.split(","));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * Writes data to a CSV file.
     * @param data The data to write.
     */
    public synchronized void writeCsv(List<String[]> data) {
        File file;
        if (checkIfRunningInsideJar()) {
            // Inside a JAR, this method can't write directly, so we need to write to an external folder
            String externalDirPath = new File("dist/resources/data").getAbsolutePath();
            file = new File(externalDirPath, this.fileName + ".csv");  // Write to an external directory outside JAR
        } else {
            // In development mode, write directly to the filesystem
            file = new File(resourceUrl.getFile(), this.fileName + ".csv");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
