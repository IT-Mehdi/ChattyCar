package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.models.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
public class GatewayController {

  private final GatewayService service;

  public GatewayController(GatewayService service) {
    this.service = service;
  }

  @PostMapping("/auth")
  public String connect(@RequestBody Credentials credentials) {
    return service.connect(credentials);
  }

  @PostMapping("/users")
  public User createUser(@RequestBody NewUser user) {
    return service.createUser(user);
  }

  @GetMapping("/users")
  public User readUserByEmail(@RequestParam String email) {
    return service.readUserByEmail(email);
  }

  @GetMapping("/users/{id}")
  public User readUserById(@PathVariable int id, @RequestHeader("Authorization") String token) {
    checkUserTokenById(id, token);
    return service.readUserById(id);
  }

  @PutMapping("/users")
  public void updatePassword(@RequestBody Credentials credentials,
      @RequestHeader("Authorization") String token) {
    if (credentials.getEmail() == null || credentials.getPassword() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Credentials in request are not correct");
    }
    checkUserTokenByEmail(credentials.getEmail(), token);
    service.updateCredentials(credentials);
  }

  @PutMapping("/users/{id}")
  public void updateUser(@PathVariable Integer id, @RequestBody User user,
      @RequestHeader("Authorization") String token) {
    if (user.getEmail() == null || user.getFirstname() == null || user.getLastname() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User in request are not correct");
    }
    if (!user.getId().equals(id)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
    checkUserTokenByEmail(user.getEmail(), token);
    service.updateUser(id, user);
  }

  @DeleteMapping("/users/{id}")
  public void deleteUser(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
    checkUserTokenById(id, token);
    service.deleteUser(id);
  }

  @GetMapping("/users/{id}/driver")
  public Iterable<Trip> readTripsByDriver(@PathVariable Integer id,
      @RequestHeader("Authorization") String token) {
    checkUserTokenById(id, token);
    return service.readTripsByDriver(id);
  }

  @GetMapping("/users/{id}/passenger")
  public PassengerTrips readTripsByPassenger(@PathVariable Integer id,
      @RequestHeader("Authorization") String token) {
    checkUserTokenById(id, token);
    return service.readTripsByPassenger(id);
  }

  @PostMapping("/trips")
  public Trip createTrip(@RequestBody NewTrip newTrip,
      @RequestHeader("Authorization") String token) {
    if (newTrip.getDriverId() == null || newTrip.getDeparture() == null
        || newTrip.getOrigin() == null || newTrip.getDestination() == null
        || newTrip.getAvailableSeating() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip in request is not correct");
    }
    checkUserTokenById(newTrip.getDriverId(), token);
    return service.createTrip(newTrip);
  }

  @GetMapping("/trips")
  public Iterable<Trip> readTrips(
      @RequestParam(value = "departure_date", required = false) String departureDate,
      @RequestParam(required = false) Double originLat,
      @RequestParam(required = false) Double originLon,
      @RequestParam(required = false) Double destinationLat,
      @RequestParam(required = false) Double destinationLon) {

    if ((originLat != null && originLon == null) || (originLat == null && originLon != null)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Both latitude and longitude should be specified for a position query");
    }
    if ((destinationLat != null && destinationLon == null)
        || (destinationLat == null && destinationLon != null)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Both latitude and longitude should be specified for a position query");
    }

    return service.readTrips(departureDate, originLat, originLon, destinationLat, destinationLon);
  }

  @GetMapping("/trips/{id}")
  public Trip readTripById(@PathVariable Integer id) {
    return service.readTripById(id);
  }

  @DeleteMapping("/trips/{id}")
  public ResponseEntity<Void> deleteTrip(@PathVariable Integer id,
      @RequestHeader("Authorization") String token) {
    checkDriverToken(id, token);
    service.deleteTrip(id);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/trips/{id}/passengers")
  public Passengers readAllPassengersTrip(@PathVariable Integer id,
      @RequestHeader("Authorization") String token) {
    checkDriverToken(id, token);
    return service.readAllPassengersTrip(id);
  }

  @PostMapping("/trips/{trip_id}/passengers/{user_id}")
  public ResponseEntity<Void> createInscription(@PathVariable("trip_id") Integer tripId,
      @PathVariable("user_id") Integer userId,
      @RequestHeader("Authorization") String token) {
    checkUserTokenById(userId, token);

    try {
      service.readTripById(tripId);
      service.readUserById(userId);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip or user not found");
    }

    //verifier le nombre de place dans le trip
    //si une place dispo alors on diminue le nombre de place et on crée une inscription
    Trip trip = service.readTripById(tripId);
    if (trip.getAvailableSeating() <= 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The ride has no available seating left");
    }
    service.createInscription(tripId, userId); // on crée une inscription
    service.decreaseNumberOfAvailableSeat(tripId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/trips/{trip_id}/passengers/{user_id}")
  public String readPassengerStatus(@PathVariable("trip_id") Integer tripId,
      @PathVariable("user_id") Integer userId,
      @RequestHeader("Authorization") String token) {
    checkDriverOrPassengerToken(tripId, userId, token);

    try {
      service.readTripById(tripId);
      service.readUserById(userId);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip or user not found");
    }

    return service.readPassengerStatus(tripId, userId);
  }

  @PutMapping("/trips/{trip_id}/passengers/{user_id}")
  public ResponseEntity<Void> updatePassengerStatus(@PathVariable("trip_id") Integer tripId,
      @PathVariable("user_id") Integer userId,
      @RequestParam String status, @RequestHeader("Authorization") String token) {
    checkDriverToken(tripId, token);

    Passengers user = service.readAllPassengersTrip(tripId);
    User passenger = null;
    List<User> pendingAcceptedUser = user.getPending();
    pendingAcceptedUser.addAll(user.getAccepted());
    for (User u : pendingAcceptedUser) {
      if (u.getId().equals(userId)) {
        passenger = u;
      }
    }
    if (passenger == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "User is not a passenger, or is not in pending status, or status not in accepted value");
    }
    try {
      service.readTripById(tripId);
      service.readUserById(userId);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip or user not found");
    }

    service.updatePassengerStatus(tripId, userId, status);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/trips/{trip_id}/passengers/{user_id}")
  public ResponseEntity<Void> deletePassenger(@PathVariable("trip_id") Integer tripId,
      @PathVariable("user_id") Integer userId,
      @RequestHeader("Authorization") String token) {
    checkDriverToken(tripId, token);

    Passengers user = service.readAllPassengersTrip(tripId);
    User passenger = null;
    List<User> allPassenger = user.getPending();
    allPassenger.addAll(user.getAccepted());
    allPassenger.addAll(user.getRefused());

    for (User u : allPassenger) {
      if (u.getId().equals(userId)) {
        passenger = u;
      }
    }
    if (passenger == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "User is not a passenger");
    }

    try {
      service.readTripById(tripId);
      service.readUserById(userId);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip or user not found");
    }

    service.deletePassenger(tripId, userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/users/{id}/notifications")
  public Iterable<Notification> readUserNotifications(@PathVariable int id,
      @RequestHeader("Authorization") String token) {
    checkUserTokenById(id, token);
    return service.readUserNotifications(id);
  }

  @DeleteMapping("/users/{id}/notifications")
  public void deleteUserNotifications(@PathVariable int id,
      @RequestHeader("Authorization") String token) {
    checkUserTokenById(id, token);
    service.deleteUserNotifications(id);
  }

  private void checkUserTokenById(int id, String token) {
    String userEmail = service.verify(token);
    User user = service.readUserById(id);
    if (!userEmail.equals(user.getEmail())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
  }

  private void checkUserTokenByEmail(String email, String token) {
    String userEmail = service.verify(token);
    if (!userEmail.equals(email)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
  }

  private void checkDriverToken(int tripId, String token) {
    String userEmail = service.verify(token);
    User driver = service.readUserByEmail(userEmail);
    Trip trip = service.readTripById(tripId);
    if (driver.getId() != trip.getDriverId()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
  }

  private void checkDriverOrPassengerToken(int tripId, int userId, String token) {
    String userEmail = service.verify(token);
    User driver = service.readUserByEmail(userEmail);
    User passenger = service.readUserById(userId);
    Trip trip = service.readTripById(tripId);
    if (!userEmail.equals(passenger.getEmail()) && driver.getId() != trip.getDriverId()) {
      throw new ResponseStatusException(
          HttpStatus.FORBIDDEN);
    }
  }
}
