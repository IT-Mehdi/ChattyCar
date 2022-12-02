package be.vinci.ipl.chattycar.trips.data;

import be.vinci.ipl.chattycar.trips.models.Trip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TripsRepository extends CrudRepository<Trip, Integer> {

    Iterable<Trip> findByDriverId(int driverId);

    @Transactional
    boolean deleteByDriverId(int driverId);

    boolean existsByDriverId(int integer);
}
