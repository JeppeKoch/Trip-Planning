package dat.daos;

import dat.dtos.GuideDto;
import dat.entities.Guide;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;


public class GuideDao implements IDAO<GuideDto, Long> {
    EntityManagerFactory emf;
    public GuideDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    @Override
    public dat.dtos.GuideDto create(GuideDto guideDto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = new Guide(guideDto);
            if (guide.getId() != 0) {
                guide = em.merge(guide);
            } else {
                em.persist(guideDto);
            }

            em.getTransaction().commit();
            return new dat.dtos.GuideDto(guide);
        }

    }

    @Override
    public GuideDto getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, id);
            return new dat.dtos.GuideDto(guide);
        }
    }

    @Override
    public List<dat.dtos.GuideDto> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<dat.dtos.GuideDto> query = em.createQuery("SELECT new dat.dtos.GuideDto(r) FROM Guide r", dat.dtos.GuideDto.class);
            return query.getResultList();
        }
    }

    @Override
    public dat.dtos.GuideDto update(GuideDto guideDto, Long id) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);
            if(guide != null){
                guide.setFirstname(guideDto.getFirstname());
                guide.setLastname(guideDto.getLastname());
                guide.setEmail(guideDto.getEmail());
                guide.setPhone(guideDto.getPhone());
                em.merge(guide);

            }
            em.getTransaction().commit();
            return new dat.dtos.GuideDto(guide);
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);
            if (guide != null){
                em.remove(guide);
            }
            em.getTransaction().commit();
        }

    }
}
