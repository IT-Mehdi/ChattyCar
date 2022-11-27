package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.models.*;
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


}
