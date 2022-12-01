package be.vinci.ipl.positions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PositionsController {

    private final PositionsService service;

    public PositionsController(PositionsService service) {
        this.service = service;
    }

    @GetMapping("/trips/position")
    public double calculateDistance(@RequestParam(value = "originLatitude", required = true) double originLatitude,
                                    @RequestParam(value = "originLongitude", required = true) double originLongitude,
                                    @RequestParam(value = "destLatitude", required = true) double destLatitude,
                                    @RequestParam(value = "destLongitude", required = true) double destLongitude) {
        if(originLatitude < 0 || originLongitude < 0 || destLatitude < 0 || destLongitude < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return service.calculateDistance(originLatitude, originLongitude, destLatitude, destLongitude);
    }
}
