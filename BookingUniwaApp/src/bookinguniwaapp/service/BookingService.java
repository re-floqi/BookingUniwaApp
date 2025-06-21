 package bookinguniwaapp.service;

import bookinguniwaapp.core.Booking;
import java.util.*;

public class BookingService {
    private final CsvService csvService;
    private final String filename;
    private final List<Booking> bookings = new ArrayList<>();

    public BookingService(CsvService csvService, String filename) {
        this.csvService = csvService;
        this.filename = filename;
    }

    public void loadData() {
        List<String[]> records = csvService.readCsv(filename);
        for (String[] record : records) {
            if (record.length >= 3) {
                try {
                bookings.add(new Booking(record[0], record[1], record[2]));
            } catch (Exception e) {
                System.out.println("Σφάλμα κατά την ανάγνωση της κράτησης: " + e.getMessage());
            }
            }
        }
    }

    public void saveData() {
        List<String[]> data = new ArrayList<>();
        for (Booking booking : bookings) {
            data.add(new String[]{
                booking.getClientCode(),
                booking.getEventCode(),
                booking.getEventType()
            });
        }
        try {
        csvService.writeCsv(filename, data); }
        catch (Exception e) {
            System.out.println("Σφάλμα κατά την αποθήκευση των δεδομένων: " + e.getMessage());
        }
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public Map<String, Integer> getTheaterStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        for (Booking booking : bookings) {
            if ("THEATER".equals(booking.getEventType())) {
                stats.merge(booking.getEventCode(), 1, Integer::sum);
            }
        }
        return stats;
    }

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