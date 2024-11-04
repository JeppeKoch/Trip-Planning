package dat.daos;

import dat.dtos.GuideDto;
import dat.dtos.TripDto;
import dat.entities.Category;
import dat.entities.Guide;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import dat.entities.Trip;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class TripDao implements IDAO<TripDto, Integer>, ITripGuide<TripDto> {
    EntityManagerFactory emf;
    public TripDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    @Override
    public TripDto create(TripDto tripDto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = new Trip(tripDto);

            if (tripDto.getGuideId() != null) {
                Guide guide = em.find(Guide.class, tripDto.getGuideId());
                if (guide != null) {
                    trip.setGuide(guide);
                } else {
                    throw new IllegalArgumentException("Guide not found with ID: " + tripDto.getGuideId());
                }
            }
            if (trip.getId() != null) {
                trip = em.merge(trip);
            } else {
                em.persist(trip);
            }

            em.getTransaction().commit();
            return new TripDto(trip);
        }

    }

    @Override
    public TripDto getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            if (trip == null) {
                throw new IllegalArgumentException("Trip not found with id: " + id);
            }
            // Ensure guide is loaded
            if (trip.getGuide() != null) {
                trip.getGuide().getId(); // Force load the guide
            }
            return new TripDto(trip);
        }
    }

    @Override
    public List<TripDto> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<TripDto> query = em.createQuery("SELECT new dat.dtos.TripDto(r) FROM Trip r", TripDto.class);
            return query.getResultList();
        }
    }

    @Override
    public TripDto update(TripDto tripDto, Long id) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if(trip != null){
                trip.setName(tripDto.getName());
                trip.setPrice(tripDto.getPrice());
                trip.setStartposition(tripDto.getStartposition());
                trip.setEndtime(tripDto.getEndtime());
                trip.setStarttime(tripDto.getStarttime());
                trip.setCategory(tripDto.getCategory());
                em.merge(trip);
            }
            em.getTransaction().commit();
            return new TripDto(trip);
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if (trip != null){
                em.remove(trip);
            }
            em.getTransaction().commit();
        }

    }

    @Override
    public void addGuideToTrip(Long tripId, Long guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripId);
            Guide guide = em.find(Guide.class, guideId);
            if (trip != null) {
                trip.setGuide(guide);
                em.merge(trip);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public Set<TripDto> getTripsByGuide(Long guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t JOIN t.guide g WHERE g.id = :guideId", Trip.class);
            query.setParameter("guideId", guideId);
            List<Trip> trips = query.getResultList();
            return trips.stream().map(TripDto::new).collect(Collectors.toSet());
        }
        }

    public List<TripDto> getTripsByCategory(Category category) {
        List<TripDto> tripList = getAll();
        return tripList.stream()
                .filter(trip -> trip.getCategory().equals(category))
                .toList();
    }

    public Double totalPriceForGuidesTrips(Long guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t JOIN t.guide g WHERE g.id = :guideId", Trip.class);
            query.setParameter("guideId", guideId);
            List<Trip> trips = query.getResultList();
            return trips.stream()
                    .mapToDouble(Trip::getPrice)
                    .sum();
        }
    }

    public GuideDto getGuideByTripId(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            if (trip == null) {
                throw new IllegalArgumentException("Trip not found with id: " + id);
            }
            Guide guide = trip.getGuide();
            return guide != null ? new GuideDto(guide) : null;
        }
    }
}

