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
    public Trip createOne(@RequestBody Trip trip) {
        if (trip.getId() < 0 || trip.getDestination() == null || trip.getOrigin() == null
                || trip.getDeparture() == null || trip.getDriverId() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        boolean created = service.createOne(trip);
        if (!created) throw new ResponseStatusException(HttpStatus.CONFLICT);
        return trip;
    }

    @GetMapping("/trips")
    public Iterable<Trip> readAll() {
        return service.readAll();
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
        return service.readAllTripOfTheDriver(id);
    }

    @DeleteMapping("/trips/driver/{id}")
    public void deleteByDrivers(@PathVariable int id) {
        boolean found = service.deleteByDriverId(id);
        if(!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }



}
