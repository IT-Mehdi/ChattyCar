package be.vinci.ipl.positions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PositionsController {

    private final PositionsService service;

    public PositionsController(PositionsService service) {
        this.service = service;
    }

    @PostMapping("/trips/position")
    public double calculateDistance(@RequestBody Trip trip) {
        if(trip.getOrigin() == null || trip.getDestination() == null || trip.getDestination().getLatitude() < 0 ||
                trip.getDestination().getLongitude() < 0 || trip.getOrigin().getLatitude() < 0 ||
                trip.getOrigin().getLongitude() < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.calculateDistance(trip);
    }
}
