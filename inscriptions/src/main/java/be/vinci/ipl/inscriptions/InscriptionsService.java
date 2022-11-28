package be.vinci.ipl.inscriptions;

import be.vinci.ipl.inscriptions.data.InscriptionsRepository;
import be.vinci.ipl.inscriptions.models.Inscription;
import org.springframework.stereotype.Service;

@Service
public class InscriptionsService {

      private final InscriptionsRepository repository;

      public InscriptionsService(InscriptionsRepository repository) {
          this.repository = repository;
      }

      public Inscription createInscription(Integer trip_id, Integer user_id) {
          if(repository.existsByTripIdAndPassengerId(trip_id,user_id)){
              return null;
          }
          Inscription inscription = new Inscription(0L, trip_id, user_id, "pending");
          return repository.save(inscription);
      }

}
