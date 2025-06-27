package bookinguniwaapp.service;

import bookinguniwaapp.core.BaseEntity;

import java.util.*;

/**
 * Γενική abstract κλάση για CRUD λειτουργίες σε οντότητες με κωδικό.
 * Παρέχει βασικές μεθόδους προσθήκης, ενημέρωσης, διαγραφής, αναζήτησης και λίστας.
 * @param <T> Ο τύπος της οντότητας
 */
public abstract class CrudService<T extends BaseEntity> {
    // Χάρτης που αποθηκεύει τις οντότητες με βάση τον κωδικό τους
    protected final Map<String, T> entityMap = new HashMap<>();

    /**
     * Προσθέτει μια νέα οντότητα αν δεν υπάρχει ήδη με τον ίδιο κωδικό.
     * @param code Κωδικός οντότητας
     * @param entity Η οντότητα προς προσθήκη
     */
    public void add(String code, T entity) {
        if (!entityMap.containsKey(code)) {
            entityMap.put(code, entity);
        } else {
            throw new IllegalArgumentException("Υπάρχει ήδη οντότητα με αυτόν τον κωδικό.");
        }
    }

    /**
     * Ενημερώνει μια υπάρχουσα οντότητα με βάση τον κωδικό.
     * @param code Κωδικός οντότητας
     * @param entity Η νέα οντότητα
     */
    public void update(String code, T entity) {
        if (entityMap.containsKey(code)) {
            entityMap.put(code, entity);
        } else {
            throw new NoSuchElementException("Δεν βρέθηκε οντότητα με αυτόν τον κωδικό.");
        }
    }

    /**
     * Διαγράφει μια οντότητα με βάση τον κωδικό.
     * @param code Κωδικός οντότητας
     */
    public void delete(String code) {
        if (entityMap.containsKey(code)) {
            entityMap.remove(code);
        } else {
            throw new NoSuchElementException("Δεν βρέθηκε οντότητα με αυτόν τον κωδικό.");
        }
    }

    /**
     * Επιστρέφει την οντότητα με τον δοσμένο κωδικό ή null αν δεν υπάρχει.
     * @param code Κωδικός οντότητας
     * @return Η οντότητα ή null
     */
    public T get(String code) {
        return entityMap.get(code);
    }

    /**
     * Επιστρέφει λίστα με όλες τις οντότητες.
     * @return Λίστα οντοτήτων
     */
    public List<T> getAll() {
        return new ArrayList<>(entityMap.values());
    }

    /**
     * Ελέγχει αν υπάρχει οντότητα με τον δοσμένο κωδικό.
     * @param code Κωδικός οντότητας
     * @return true αν υπάρχει, false αλλιώς
     */
    public boolean exists(String code) {
        return entityMap.containsKey(code);
    }
}
