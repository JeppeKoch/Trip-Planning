package dat.config;

import dat.daos.TripDao;
import dat.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class Populate {
    private final EntityManagerFactory emf;
    private final TripDao tripDao;

    public Populate(EntityManagerFactory emf, TripDao tripDao) {
        this.emf = emf;
        this.tripDao = tripDao;
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("trip");
        populateDatabase(emf);
        emf.close();
    }

        public static Set<Guide> populateDatabase(EntityManagerFactory emf) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            // Create sets to hold guides and their trips
            Set<Guide> guides = new HashSet<>();

            // Create trips for the first guide
            Set<Trip> tripsForGuide1 = Set.of(
                    new Trip(LocalTime.of(9, 0), LocalTime.of(11, 0), "City Center", "Morning City Tour", 49.99, Category.CITY),
                    new Trip(LocalTime.of(14, 0), LocalTime.of(16, 0), "Mountain Base", "Mountain Exploration", 89.99, Category.BEACH)
            );

            // Create trips for the second guide
            Set<Trip> tripsForGuide2 = Set.of(
                    new Trip(LocalTime.of(10, 0), LocalTime.of(12, 0), "Historic Quarter", "Historic Sites Tour", 59.99, Category.LAKE),
                    new Trip(LocalTime.of(13, 0), LocalTime.of(15, 0), "Coastal Bay", "Beach Relaxation", 79.99, Category.BEACH)
            );

            // Create Guide 1 and associate trips
            Guide guide1 = new Guide(null, "Alice Johnson", "Alice", "alice.johnson@example.com", "123456789", 7, new HashSet<>(tripsForGuide1));
            tripsForGuide1.forEach(trip -> trip.setGuide(guide1));

            // Create Guide 2 and associate trips
            Guide guide2 = new Guide(null, "Bob Smith", "Bob", "bob.smith@example.com", "987654321", 10, new HashSet<>(tripsForGuide2));
            tripsForGuide2.forEach(trip -> trip.setGuide(guide2));

            // Persist guides (cascades will persist associated trips)
            em.persist(guide1);
            em.persist(guide2);

            // Commit transaction and close entity manager
            em.getTransaction().commit();
            em.close();

            // Add guides to the set and return
            guides.add(guide1);
            guides.add(guide2);

            em.getTransaction().commit();
            System.out.println("Database populated with sample guides and trips.");
            return guides;
        }
        }


