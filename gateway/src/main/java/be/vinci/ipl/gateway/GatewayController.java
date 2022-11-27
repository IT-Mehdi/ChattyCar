package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.models.*;
import javax.ws.rs.PathParam;
import org.springframework.web.bind.annotation.*;


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
  User readUserByEmail(@RequestParam String email){
    return service.readUserByEmail(email);
  }

  @GetMapping("/users/{id}")
  User readUserById(@PathVariable int id){
    return service.readUserById(id);
  }


}