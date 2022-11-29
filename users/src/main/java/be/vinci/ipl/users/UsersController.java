package be.vinci.ipl.users;

import be.vinci.ipl.users.models.NoIdUser;
import be.vinci.ipl.users.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UsersController {
    private final UsersService service;

    public UsersController(UsersService service) {
        this.service = service;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createOne(@RequestParam String email, @RequestBody NoIdUser user) {
        if (user.getEmail() == null || !user.getEmail().equals(email) || user.getLastname() == null || user.getFirstname() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User createdUser = service.createOne(user);
        if (createdUser == null) throw new ResponseStatusException(HttpStatus.CONFLICT);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public User readONe(@RequestParam String email) {
        User user = service.readOneEmail(email);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return user;
    }

    @GetMapping("/users/{id}")
    public User readOne(@PathVariable int id) {
        User user = service.readOneId(id);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return user;
    }

    @PutMapping("/users/{id}")
    public void updateOne(@PathVariable int id, @RequestBody User user) {
        if (user.getId() != id || user.getEmail() == null || user.getLastname() == null || user.getFirstname() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        boolean found = service.updateOne(user);
        if (!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/users/{id}")
    public void deleteOne(@PathVariable int id) {
        boolean found = service.deleteOne(id);
        if (!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
