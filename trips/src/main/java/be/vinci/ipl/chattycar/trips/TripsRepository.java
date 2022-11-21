package be.vinci.ipl.chattycar.trips;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TripsRepository extends CrudRepository<Trip, Integer> {

    //On peut ajouter des fonctions qui ne sont pas liés à l'id ici
    //@Transactionnal pour les fonctions DELETE

    Iterable<Trip> findByDriverId(int driverId);

    @Transactional
    boolean deleteByDriverId(int driverId);


}
