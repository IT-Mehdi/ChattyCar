package be.vinci.ipl.chattycar.trips;

import org.springframework.stereotype.Service;

@Service
public class TripsService {

    private final TripsRepository repository;

    public TripsService(TripsRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a trip in repository
     * @param trip the trip to create
     * @return True if the trip could be created, false if it already existed
     */
    public boolean createOne(Trip trip) {
        if (repository.existsById(trip.getId())) return false;
        repository.save(trip);
        return true;
    }

    /**
     * Reads all trips in repository
     * @return all trips
     */
    public Iterable<Trip> readAll() {
        return repository.findAll();
    }

    /**
     * Reads a trip in repository
     * @param id the id to search for
     * @return The trip, or null if it couldn't be found
     */
    public Trip readOne(int id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Deletes a trip from repository
     * @param id the id of the trip
     * @return True if the trip could be deleted, false if it couldn't be found
     */
    public boolean deleteOne(int id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    /**
     * Read all trips of the driver
     * @param driverId the id of the driver
     * @return list of trips of the driver
     */
    public Iterable<Trip> readAllTripOfTheDriver(int driverId) {
        return repository.findByDriverId(driverId);
    }

    /**
     * Delete all trips of the drivers
     * @param driverId the id of the driver
     * @return True if trips are deleted, false if the driver couldn't be found
     */
    public boolean deleteByDriverId(int driverId) {
        if(!repository.existsById(driverId)) return false;
        repository.deleteByDriverId(driverId);
        return true;
    }



}
