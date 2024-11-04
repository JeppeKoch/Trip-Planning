package dat.routes;

import dat.daos.TripDao;
import dat.dtos.TripDto;
import dat.entities.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class Populator {
    private static EntityManagerFactory emf;
    private static TripDao tripDao;

    public Populator(TripDao tripDao, EntityManagerFactory emf) {
        this.tripDao = tripDao;
        this.emf = emf;
    }
    public List<TripDto> populate3Trips(){

        TripDto s1, s2, s3;
        s1 = new TripDto("Trip 1", LocalTime.of(9, 0), LocalTime.of(11, 0), "Start Position 1", 50.0, Category.CITY);
        s2 = new TripDto("Trip 2", LocalTime.of(12, 0), LocalTime.of(14, 0), "Start Position 2", 75.0, Category.BEACH);
        s3 = new TripDto("Trip 3", LocalTime.of(15, 0), LocalTime.of(17, 0), "Start Position 3", 100.0, Category.BEACH);


        s1 = tripDao.create(s1);
        s2 = tripDao.create(s2);
        s3 = tripDao.create(s3);

        return new ArrayList<>(List.of(s1, s2, s3));
    }

    public void clean() {
        try (
                EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Trip ").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE trip_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
