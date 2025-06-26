package bookinguniwaapp.service;

import bookinguniwaapp.core.Client;
import bookinguniwaapp.exception.SingletonInitializationException;

import java.util.*;

public class ClientService extends CrudService<Client> {
    private final CsvService csvService;

    public ClientService() throws SingletonInitializationException {
        this.csvService = CsvService.getInstance(Client.class);
    }

    public void loadData() {
        List<String[]> records = csvService.readCsv();
        for (String[] record : records) {
            if (record.length >= 2) {
                Client client = new Client(Long.valueOf(record[0]), record[1], record[2]);
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
            csvService.writeCsv(data);
        } catch (Exception e) {
            System.out.println("Σφάλμα κατά την αποθήκευση των δεδομένων: " + e.getMessage());
        }
    }
}
