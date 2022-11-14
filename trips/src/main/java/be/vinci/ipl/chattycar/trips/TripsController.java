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
                || trip.getDeparture() == null || trip.getDriver_id() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        boolean created = service.createOne(trip);
        if (!created) throw new ResponseStatusException(HttpStatus.CONFLICT);
        return trip;
    }


}
