package be.vinci.ipl.inscriptions.data;

import be.vinci.ipl.inscriptions.models.Inscription;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionsRepository extends CrudRepository<Inscription,Long> {

  boolean existsByTripIdAndPassengerId(Integer trip_id, Integer passenger_id);

  Inscription findByTripIdAndPassengerId(Integer trip_id, Integer passenger_id);

  List<Inscription> getAllInscriptionsByPassengerId(Integer passenger_id);

  List<Inscription> getAllInscriptionsByTripId(Integer trip_id);

}
