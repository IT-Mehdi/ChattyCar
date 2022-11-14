package be.vinci.ipl.chattycar.trips;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripsRepository extends CrudRepository<Trip, Integer> {

    //On peut ajouter des fonctions qui ne sont pas liés à l'id ici
    //@Transactionnal pour les fonctions DELETE
}
