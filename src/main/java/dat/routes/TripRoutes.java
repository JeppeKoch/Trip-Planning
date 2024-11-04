package dat.routes;

import dat.config.Populate;
import dat.controllers.impl.TripController;
import dat.daos.TripDao;
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
            post("/", tripController::create);
            get("/", tripController::getAll);
            get("/{id}", tripController::getById);
            put("/{id}", tripController::update);
            delete("/{id}", tripController::delete);
            put("/{id}/guides/{guideId}", tripController::addGuide);
            post("/populate", ctx -> {Populate.populateDatabase(emf);});
            get("/category/{category}", tripController::filterByCategory);
            get("/guide/totalPrice/{guideId}", tripController::totalPriceForGuidesTrips);
        };
    }
}
