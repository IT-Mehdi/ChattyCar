package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.NewTrip;
import be.vinci.ipl.gateway.models.NewUser;
import be.vinci.ipl.gateway.models.Trip;
import be.vinci.ipl.gateway.models.User;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "trips")
public interface TripsProxy {

  @PostMapping("/trips")
  Trip createTrip(@RequestBody NewTrip trip);

  @GetMapping("/trips")
  List<Trip> readTrips(@RequestParam String departure_date, @RequestParam double originLat,
      @RequestParam double originLon,
      @RequestParam double destinationLat, @RequestParam double destinationLon);

  @GetMapping("/trips/{id}")
  Trip readTripById(@PathVariable int id);

  @DeleteMapping("/trips/{id}")
  void deleteTripById(@PathVariable int id);

  @GetMapping("/trips/driver/{id}")
  Iterable<Trip> readTripsByDriver(@PathVariable int id);

  @DeleteMapping("/trips/driver/{id}")
  void deleteTripsByDriver(@PathVariable int id);

  @GetMapping("/trips/passengers/{id}")
  Iterable<Trip> readTripsByPassenger(@PathVariable int id);
}
