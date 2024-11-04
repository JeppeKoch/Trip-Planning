package dat.routes;

import dat.config.Populate;
import dat.controllers.impl.TripController;
import dat.daos.TripDao;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;


public class TripRoutes {
    private final TripController tripController;
    private EntityManagerFactory emf;

    public TripRoutes(EntityManagerFactory emf) {
        tripController = new TripController(new TripDao(emf));
        this.emf = emf;
    }


    protected EndpointGroup getRoutes() {

        return () -> {
            post("/", tripController::create, Role.ADMIN);
            get("/", tripController::getAll, Role.ANYONE);
            get("/{id}", tripController::getById, Role.ANYONE);
            put("/{id}", tripController::update, Role.ADMIN);
            delete("/{id}", tripController::delete, Role.ADMIN);
            put("/{id}/guides/{guideId}", tripController::addGuide, Role.ADMIN);
            post("/populate", ctx -> {Populate.populateDatabase(emf);});
            get("/category/{category}", tripController::filterByCategory, Role.ANYONE);
            get("/guide/totalPrice/{guideId}", tripController::totalPriceForGuidesTrips, Role.ANYONE);
        };
    }
}
