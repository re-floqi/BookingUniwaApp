package bookinguniwaapp.service;

import bookinguniwaapp.core.Client;
import java.util.*;

public class ClientService {
    private final CsvService csvService;
    private final String filename;
    private final Map<String, Client> clientMap = new HashMap<>();

    public ClientService(CsvService csvService, String filename) {
        this.csvService = csvService;
        this.filename = filename;
    }

    public void loadData() {
        List<String[]> records = csvService.readCsv(filename);
        for (String[] record : records) {
            if (record.length >= 2) {
                Client client = new Client(record[0], record[1]);
                clientMap.put(record[0], client);
            }
        }
    }

    public void saveData() {
        List<String[]> data = new ArrayList<>();
        for (Client client : clientMap.values()) {
            data.add(new String[]{client.getCode(), client.getName()});
        }
        csvService.writeCsv(filename, data);
    }

    public void addClient(Client client) {
        if (!clientMap.containsKey(client.getCode())) {
            clientMap.put(client.getCode(), client);
            System.out.println("Προστέθηκε επιτυχώς!");
        } else {
            System.out.println("Υπάρχει ήδη πελάτης με αυτόν τον κωδικό!");
        }
    }

    public void updateClient(String code, String name) {
        if (clientMap.containsKey(code)) {
            Client client = clientMap.get(code);
            client.setName(name);
            System.out.println("Ενημερώθηκε επιτυχώς!");
        } else {
            System.out.println("Δεν βρέθηκε πελάτης με αυτόν τον κωδικό!");
        }
    }

    public void deleteClient(String code) {
        if (clientMap.containsKey(code)) {
            clientMap.remove(code);
            System.out.println("Διαγράφηκε επιτυχώς!");
        } else {
            System.out.println("Δεν βρέθηκε πελάτης με αυτόν τον κωδικό!");
        }
    }

    public List<Client> getAllClients() {
        return new ArrayList<>(clientMap.values());
    }

    public Client getClient(String code) {
        return clientMap.get(code);
    }
}