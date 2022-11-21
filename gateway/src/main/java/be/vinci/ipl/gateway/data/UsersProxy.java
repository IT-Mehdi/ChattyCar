package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "users")
public interface UsersProxy {

  @PostMapping("/users/{email}")
  void createUser(@PathVariable String email, @RequestBody User user);

  @GetMapping("/users/{email}")
  User readUser(@PathVariable String email);

  @PutMapping("/users/{email}")
  void updateUser(@PathVariable String email, @RequestBody User user);

  @DeleteMapping("/users/{email}")
  void deleteUser(@PathVariable String email);

}

