package bookinguniwaapp.service;

import bookinguniwaapp.core.Client;
import java.util.*;

public class ClientService extends CrudService<Client> {
    private final CsvService csvService;
    private final String filename;

    public ClientService(CsvService csvService, String filename) {
        this.csvService = csvService;
        this.filename = filename;
    }

    public void loadData() {
        List<String[]> records = csvService.readCsv(filename);
        for (String[] record : records) {
            if (record.length >= 2) {
                Client client = new Client(record[0], record[1]);
                entityMap.put(record[0], client);
            }
        }
    }

    public void saveData() {
        List<String[]> data = new ArrayList<>();
        for (Client client : entityMap.values()) {
            data.add(new String[]{client.getCode(), client.getName()});
        }
        try {
            csvService.writeCsv(filename, data);
            System.out.println("Τα δεδομένα αποθηκεύτηκαν επιτυχώς!");
        } catch (Exception e) {
            System.out.println("Σφάλμα κατά την αποθήκευση των δεδομένων: " + e.getMessage());
        }
    }
}