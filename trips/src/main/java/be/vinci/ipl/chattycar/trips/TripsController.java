package be.vinci.ipl.chattycar.trips;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TripsController {

    private final TripsService service;

    public TripsController(TripsService service) {
        this.service = service;
    }

    @PostMapping("/trips")
    public ResponseEntity<Trip> createOne(@RequestBody Trip trip) {
        if (trip.getDestination() == null || trip.getOrigin() == null
                || trip.getDeparture() == null || trip.getDriverId() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        service.createOne(trip);
        return new ResponseEntity<>(trip, HttpStatus.CREATED);
    }

    @GetMapping("/trips")
    public Iterable<Trip> readAll() {
        Iterable<Trip> response = service.readAll();
        for(Trip trip : response) {
            if(trip.getOrigin().getLatitude() < 0 || trip.getOrigin().getLongitude() < 0
                    || trip.getDestination().getLatitude() < 0 || trip.getDestination().getLongitude() < 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping("/trips/{id}")
    public Trip readOne(@PathVariable int id) {
        Trip trip = service.readOne(id);
        if (trip == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return trip;
    }

    @DeleteMapping("/trips/{id}")
    public void deleteOne(@PathVariable int id) {
        boolean found = service.deleteOne(id);
        if (!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/trips/driver/{id}")
    public Iterable<Trip> readAllTripOfTheDriver(@PathVariable int id) {
        if(!service.driverExist(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return service.readAllTripOfTheDriver(id);
    }

    //Probleme renvoie erreur 500
    @DeleteMapping("/trips/driver/{id}")
    public void deleteByDrivers(@PathVariable int id) {
        boolean found = service.deleteByDriverId(id);
        if(!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }



}
