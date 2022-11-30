package be.vinci.ipl.chattycar.trips;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
public class TripsController {

    private final TripsService service;

    public TripsController(TripsService service) {
        this.service = service;
    }

    @PostMapping("/trips")
    public ResponseEntity<NewTrip> createOne(@RequestBody NewTrip newTrip) {
        if (newTrip.getDestination() == null || newTrip.getOrigin() == null
                || newTrip.getDeparture() == null || newTrip.getDriverId() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        service.createOne(newTrip);
        return new ResponseEntity<>(newTrip, HttpStatus.CREATED);
    }

    @GetMapping("/trips")
    public Iterable<Trip> readAll(@RequestParam(value = "departure_date", required = false) LocalDate departureDate,
                                  @RequestParam(required = false) Double originLat,
                                  @RequestParam(required = false) Double originLon,
                                  @RequestParam(required = false) Double destinationLat,
                                  @RequestParam(required = false) Double destinationLon) {
        Iterable<Trip> response = service.readAll();
        ArrayList<Trip> trips = new ArrayList<>();

        for(Trip t : response) {
            if(t.getAvailableSeating() > 0) {
                if(departureDate != null && departureDate.isEqual(t.getDeparture())) {
                    trips.add(t);
                }
                else if(originLat != null && originLon != null && destinationLat != null && destinationLon != null) {
                    trips.add(t);
                }
                else if(originLat != null && originLon != null) {
                    trips.add(t);
                }
                else if(destinationLat != null && destinationLon != null) {
                    trips.add(t);
                }
                else {
                    trips.add(t);
                }
            }
        }
        return trips;
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

    @PutMapping("/trips/inscription/{id}")
    public Trip updateAvailableSeating(@PathVariable int id) {
        Trip tripUpdated = service.readOne(id);
        if(tripUpdated == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        tripUpdated.setAvailableSeating(tripUpdated.getAvailableSeating() - 1);
        service.updateAvailableSeating(tripUpdated);
        return tripUpdated;
    }



}
