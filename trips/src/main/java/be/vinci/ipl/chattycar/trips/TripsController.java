package be.vinci.ipl.chattycar.trips;

import be.vinci.ipl.chattycar.trips.models.NewTrip;
import be.vinci.ipl.chattycar.trips.models.Trip;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@RestController
public class TripsController {

    private final TripsService service;

    public TripsController(TripsService service) {
        this.service = service;
    }

    @PostMapping("/trips")
    public ResponseEntity<Trip> createOne(@RequestBody NewTrip newTrip) {
        if (newTrip.getDestination() == null || newTrip.getOrigin() == null
                || newTrip.getDeparture() == null || newTrip.getDriverId() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Trip tripCreated = service.createOne(newTrip);
        return new ResponseEntity<>(tripCreated, HttpStatus.CREATED);
    }

    @GetMapping("/trips")
    public Iterable<Trip> readAll(@RequestParam(value = "departureDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
                                  @RequestParam(value = "originLat", required = false) Double originLat,
                                  @RequestParam(value = "originLon", required = false) Double originLon,
                                  @RequestParam(value = "destinationLat", required = false) Double destinationLat,
                                  @RequestParam(value = "destinationLon", required = false) Double destinationLon) {
        Iterable<Trip> response = service.readAll();

        HashMap<Trip, Double> trips = new HashMap<>();

        for(Trip t : response) {
            if(t.getAvailableSeating() > 0) {
                if(departureDate != null) {
                    if(departureDate.isEqual(t.getDeparture())) {
                        trips.put(t, -1.0);
                    }
                }
                else if(originLat != null && originLon != null && destinationLat != null && destinationLon != null) {
                    trips.put(t, service.calculateDistance(originLat, originLon, t.getOrigin().getLatitude(), t.getOrigin().getLongitude())
                            + service.calculateDistance(destinationLat, destinationLon, t.getDestination().getLatitude(), t.getDestination().getLongitude())); //A verifier
                }
                else if(originLat != null && originLon != null) {
                    trips.put(t, service.calculateDistance(originLat, originLon, t.getOrigin().getLatitude(), t.getOrigin().getLongitude()));
                }
                else if(destinationLat != null && destinationLon != null) {
                    trips.put(t, service.calculateDistance(destinationLat, destinationLon, t.getDestination().getLatitude(), t.getDestination().getLongitude()));
                }
                else {
                    trips.put(t, -1.0);
                }
            }
        }

        if(departureDate != null) {
            ArrayList<Trip> sortedTrips = new ArrayList<>(trips.keySet());
            return sortedTrips.stream().limit(20).toList();
        }
        else if(originLat != null && originLon != null && destinationLat != null && destinationLon != null) {
            return sortedTripsByDistance(trips);
        }
        else if(originLat != null && originLon != null) {
            return sortedTripsByDistance(trips);
        }
        else if(destinationLat != null && destinationLon != null) {
            return sortedTripsByDistance(trips);
        }
        else {
            ArrayList<Trip> sortedTrips = new ArrayList<>(trips.keySet());
            return sortedTrips.stream().sorted(Comparator.comparingInt(Trip::getId).reversed()).limit(20).toList();
        }
    }

    /**
     * Take a map of trip with the distance and return a list sorted by distance limit to 20
     * @param trips the map of trip with the distance
     * @return a list sorted by distance limit to 20
     */
    private Iterable<Trip> sortedTripsByDistance(HashMap<Trip, Double> trips) {
        SortedMap<Double, Trip> sortedMapTrips = new TreeMap<>();
        for(Map.Entry<Trip, Double> entry : trips.entrySet()) {
            sortedMapTrips.put(entry.getValue(), entry.getKey());
        }
        ArrayList<Trip> sortedTrips = new ArrayList<>(sortedMapTrips.values());
        return sortedTrips.stream().limit(20).toList();
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
