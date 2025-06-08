/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bookinguniwaapp;

import bookinguniwaapp.core.*;
import bookinguniwaapp.service.*;
import java.io.IOException;
import java.util.*;

public class App {
    // Ονόματα αρχείων CSV
    private static final String THEATER_FILE = "theater.csv";
    private static final String MUSIC_FILE = "music.csv";
    private static final String CLIENT_FILE = "client.csv";
    private static final String BOOKING_FILE = "bookings.csv";
    
    // Υπηρεσίες εφαρμογής
    private static TheaterService theaterService;
    private static MusicService musicService;
    private static ClientService clientService;
    private static BookingService bookingService;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in); // ΠΡΩΤΑ το scanner
        initializeServices();
        loadData();
        mainMenu();
        saveData();
        scanner.close();
        System.out.println("Η εφαρμογή τερματίστηκε επιτυχώς!");
        
    }

    // Αρχικοποίηση των υπηρεσιών
    private static void initializeServices() {
        CsvService csvService = new CsvService();
        theaterService = new TheaterService(csvService, THEATER_FILE);
        musicService = new MusicService(csvService, MUSIC_FILE);
        clientService = new ClientService(csvService, CLIENT_FILE);
        bookingService = new BookingService(csvService, BOOKING_FILE);
    }

    // Φόρτωμα δεδομένων από τα CSV αρχεία
    private static void loadData() {
        theaterService.loadData();
        musicService.loadData();
        clientService.loadData();
        bookingService.loadData();
        System.out.println("Τα αρχικά δεδομένα φορτώθηκαν επιτυχώς!");
    }   

    // Αποθήκευση δεδομένων στα CSV αρχεία
    private static void saveData() {
        theaterService.saveData();
        musicService.saveData();
        clientService.saveData();
        bookingService.saveData();
        System.out.println("Όλα τα δεδομένα αποθηκεύτηκαν!");        
    }


    private static void pause() { // Μέθοδος για παύση της εκτέλεσης και αναμονή για είσοδο από τον χρήστη
        System.out.print("Πατήστε Enter για να συνεχίσετε...");
        scanner.nextLine();
    }

    public static void sleep(long millis) {
    try {
        Thread.sleep(millis);
    } catch (InterruptedException ignored) {}
}

    // Κύριο μενού εφαρμογής
    private static void mainMenu() {
        int choice;
        do {
            clearConsole();
            System.out.println("============ [Κύριο Μενού] ===========");
            System.out.println("1. Διαχείριση Θεατρικών Παραστάσεων");
            System.out.println("2. Διαχείριση Μουσικών Παραστάσεων");
            System.out.println("3. Διαχείριση Πελατών");
            System.out.println("4. Διαχείριση Κρατήσεων & Στατιστικά");
            System.out.println("5. Έξοδος");
            System.out.print("Επιλέξτε λειτουργία: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();  // Καθαρισμός buffer
            
            switch (choice) {
                case 1 : manageTheater(); break;
                case 2 : manageMusic(); break;
                case 3 : manageClients(); break;
                case 4 : manageBookings(); break;
                case 5 : System.out.println("Αποσύνδεση από το σύστημα..."); break;
                default : System.out.println("Μη έγκυρη επιλογή! Παρακαλώ επιλέξτε 1-5."); break;
            }
        } while (choice != 5);
    }

    // Διαχείριση θεατρικών παραστάσεων
    private static void manageTheater() {
        int choice;
        do {
            clearConsole();
            System.out.println("===[Διαχείριση Θεατρικών Παραστάσεων]===");
            System.out.println("1. Προσθήκη Νέας Παράστασης");
            System.out.println("2. Ενημέρωση Παράστασης");
            System.out.println("3. Διαγραφή Παράστασης");
            System.out.println("4. Προβολή Όλων των Παραστάσεων");
            System.out.println("5. Επιστροφή στο Κύριο Μενού");
            System.out.print("Επιλέξτε λειτουργία: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 : addTheater(); break;
                case 2 : updateTheater(); break;
                case 3 : deleteTheater(); break;
                case 4 : showAllTheaters(); break;
                case 5 : System.out.println("Επιστροφή στο κύριο μενού..."); break;
                default: System.out.println("Μη έγκυρη επιλογή! Παρακαλώ επιλέξτε 1-5."); break;
            }
        } while (choice != 5);
    }

    // Προσθήκη νέας θεατρικής παράστασης
    private static void addTheater() {
        clearConsole();
        System.out.println("===== [Προσθήκη Νέας Θεατρικής Παράστασης] ======");
        System.out.print("Κωδικός Παράστασης: ");
        String code = scanner.nextLine();
        do {
            if (code.equalsIgnoreCase("EXIT")) {
            System.out.println("Έξοδος από την προσθήκη.");
            return;
            }

            if (!searchByCodeTheater(code)) {
            break;  // Βγήκε από το loop αν βρεθεί το θέατρο
            }
                else {
                System.out.println("Βρέθηκε θεατρική παράσταση με αυτόν τον κωδικό. Παρακαλώ προσπαθήστε ξανά ή γράψτε EXIT για έξοδο.");
                System.out.println("Εισάγετε νεο κωδικό παραστάσης: ");
                code = scanner.nextLine();
            }
        } while (true);  // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος        
        System.out.print("Τίτλος: ");
        String title = scanner.nextLine();
        System.out.print("Πρωταγωνιστής: ");
        String protagonist = scanner.nextLine();
        System.out.print("Χώρος: ");
        String location = scanner.nextLine();
        System.out.print("Ημερομηνία (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        
        theaterService.addTheater(new Theater(code, title, protagonist, location, date));
        System.out.println("Η θεατρική παράσταση προστέθηκε επιτυχώς!"); // αργότερα θα προσθέσουμε και έλεγχο για διπλότυπα και έλεγχο για επιτυχή αποθήκευση
        pause(); // Παύση για να δει ο χρήστης το μήνυμα
    }

    // Ενημέρωση υπάρχουσας θεατρικής παράστασης
    private static void updateTheater() {
        clearConsole();
        System.out.println("== [Ενημέρωση Θεατρικής Παράστασης] ==");
        System.out.print("Εισάγετε κωδικό παραστάσης: ");
        String code = scanner.nextLine();
    do {
    if (code.equalsIgnoreCase("EXIT")) {
        System.out.println("Έξοδος από την ενημέρωση.");
        return;
        }

        if (searchByCodeTheater(code)) {
        break;  // Βγήκε από το loop αν βρεθεί το θέατρο
        }
        else {
        System.out.println("Δεν βρέθηκε θεατρική παράσταση με αυτόν τον κωδικό. Παρακαλώ προσπαθήστε ξανά ή γράψτε EXIT για έξοδο.");
        System.out.println("Εισάγετε κωδικό παραστάσης: ");
        code = scanner.nextLine();
        }
        } while (true);  // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος
        System.out.print("Νέος τίτλος: ");
        String title = scanner.nextLine();
        System.out.print("Νέος πρωταγωνιστής: ");
        String protagonist = scanner.nextLine();
        System.out.print("Νέος χώρος: ");
        String location = scanner.nextLine();
        System.out.print("Νέα ημερομηνία (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        
        theaterService.updateTheater(code, title, protagonist, location, date);
        System.out.println("Η θεατρική παράσταση ενημερώθηκε επιτυχώς!");
        pause(); // Παύση για να δει ο χρήστης το μήνυμα
    }

    public static boolean searchByCodeTheater(String code) { // Μέθοδος για αναζήτησης βάσει code θεατρού
    for (Theater tmp : theaterService.getAllTheaters()) {
        if (tmp.getCode().equals(code)) {
            return true;  // Επιστρέφει true αν βρει το θεατρικό με το δοσμένο code
        }
    }
    return false;  // Αν δεν βρει το θεατρικό, επιστρέφει false
    }



    // Διαγραφή θεατρικής παράστασης
    private static void deleteTheater() {
        clearConsole();
        System.out.println("=== [Διαγραφή Θεατρικής Παράστασης] ===");
        System.out.print("Εισάγετε κωδικό παραστάσης: ");
        String code = scanner.nextLine();
        do {
            if (code.equalsIgnoreCase("EXIT")) {
            System.out.println("Έξοδος από την διαγραφή.");
            return;
            }

            if (searchByCodeTheater(code)) {
            break;  // Βγήκε από το loop αν βρεθεί το θέατρο
            }
                else {
                System.out.println("Δεν βρέθηκε θεατρική παράσταση με αυτόν τον κωδικό. Παρακαλώ προσπαθήστε ξανά ή γράψτε EXIT για έξοδο.");
                System.out.println("Εισάγετε κωδικό παραστάσης: ");
                code = scanner.nextLine();
            }
        } while (true);  // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος
        theaterService.deleteTheater(code);
        System.out.println("Η θεατρική παράσταση διαγράφηκε επιτυχώς!");
        pause(); // Παύση για να δει ο χρήστης το μήνυμα
    }

    // Εμφάνιση όλων των θεατρικών παραστάσεων
    private static void showAllTheaters() {
        clearConsole();
        System.out.println("== [Κατάλογος Θεατρικών Παραστάσεων] ==");
        List<Theater> theaters = theaterService.getAllTheaters();
        
        if (theaters.isEmpty()) {
            System.out.println("Δεν βρέθηκαν διαθέσιμες παραστάσεις.");
        } else {
            for (Theater theater : theaters) {
                System.out.println("----------------------------------");
                System.out.println("Κωδικός: " + theater.getCode());
                System.out.println("Τίτλος: " + theater.getTitle());
                System.out.println("Πρωταγωνιστής: " + theater.getProtagonist());
                System.out.println("Χώρος: " + theater.getLocation());
                System.out.println("Ημερομηνία: " + theater.getDate());
            }
            System.out.println("----------------------------------");
            System.out.println("Σύνολο: " + theaters.size() + " παραστάσεις");
            pause(); // Παύση για να δει ο χρήστης το κατάλογο
        }
    }
    public static boolean searchByCodeMusic(String code) { // Μέθοδος για αναζήτησης βάσει code μουσικής παραστάσης
    for (Music tmp : musicService.getAllMusic()) {
        if (tmp.getCode().equals(code)) {
            return true;  // Επιστρέφει true αν βρει το μουσικη με το δοσμένο code
        }
    }
    return false;  // Αν δεν βρει το μουσικη, επιστρέφει false
    }

    // Διαχείριση μουσικών παραστάσεων (παρόμοια με θεατρικές)
    private static void manageMusic() {
        int choice;
        do {
            clearConsole();
            System.out.println("== [Διαχείριση Μουσικών Παραστάσεων] ==");
            System.out.println("1. Προσθήκη Νέας Παράστασης");
            System.out.println("2. Ενημέρωση Παράστασης");
            System.out.println("3. Διαγραφή Παράστασης");
            System.out.println("4. Προβολή Όλων των Παραστάσεων");
            System.out.println("5. Επιστροφή στο Κύριο Μενού");
            System.out.print("Επιλέξτε λειτουργία: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: addMusic(); break;
                case 2: updateMusic(); break;
                case 3: deleteMusic(); break;
                case 4: showAllMusic(); break;
                case 5: System.out.println("Επιστροφή στο κύριο μενού..."); break;
                default: System.out.println("Μη έγκυρη επιλογή! Παρακαλώ επιλέξτε 1-5."); break;
            }
        } while (choice != 5);
    }

    private static void addMusic() {
        System.out.println("= [Προσθήκη Νέας Μουσικής Παράστασης] =");
        System.out.print("Κωδικός Παράστασης: ");
        String code = scanner.nextLine();
        do {
            if (code.equalsIgnoreCase("EXIT")) {
            System.out.println("Έξοδος από την προσθήκη.");
            return;
            }

            if (!searchByCodeMusic(code)) {
            break;  // Βγήκε από το loop αν βρεθεί το μουσική
            }
                else {
                System.out.println("Βρέθηκε μουσική παράσταση με αυτόν τον κωδικό. Παρακαλώ προσπαθήστε ξανά ή γράψτε EXIT για έξοδο.");
                System.out.println("Εισάγετε νεο κωδικό παραστάσης: ");
                code = scanner.nextLine();
            }
        } while (true);  // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος        
        System.out.print("Τίτλος: ");
        String title = scanner.nextLine();
        System.out.print("Τραγουδιστής/Συγκρότημα: ");
        String singer = scanner.nextLine();
        System.out.print("Χώρος: ");
        String location = scanner.nextLine();
        System.out.print("Ημερομηνία (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        
        musicService.addMusic(new Music(code, title, singer, location, date));
        System.out.println("Η μουσική παράσταση προστέθηκε επιτυχώς!");
        pause(); // Παύση για να δει ο χρήστης το μήνυμα
    }

    private static void updateMusic() {
        clearConsole();
        System.out.println("=== [Ενημέρωση Μουσικής Παράστασης] ===");
        System.out.print("Εισάγετε κωδικό παραστάσης: ");
        String code = scanner.nextLine();
        do {
            if (code.equalsIgnoreCase("EXIT")) {
            System.out.println("Έξοδος από την ενημέρωση.");
            return;
            }
            if (searchByCodeMusic(code)) {
                break;  // Βγήκε από το loop αν βρεθεί το μουσική
            }
            else {
            System.out.println("Δεν βρέθηκε μουσική παράσταση με αυτόν τον κωδικό. Παρακαλώ προσπαθήστε ξανά ή γράψτε EXIT για έξοδο.");
            System.out.println("Εισάγετε κωδικό παραστάσης: ");
            code = scanner.nextLine();
            }
        } while (true);  // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος
        System.out.print("Νέος τίτλος: ");
        String title = scanner.nextLine();
        System.out.print("Νέος τραγουδιστής/συγκρότημα: ");
        String singer = scanner.nextLine();
        System.out.print("Νέος χώρος: ");
        String location = scanner.nextLine();
        System.out.print("Νέα ημερομηνία (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        
        musicService.updateMusic(code, title, singer, location, date);
        System.out.println("Η μουσική παράσταση ενημερώθηκε επιτυχώς!");
        pause(); // Παύση για να δει ο χρήστης το μήνυμα
    }

    private static void deleteMusic() {
        clearConsole();
        System.out.println("=== [Διαγραφή Μουσικής Παράστασης] ===");
        System.out.print("Εισάγετε κωδικό παραστάσης: ");
        String code = scanner.nextLine();
        do {
            if (code.equalsIgnoreCase("EXIT")) {
            System.out.println("Έξοδος από την διαγραφή.");
            return;
                }
                if (searchByCodeTheater(code)) {
                break;  // Βγήκε από το loop αν βρεθεί το θέατρο
                }
                else {
                System.out.println("Δεν βρέθηκε μουσική παράσταση με αυτόν τον κωδικό. Παρακαλώ προσπαθήστε ξανά ή γράψτε EXIT για έξοδο.");
                System.out.println("Εισάγετε κωδικό παραστάσης: ");
                code = scanner.nextLine();
            }
        } while (true);  // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος
        musicService.deleteMusic(code);
        System.out.println("Η μουσική παράσταση διαγράφηκε επιτυχώς!");
        pause(); // Παύση για να δει ο χρήστης το μήνυμα
    }

    private static void showAllMusic() {
        clearConsole();
        System.out.println("== [Κατάλογος Μουσικών Παραστάσεων] ==");
        List<Music> musicShows = musicService.getAllMusic();
        
        if (musicShows.isEmpty()) {
            System.out.println("Δεν βρέθηκαν διαθέσιμες παραστάσεις.");
        } else {
            for (Music music : musicShows) {
                System.out.println("----------------------------------");
                System.out.println("Κωδικός: " + music.getCode());
                System.out.println("Τίτλος: " + music.getTitle());
                System.out.println("Τραγουδιστής: " + music.getSinger());
                System.out.println("Χώρος: " + music.getLocation());
                System.out.println("Ημερομηνία: " + music.getDate());
            }
            System.out.println("----------------------------------");
            System.out.println("Σύνολο: " + musicShows.size() + " παραστάσεις");
            pause(); // Παύση για να δει ο χρήστης το κατάλογο
        }
    }

    // Διαχείριση πελατών
    private static void manageClients() {
        int choice;
        do {
            clearConsole();
            System.out.println("======== [Διαχείριση Πελατών] ========");
            System.out.println("1. Εγγραφή Νέου Πελάτη");
            System.out.println("2. Ενημέρωση Στοιχείων Πελάτη");
            System.out.println("3. Διαγραφή Πελάτη");
            System.out.println("4. Προβολή Όλων των Πελατών");
            System.out.println("5. Επιστροφή στο Κύριο Μενού");
            System.out.print("Επιλέξτε λειτουργία: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Καθαρισμός buffer
            
            switch (choice) {
                case 1: addClient(); break;
                case 2: updateClient(); break;
                case 3: deleteClient(); break;
                case 4: showAllClients(); break;
                case 5: System.out.println("Επιστροφή στο κύριο μενού..."); break;
                default: System.out.println("Μη έγκυρη επιλογή! Παρακαλώ επιλέξτε 1-5."); break;
            }
        } while (choice != 5);
    }

    public static boolean searchByCodeClient(String code) { // Μέθοδος για αναζήτησης βάσει code μουσικής παραστάσης
    for (Client tmp : clientService.getAllClients()) {
        if (tmp.getCode().equals(code)) {
            return true;  // Επιστρέφει true αν βρει πελατη
        }
    }
    return false;  // Αν δεν βρει το πελατη, επιστρέφει false
    }

    private static void addClient() {
        clearConsole();
        System.out.println("====== [Εγγραφή Νέου Πελάτη] ======");
        System.out.print("Κωδικός Πελάτη: ");
        String code = scanner.nextLine();
        do {
            if (code.equalsIgnoreCase("EXIT")) {
            System.out.println("Έξοδος από την εγγραφή.");
            return;
            }

            if (!searchByCodeClient(code)) {
            break;  // Βγήκε από το loop αν δεν βρεθεί πελάτης
            }
                else {
                System.out.println("Υπάρχει ήδη πελάτης με αυτόν τον κωδικό. Παρακαλώ προσπαθήστε ξανά ή γράψτε EXIT για έξοδο.");
                System.out.println("Εισάγετε νεο κωδικό πελάτη: ");
                code = scanner.nextLine();
            }
        } while (true);  // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος
        System.out.print("Όνοματεπώνυμο: ");
        String name = scanner.nextLine();

        clientService.addClient(new Client(code, name));
        System.out.println("Ο πελάτης προστέθηκε επιτυχώς!");
        pause(); // Παύση για να δει ο χρήστης το μήνυμα
    }

    private static void updateClient() {
        clearConsole();
        System.out.println("==== [Ενημέρωση Στοιχείων Πελάτη] ====");
        System.out.print("Εισάγετε κωδικό πελάτη: ");
        String code = scanner.nextLine();
        do {
            if (code.equalsIgnoreCase("EXIT")) {
            System.out.println("Έξοδος από την ενημέρωση.");
            return;
            }

            if (searchByCodeClient(code)) {
            break;  // Βγήκε από το loop αν βρεθεί ο πελάτης
            }
                else {
                System.out.println("Υπάρχει ήδη πελάτης με αυτόν τον κωδικό. Παρακαλώ προσπαθήστε ξανά ή γράψτε EXIT για έξοδο.");
                System.out.println("Εισάγετε νεο κωδικό πελάτη: ");
                code = scanner.nextLine();
            }
        } while (true);  // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος
        System.out.print("Νέο όνοματεπώνυμο: ");
        String name = scanner.nextLine();
        clientService.updateClient(code, name);
        System.out.println("Τα στοιχεία του πελάτη ενημερώθηκαν επιτυχώς!");
        pause(); // Παύση για να δει ο χρήστης το μήνυμα
    }

    private static void deleteClient() {
        clearConsole();
        System.out.println("========== [Διαγραφή Πελάτη] ==========");
        System.out.print("Εισάγετε κωδικό πελάτη: ");
        String code = scanner.nextLine();
        do {
            if (code.equalsIgnoreCase("EXIT")) {
            System.out.println("Έξοδος από την διαγραφή.");
            return;
            }

            if (searchByCodeClient(code)) {
            break;  // Βγήκε από το loop αν βρεθεί ο πελάτης
            }
                else {
                System.out.println("Δεν υπάρχει ήδη πελάτης με αυτόν τον κωδικό. Παρακαλώ προσπαθήστε ξανά ή γράψτε EXIT για έξοδο.");
                System.out.println("Εισάγετε νεο κωδικό πελάτη: ");
                code = scanner.nextLine();
            }
        } while (true);  // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος
        clientService.deleteClient(code);
        System.out.println("Ο πελάτης διαγράφηκε επιτυχώς!");
        pause(); // Παύση για να δει ο χρήστης το μήνυμα
    }

    private static void showAllClients() {
        clearConsole();
        System.out.println("========= [Κατάλογος Πελατών] =========");
        List<Client> clients = clientService.getAllClients();
        
        if (clients.isEmpty()) {
            System.out.println("Δεν βρέθηκαν εγγεγραμμένοι πελάτες.");
            pause(); // Παύση για να δει ο χρήστης το μήνυμα
        } else {
            for (Client client : clients) {
                System.out.println("----------------------------------");
                System.out.println("Κωδικός: " + client.getCode());
                System.out.println("Ονοματεπώνυμο: " + client.getName());
            }
            System.out.println("----------------------------------");
            System.out.println("Σύνολο: " + clients.size() + " πελάτες");
            pause(); // Παύση για να δει ο χρήστης το κατάλογο
        }
    }

    // Διαχείριση κρατήσεων και στατιστικών
    private static void manageBookings() {
        int choice;
        do {
            clearConsole();
            clearConsole();
            System.out.println("== [Διαχείριση Κρατήσεων & Στατιστικά] ==");
            System.out.println("1. Κράτηση Θεατρικής Παράστασης");
            System.out.println("2. Κράτηση Μουσικής Παράστασης");
            System.out.println("3. Στατιστικά Θεατρικών Παραστάσεων");
            System.out.println("4. Στατιστικά Μουσικών Παραστάσεων");
            System.out.println("5. Επιστροφή στο Κύριο Μενού");
            System.out.print("Επιλέξτε λειτουργία: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Καθαρισμός buffer
            
            switch (choice) {
                case 1: bookTheater(); break;
                case 2: bookMusic(); break;
                case 3: showTheaterStats(); break;
                case 4: showMusicStats(); break;
                case 5: System.out.println("Επιστροφή στο κύριο μενού..."); break;
                default: System.out.println("Μη έγκυρη επιλογή! Παρακαλώ επιλέξτε 1-5."); break;
            }
        } while (choice != 5);
    }

    // Κράτηση θεατρικής παράστασης
    private static void bookTheater() { 
        clearConsole();
        System.out.println("=== [Κράτηση Θεατρικής Παράστασης] ===");
        System.out.print("Κωδικός Πελάτη: ");
        String clientCode = scanner.nextLine();

       do { // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος
            if (clientCode.equalsIgnoreCase("EXIT")) {
            System.out.println("Έξοδος από την κράτηση.");
            return;
            }
            if (clientCode.equalsIgnoreCase("NEW")) { // κλήση για προσθήκη νέου πελάτη
            addClient() ;
            return;
            }

            if (searchByCodeClient(clientCode)) {
            break;  // Βγήκε από το loop αν βρεθεί ο πελάτης
            }
                else {
                System.out.println("Δεν υπάρχει  πελάτης με αυτόν τον κωδικό. Παρακαλώ προσπαθήστε ξανά ή γράψτε EXIT για έξοδο. ή NEW για προσθήκη νέου πελάτη.");
                System.out.println("Εισάγετε νεο κωδικό πελάτη: ");
                clientCode = scanner.nextLine();
            }
        } while (true);  // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος
        System.out.print("Κωδικός Παράστασης: ");
        String eventCode = scanner.nextLine();
        do {
            if (eventCode.equalsIgnoreCase("EXIT")) {
            System.out.println("Έξοδος από την κράτηση.");
            return;
            }

            if (searchByCodeTheater(eventCode)) {
            break;  // Βγήκε από το loop αν βρεθεί το θέατρο
            }
            else {
                System.out.println("Δεν βρέθηκε θεατρική παράσταση με αυτόν τον κωδικό. Παρακαλώ προσπαθήστε ξανά ή γράψτε EXIT για έξοδο.");
                System.out.println("Εισάγετε κωδικό παραστάσης: ");
                eventCode = scanner.nextLine();
        }
        } while (true);  // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος
        
        bookingService.addBooking(new Booking(clientCode, eventCode, "THEATER"));
        System.out.println("Εγινε κρατηση για την παράσταση: " + theaterService.getTheater(eventCode).getTitle() + " για τον πελάτη: " + clientService.getClient(clientCode).getName());
        pause(); // Παύση για να δει ο χρήστης το μήνυμα
    }

    // Κράτηση μουσικής παράστασης
    private static void bookMusic() { // TODO: να γίνει έλεγχος αμεσα για τον υπάρχει ο πελάτης και η παράσταση
        clearConsole();
        System.out.println("=== [Κράτηση Θεατρικής Παράστασης] ===");
        System.out.print("Κωδικός Πελάτη: ");
        String clientCode = scanner.nextLine();

       do { // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος
            if (clientCode.equalsIgnoreCase("EXIT")) {
            System.out.println("Έξοδος από την κράτηση.");
            return;
            }
            if (clientCode.equalsIgnoreCase("NEW")) { // κλήση για προσθήκη νέου πελάτη
            addClient() ;
            return;
            }

            if (searchByCodeClient(clientCode)) {
            break;  // Βγήκε από το loop αν βρεθεί ο πελάτης
            }
                else {
                System.out.println("Δεν υπάρχει  πελάτης με αυτόν τον κωδικό. Παρακαλώ προσπαθήστε ξανά ή γράψτε EXIT για έξοδο. ή NEW για προσθήκη νέου πελάτη.");
                System.out.println("Εισάγετε νεο κωδικό πελάτη: ");
                clientCode = scanner.nextLine();
            }
        } while (true);  // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος
        
        System.out.print("Κωδικός Παράστασης: ");
        String eventCode = scanner.nextLine();

        do {
            if (eventCode.equalsIgnoreCase("EXIT")) {
                System.out.println("Έξοδος από την κράτηση.");
                return;
                }

            if (searchByCodeMusic(eventCode)) {
                break;  // Βγήκε από το loop αν βρεθεί το θέατρο
            }
            else {
                System.out.println("Δεν βρέθηκε θεατρική παράσταση με αυτόν τον κωδικό. Παρακαλώ προσπαθήστε ξανά ή γράψτε EXIT για έξοδο.");
                System.out.println("Εισάγετε κωδικό παραστάσης: ");
                eventCode = scanner.nextLine();
        }
        } while (true);  // Επανάληψη μέχρι να βρει τον κωδικό ή να γίνει έξοδος
        bookingService.addBooking(new Booking(clientCode, eventCode, "MUSIC"));
        System.out.println("Η κράτηση ολοκληρώθηκε επιτυχώς!");
        System.out.println("Εγινε κρατηση για την παράσταση: " + musicService.getMusic(eventCode).getTitle() + " για τον πελάτη: " + clientService.getClient(clientCode).getName());
        pause(); // Παύση για να δει ο χρήστης το μήνυμα
    }

    // Στατιστικά θεατρικών παραστάσεων
    private static void showTheaterStats() {
        clearConsole();
        System.out.println("== [Στατιστικά Θεατρικών Παραστάσεων] ==");
        Map<String, Integer> stats = bookingService.getTheaterStatistics();
        
        if (stats.isEmpty()) {
            System.out.println("Δεν υπάρχουν κρατήσεις για θεατρικές παραστάσεις.");
        } else {
            System.out.println("Κρατήσεις ανά Παράσταση:");
            for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                Theater theater = theaterService.getTheater(entry.getKey());
                if (theater != null) {
                    System.out.printf("- %s: %s: %d κρατήσεις%n", 
                        theater.getCode(), theater.getTitle(), entry.getValue());
                }
            }
            System.out.println("Σύνολο κρατήσεων: " + stats.values().stream().mapToInt(Integer::intValue).sum());
        }
        pause(); // Παύση για να δει ο χρήστης τα στατιστικά
    }

    // Στατιστικά μουσικών παραστάσεων
    private static void showMusicStats() {
        System.out.println("\n--- Στατιστικά Μουσικών Παραστάσεων ---");
        Map<String, Integer> stats = bookingService.getMusicStatistics();
        
        if (stats.isEmpty()) {
            System.out.println("Δεν υπάρχουν κρατήσεις για μουσικές παραστάσεις.");
        } else {
            System.out.println("Κρατήσεις ανά Παράσταση:");
            for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                Music music = musicService.getMusic(entry.getKey());
                if (music != null) {
                    System.out.printf("- %s: %s: %d κρατήσεις%n", 
                        music.getCode(), music.getTitle(), entry.getValue());
                }
            }
            System.out.println("Σύνολο κρατήσεων: " + stats.values().stream().mapToInt(Integer::intValue).sum());
        }
        pause(); // Παύση για να δει ο χρήστης τα στατιστικά
    }
public static void clearConsole() { // Μέθοδος για καθαρισμό κονσόλας συνδυασμός ANSI escape codes και ProcessBuilder
    try {
        // Αν δεν υπάρχει κονσόλα (π.χ. IDE), απλώς τυπώνουμε κενές γραμμές
        if (System.console() == null) {
            for (int i = 0; i < 50; i++) System.out.println();
            return;
        }

        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder pb = os.contains("win")
                ? new ProcessBuilder("cmd", "/c", "cls")
                : new ProcessBuilder("clear");   // σε Linux/macOS/Unix

        pb.inheritIO().start().waitFor();
    } catch (IOException | InterruptedException ex) {
        // Επαναφέρουμε το interrupt flag αν διακοπεί το νήμα
        if (ex instanceof InterruptedException) Thread.currentThread().interrupt();
        // Fallback: ANSI escape – δουλεύει στους περισσότερους emulators
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }  
}

}