package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.models.*;
import javax.ws.rs.PathParam;
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
  String connect(@RequestBody Credentials credentials) {
    return service.connect(credentials);
  }

  @PostMapping("/users")
  User createUser(@RequestBody UserWithCredentials credentials) {
    return service.createUser(credentials);
  }

  @GetMapping("/users")
  User readUserByEmail(@RequestParam String email) {
    return service.readUserByEmail(email);
  }

  @GetMapping("/users/{id}")
  User readUserById(@PathVariable int id) {
    return service.readUserById(id);
  }

  @PutMapping("/users")
  void updatePassword(@RequestBody Credentials credentials, @RequestHeader("Authorization") String token) {

    String userEmail = service.verify(token);
    if (!userEmail.equals(credentials.getEmail())) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

    service.updateCredentials(credentials);
  }

  @PutMapping("/users/{id}")
  void updateUser(@PathVariable int id, @RequestBody User user, @RequestHeader("Authorization") String token){
    if (user.getId() != id) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

    String userEmail = service.verify(token);
    if (!userEmail.equals(user.getEmail())) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

    service.updateUser(id, user);
  }


}
