package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.NewUser;
import be.vinci.ipl.gateway.models.NoIdUser;
import be.vinci.ipl.gateway.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "users")
public interface UsersProxy {

  @PostMapping("/users")
  User createUser(@RequestBody NoIdUser user);

  @GetMapping("/users/{id}")
  User readUserById(@PathVariable int id);

  @GetMapping("/users")
  User readUserByEmail(@RequestParam String email);

  @PutMapping("/users/{id}")
  void updateUser(@PathVariable int id, @RequestBody User user);

  @DeleteMapping("/users/{id}")
  void deleteUser(@PathVariable int id);

}

