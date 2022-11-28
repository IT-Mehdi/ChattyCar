package be.vinci.ipl.inscriptions.data;

import be.vinci.ipl.inscriptions.models.Inscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionsRepository extends CrudRepository<Inscription,Long> {

  boolean existsByTripIdAndPassengerId(Integer trip_id, Integer passenger_id);

}
