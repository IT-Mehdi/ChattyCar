package be.vinci.ipl.inscriptions;

import be.vinci.ipl.inscriptions.models.Inscription;
import be.vinci.ipl.inscriptions.models.PassengerTrips;
import be.vinci.ipl.inscriptions.models.Passengers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class InscriptionsController {
  private final InscriptionsService service;

  public InscriptionsController(InscriptionsService service){
    this.service = service;
  }

  /**
   * Get all the passengers of a trip
   * @param tripId the id of the trip
   * @return a Passengers object containing the list of passengers
   */
  @GetMapping("/inscriptions/{trip_id}")
  public ResponseEntity<Passengers> getAllPassengers(@PathVariable("trip_id") Integer tripId){
    Passengers passengers = service.getAllPassengersOfATrip(tripId);
    if(passengers == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No passengers found for this trip");
    return new ResponseEntity<>(passengers, HttpStatus.OK);
  }

  /**
   * Get all the trips of a passenger
   * @param tripId the id of the passenger
   */
  @DeleteMapping("/inscriptions/{trip_id}")
  public ResponseEntity<Void> deleteAllPassengerOfATrip(@PathVariable("trip_id") Integer tripId){
    if(!service.deleteAllInscriptionOfATrip(tripId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No passengers found for this trip");
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Get all the trips of a passenger
   * @param passenger_id the id of the passenger
   * @return a PassengerTrips object containing the list of trips
   */
  @GetMapping("/inscriptions/passengers/{passenger_id}")
  public ResponseEntity<PassengerTrips> getAllInscriptions(@PathVariable("passenger_id") Integer passenger_id){
    //:TODO
    PassengerTrips passengerTrips = service.getAllInscriptionsOfAPassenger(passenger_id);
    if(passengerTrips == null){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No inscriptions found for this passenger");
    }
    return new ResponseEntity<>(passengerTrips, HttpStatus.OK);
  }

  /**
   * Delete all the inscriptions of a passenger
   * @param passenger_id the id of the passenger
   */
  @DeleteMapping("/inscriptions/passengers/{passenger_id}")
  public ResponseEntity<Void> deleteAllTrips(@PathVariable("passenger_id") Integer passenger_id) {
    if(!service.deleteAllInscriptionOfAPassenger(passenger_id))throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No inscriptions found for this passenger");
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  /**
   * Create a new inscription
   * @param passengerId the id of the user
   * @param tripId the id of the trip
   * @return the inscription created
   */
  @PostMapping("/inscriptions/{tripId}/{passenger_id}")
  public ResponseEntity<Void> createOne(@PathVariable("passenger_id") Integer passengerId,@PathVariable("tripId") Integer tripId) {
    Inscription inscription = service.createInscription(tripId, passengerId);
    if (inscription == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User is already registered for this trip");
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Get one inscription by tripId and userId
   * @param passengerId the id of the user
   * @param tripId the id of the trip
   * @return the inscription
   */
  @GetMapping("/inscriptions/{tripId}/{passenger_id}")
  public ResponseEntity<String> getInscriptionStatus(@PathVariable("passenger_id") Integer passengerId,@PathVariable("tripId") Integer tripId){
    if (passengerId == null || tripId == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    Inscription inscription = service.getInscription(tripId, passengerId);
    if (inscription == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(inscription.getStatus(), HttpStatus.OK);
  }

  /**
   * Delete an inscription
   * @param passengerId the id of the user
   * @param tripId the id of the trip
   */
  @DeleteMapping("/inscriptions/{tripId}/{passenger_id}")
  public ResponseEntity<Void> deleteOne(@PathVariable("passenger_id") Integer passengerId,@PathVariable("tripId") Integer tripId){
    if (passengerId == null || tripId == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    Inscription inscription = service.getInscription(tripId, passengerId);
    if (inscription == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    service.deleteInscription(tripId, passengerId);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  /**
   * Edit the status of an inscription
   * @param passengerId the id of the user
   * @param tripId the id of the trip
   * @param status the new status
   */
  @PutMapping("/inscriptions/{tripId}/{passenger_id}")
  public ResponseEntity<Void> editStatus(@PathVariable("passenger_id") Integer passengerId,@PathVariable("tripId") Integer tripId, @RequestParam(required = true) String status){
    if (passengerId == null || tripId == null || (!status.equals("accepted") && !status.equals("refused"))) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    Inscription inscriptionEdited = service.editInscriptionStatus(tripId,passengerId,status);
    if (inscriptionEdited == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
