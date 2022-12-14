package be.vinci.ipl.authentication;

import be.vinci.ipl.authentication.models.InsecureCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }


    @PostMapping("/authentication/connect")
    public String connect(@RequestBody InsecureCredentials credentials) {
        String token =  service.connect(credentials);
        if (token == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return token;
    }


    @PostMapping("/authentication/verify")
    public String verify(@RequestBody String token) {
        String pseudo = service.verify(token);
        if (pseudo == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return pseudo;
    }


    @PostMapping("/authentication")
    public ResponseEntity<Void> createOne(@RequestBody InsecureCredentials credentials) {
        if (credentials.getEmail() == null || credentials.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        boolean created = service.createOne(credentials);
        if (!created) throw new ResponseStatusException(HttpStatus.CONFLICT);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/authentication")
    public void updateOne(@RequestBody InsecureCredentials credentials) {
        if (credentials.getEmail() == null || credentials.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        boolean found = service.updateOne(credentials);
        if (!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/authentication/{email}")
    public void deleteCredentials(@PathVariable String email) {
        boolean found = service.deleteOne(email);
        if (!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
