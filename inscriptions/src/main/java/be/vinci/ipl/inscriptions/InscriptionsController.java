package be.vinci.ipl.inscriptions;

import be.vinci.ipl.inscriptions.models.Inscription;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class InscriptionsController {
  private final InscriptionsService service;

  public InscriptionsController(InscriptionsService service){
    this.service = service;
  }

  @PostMapping("/inscriptions/{tripId}/{userId}")
  public ResponseEntity<Inscription> createOne(@PathVariable Integer userId,@PathVariable Integer tripId) {
    if (userId == null || tripId == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    Inscription inscription = service.createInscription(userId, tripId);
    if (inscription == null) throw new ResponseStatusException(HttpStatus.CONFLICT);
    return new ResponseEntity<>(inscription, HttpStatus.CREATED);
  }

}
