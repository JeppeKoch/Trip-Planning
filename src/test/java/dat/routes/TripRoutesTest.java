package dat.routes;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.config.Populate;
import dat.daos.TripDao;
import dat.dtos.TripDto;
import dat.entities.Category;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TripRoutesTest {
    private static Javalin app;
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final TripDao tripDao = new TripDao(emf);
    private static final String BASE_URL = "http://localhost:7080/api/trips";

    private static Populator populator = new Populator( tripDao, emf);
    private static TripDto t1, t2, t3;

    private static List<TripDto> tripDtos;

    @BeforeAll
    static void init() {
        app = ApplicationConfig.startServer(7080, emf);
    }

    @BeforeEach
    void setUp() {
        tripDtos = populator.populate3Trips();
        t1 = tripDtos.get(0);
        t2 = tripDtos.get(1);
        t3 = tripDtos.get(2);

        System.out.println("Trip 1 ID: " + t1.getId());
        System.out.println("Trip 1 Details: " + t1);

    }


    @AfterEach
    void tearDown() {
      populator.clean();
    }


    @AfterAll
    static void closeDown() {
        ApplicationConfig.stopServer(app);
    }

    @Test
    void testGetTripByID() {
        TripDto trip =
                given()
                        .when()
                        .get(BASE_URL + "/" + t1.getId())
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(TripDto.class);

        assertThat(trip, equalTo(t1));
    }

    @Test
    void testCreateTrip() {

        TripDto newTrip = new TripDto("Trip 4", LocalTime.of(16, 0), LocalTime.of(17, 0), "Start Position 3", 100.0, Category.FOREST);


        TripDto createdTrip =
                given()
                        .contentType("application/json")
                        .body(newTrip)
                        .when()
                        .post(BASE_URL + "/")
                        .then()
                        .log().all()
                        .statusCode(201)
                        .extract()
                        .as(TripDto.class);

        // Assert: Verify that the spice ID is assigned (non-zero)
        assertNotNull(createdTrip.getId());



        // Assert: Verify the spice details
        assertEquals("Trip 4", createdTrip.getName());
        assertEquals(LocalTime.of(16, 0), createdTrip.getStarttime());
        assertEquals(LocalTime.of(17, 0), createdTrip.getEndtime());
    }

    @Test
    void testUpdateTrip() {
        TripDto updatedTrip = new TripDto("updated Trip 4", LocalTime.of(16, 0), LocalTime.of(17, 0), "Start Position 3", 100.0, Category.FOREST);

        given()
                .contentType("application/json")
                .body(updatedTrip)
                .when()
                .put(BASE_URL + "/" + t1.getId())
                .then()
                .log().all()
                .statusCode(200);

        TripDto trip =
                given()
                        .when()
                        .get(BASE_URL + "/" + t1.getId())
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(TripDto.class);

        assertEquals("updated Trip 4", trip.getName());



    }

    @Test
    void testDeleteTrip() {
        given()
                .when()
                .delete(BASE_URL + "/" + t1.getId())
                .then()
                .log().all()
                .statusCode(204); }

}
