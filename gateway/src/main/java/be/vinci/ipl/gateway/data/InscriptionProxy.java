package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.Inscription;
import be.vinci.ipl.gateway.models.PassengerTrips;
import be.vinci.ipl.gateway.models.Passengers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
@FeignClient(name = "inscriptions")
public interface InscriptionProxy {

  @GetMapping("/inscriptions/{trip_id}")
  Passengers readAllPassengersTrip(@PathVariable("trip_id") int tripId);

  @DeleteMapping("/inscriptions/{trip_id}")
  void deleteAllPassengersTrip(@PathVariable("trip_id") int tripId);

  @GetMapping("/inscriptions/passengers/{passenger_id}")
  PassengerTrips readAllTripsPassenger(@PathVariable("passenger_id") int passengerId);

  @DeleteMapping("/inscriptions/passengers/{passenger_id}")
  void deleteAllTripsPassenger(@PathVariable("passenger_id") int passengerId);

  @PostMapping("/inscriptions/{trip_id}/{passenger_id}")
  Inscription createInscription(@PathVariable("trip_id") int tripId, @PathVariable("passenger_id") int passengerId);

  @GetMapping("/inscriptions/{trip_id}/{passenger_id}")
  String readInscription(@PathVariable("trip_id") int tripId, @PathVariable("passenger_id") int passengerId);

  @DeleteMapping("/inscriptions/{trip_id}/{passenger_id}")
  void deleteInscription(@PathVariable("trip_id") int tripId, @PathVariable("passenger_id") int passengerId);

  @PutMapping("/inscriptions/{trip_id}/{passenger_id}")
  void updateInscription(@PathVariable("trip_id") int tripId, @PathVariable("passenger_id") int passengerId, @RequestParam("status") String status);

}
