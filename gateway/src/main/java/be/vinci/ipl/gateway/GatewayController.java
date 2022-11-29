package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.models.*;
import org.springframework.http.HttpStatus;
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
  public User readUserById(@PathVariable int id) {
    return service.readUserById(id);
  }

  @PutMapping("/users")
  public void updatePassword(@RequestBody Credentials credentials, @RequestHeader("Authorization") String token) {
    checkUserTokenByEmail(credentials.getEmail(), token);
    service.updateCredentials(credentials);
  }

  @PutMapping("/users/{id}")
  public void updateUser(@PathVariable int id, @RequestBody User user, @RequestHeader("Authorization") String token){
    if (user.getId() != id) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    checkUserTokenByEmail(user.getEmail(), token);
    service.updateUser(id, user);
  }

  @DeleteMapping("/users/{id}")
  public void deleteUser(@PathVariable int id, @RequestHeader("Authorization") String token){
    checkUserTokenById(id,token);
    service.deleteUser(id);
  }

  @GetMapping("/users/{id}/driver")
  public Iterable<Trip> readTripsByDriver(@PathVariable int id, @RequestHeader("Authorization") String token){
    checkUserTokenById(id,token);
    return service.readTripsByDriver(id);
  }

  @GetMapping("/users/{id}/passenger")
  public Iterable<Trip> readTripsByPassenger(@PathVariable int id, @RequestHeader("Authorization") String token){
    checkUserTokenById(id,token);
    return service.readTripsByPassenger(id);
  }

  @PostMapping("/trips")
  public Trip createTrip(@RequestBody NewTrip newTrip, @RequestHeader("Authorization") String token){
    checkUserTokenById(newTrip.getDriverId(), token);
    return service.createTrip(newTrip);
  }

  @GetMapping("/trips")
  public Iterable<Trip> readTrips(@RequestParam String departure_date,
      @RequestParam double originLat, @RequestParam double originLon,
      @RequestParam double destinationLat, @RequestParam double destinationLon){

    return service.readTrips( departure_date,  originLat,  originLon, destinationLat, destinationLon);
  }

  @GetMapping("/trips/{id}")
  public Trip readTripById(@PathVariable int id){
    return service.readTripById(id);
  }

  @DeleteMapping("/trips/{id}")
  public void deleteTrip(@PathVariable int id){
    service.deleteTrip(id);
  }

  @GetMapping("/trips/{id}/passengers")
  public Passengers readAllPassengersTrip(@PathVariable Integer id, @RequestHeader("Authorization") String token){
    Trip trip = service.readTripById(id);
    String userEmail = service.verify(token);
    User user = service.readUserByEmail(userEmail);

    if(trip.getDriverId() != user.getId()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not identified as the corresponding user");
    return service.readAllPassengersTrip(id);
  }

  @PostMapping("/trips/{trip_id}/passengers/{user_id}")
  public void createInscription(@PathVariable("trip_id") Integer tripId, @PathVariable("user_id") Integer userId,
      @RequestHeader("Authorization") String token){
    checkUserTokenById(userId, token);

    try{
      service.readTripById(tripId);
      service.readUserById(userId);
    }catch (Exception e){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip or user not found");
    }

    service.createInscription(tripId, userId);
    throw new ResponseStatusException(HttpStatus.CREATED, "User added as pending passenger");
  }

  @GetMapping("/trips/{trip_id}/passengers/{user_id}")
  public String readPassengerStatus(@PathVariable("trip_id") Integer tripId, @PathVariable("user_id") Integer userId,
      @RequestHeader("Authorization") String token){
    checkUserTokenById(userId, token);

    try{
      service.readTripById(tripId);
      service.readUserById(userId);
    }catch (Exception e){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip or user not found");
    }

    return service.readPassengerStatus(tripId, userId);
  }

  private void checkUserTokenById(int id, String token){
    String userEmail = service.verify(token);
    User user = service.readUserById(id);
    if(!userEmail.equals(user.getEmail())) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
  }

  private void checkUserTokenByEmail(String email, String token){
    String userEmail = service.verify(token);
    if(!userEmail.equals(email)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
  }
}
