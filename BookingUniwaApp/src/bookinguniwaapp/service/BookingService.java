 package bookinguniwaapp.service;

import bookinguniwaapp.core.BaseEntity;
import bookinguniwaapp.core.Booking;
import bookinguniwaapp.exception.SingletonInitializationException;

import java.util.*;

public class BookingService {
    private final CsvService csvService;
    private final List<Booking> bookings = new ArrayList<>();

    public BookingService() throws SingletonInitializationException {
        this.csvService = CsvService.getInstance(Booking.class);
    }

    /**
     * Φορτώνει τα δεδομένα απο το αρχείο csv
     */
    public void loadData() {
        List<String[]> records = csvService.readCsv();
        for (String[] record : records) {
            if (record.length >= 3) {
            try {
                bookings.add(new Booking(record[0], record[1], record[2], record[3]));
            } catch (Exception e) {
                System.out.println("Σφάλμα κατά την ανάγνωση της κράτησης: " + e.getMessage());
            }
            }
        }
    }

    /**
     * Αποθηκεύει τα δεδομένα σε αρχείο csv
     */
    public void saveData() {
        List<String[]> data = new ArrayList<>();
        for (Booking booking : bookings) {
            data.add(new String[]{
                booking.getCode(),
                booking.getClientCode(),
                booking.getEventCode(),
                booking.getEventType()
            });
        }
        try {
        csvService.writeCsv(data); }
        catch (Exception e) {
            System.out.println("Σφάλμα κατά την αποθήκευση των δεδομένων: " + e.getMessage());
        }
    }

    /**
     * Προσθέτει μία νέα κράτηση
     * @param booking Η νέα κράτηση
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * Επιστρέφει στατιστικά στοιχεία για τις κρατήσεις θεατρικών παραστάσεων
     * @return Ένα Map κειμένου και αριθμού, που αναπαριστά τις κρατήσεις ανά θεατρική παράσταση
     */
    public Map<String, Integer> getTheaterStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        for (Booking booking : bookings) {
            if ("THEATER".equals(booking.getEventType())) {
                stats.merge(booking.getEventCode(), 1, Integer::sum);
            }
        }
        return stats;
    }

    /**
     * Επιστρέφει στατιστικά στοιχεία για τις κρατήσεις μουσικών παραστάσεων
     * @return Ένα Map κειμένου και αριθμού, που αναπαριστά τις κρατήσεις ανά μουσική παράσταση
     */
    public Map<String, Integer> getMusicStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        for (Booking booking : bookings) {
            if ("MUSIC".equals(booking.getEventType())) {
                stats.merge(booking.getEventCode(), 1, Integer::sum);
            }
        }
        return stats;
    }
}
