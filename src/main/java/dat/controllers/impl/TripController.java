package dat.controllers.impl;

import dat.daos.TripDao;
import dat.dtos.GuideDto;
import dat.dtos.TripDto;
import dat.entities.Category;
import dat.security.exceptions.ApiException;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public class TripController {
    private final Logger log = LoggerFactory.getLogger(TripController.class);
    private EntityManagerFactory emf;
    private TripDao tripDao;


    public TripController(TripDao tripDao) {
        this.tripDao = tripDao;
    }



    public void getById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            TripDto tripDto = tripDao.getById(id);
            GuideDto guideDto = tripDao.getGuideByTripId(id);
            tripDto.setGuide(guideDto);
            ctx.json(tripDto);
        } catch (Exception e) {
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }

    }



    public void getAll(Context ctx) {
        try {
            List<TripDto> tripDtos = tripDao.getAll();
            // response
            ctx.res().setStatus(200);
            ctx.json(tripDtos, TripDto.class);
        } catch (Exception e) {
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }


    public void create(Context ctx) {
        try {

            TripDto tripDto = ctx.bodyAsClass(TripDto.class);
            ctx.status(HttpStatus.CREATED);
            ctx.json(tripDao.create(tripDto));

            // == response ==
            ctx.res().setStatus(201);
        } catch (Exception e) {
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }


    public void update(Context ctx) {
        try {
            TripDto tripDto = ctx.bodyAsClass(TripDto.class);

            // == querying ==
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            tripDao.update(tripDto, id);

            // == response ==
            ctx.res().setStatus(200);
        } catch (Exception e) {
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }


    public void delete(Context ctx) {
        try {
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            // entity
            tripDao.delete(id);
            // response
            ctx.res().setStatus(204);
        } catch (Exception e) {
            log.error("400{}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }


    public void addGuide(Context ctx) {
        try {
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            Long guideId = ctx.pathParamAsClass("guideId", Long.class).get();
            tripDao.addGuideToTrip(id, guideId);
            ctx.res().setStatus(200);
        } catch (Exception e) {
            log.error("400{}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void filterByCategory(Context ctx){
        try{
            Category category = Category.valueOf(ctx.pathParam("category").toUpperCase());

            List<TripDto> tripDtos = tripDao.getTripsByCategory(category);
            ctx.res().setStatus(200);
            ctx.json(tripDtos, TripDto.class);
        }catch (Exception e){
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void totalPriceForGuidesTrips(Context ctx){
        try{
            Long guideId = ctx.pathParamAsClass("guideId", Long.class).get();

            Double totalPrice = tripDao.totalPriceForGuidesTrips(guideId);
            ctx.res().setStatus(200);
            ctx.json(Map.of("totalPrice", totalPrice));

        }catch (Exception e){
            log.error("400{}",e.getMessage());
            throw new ApiException(400, e.getMessage());
        }

    }

}
