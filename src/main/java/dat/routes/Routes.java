package dat.routes;

import dat.security.routes.SecurityRoutes;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {


    private final SecurityRoutes authRoute = new SecurityRoutes();

    private final TripRoutes tripRoutes;


    public Routes(EntityManagerFactory emf) {
        tripRoutes = new TripRoutes(emf);

    }

    public EndpointGroup getRoutes() {
        return () -> {

            path("/auth", authRoute.getSecurityRoutes());
            path("/trips", tripRoutes.getRoutes());

        };
    }
}
