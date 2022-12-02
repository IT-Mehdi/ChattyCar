package be.vinci.ipl.chattycar.trips;

import be.vinci.ipl.chattycar.trips.data.PositionsProxy;
import be.vinci.ipl.chattycar.trips.data.TripsRepository;
import be.vinci.ipl.chattycar.trips.models.NewTrip;
import be.vinci.ipl.chattycar.trips.models.PositionTrip;
import be.vinci.ipl.chattycar.trips.models.Trip;
import org.springframework.stereotype.Service;

@Service
public class TripsService {

    private final TripsRepository repository;

    private final PositionsProxy positionsProxy;

    public TripsService(TripsRepository repository, PositionsProxy positionsProxy) {
        this.repository = repository;
        this.positionsProxy = positionsProxy;
    }

    /**
     * Creates a newTrip in repository
     * @param newTrip the newTrip to create
     */
    public Trip createOne(NewTrip newTrip) {
        return repository.save(newTrip.toTrip());
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
        if(!driverExist(driverId)) return false;
        repository.deleteByDriverId(driverId);
        return true;
    }

    public boolean driverExist(int driverId) {
        return repository.existsByDriverId(driverId);
    }

    public void updateAvailableSeating(Trip tripUpdated) {
        repository.save(tripUpdated);
    }

    public double calculateDistance(Trip t) {
        return positionsProxy.calculateDistance(t.getOrigin().getLatitude(), t.getOrigin().getLongitude(), t.getDestination().getLatitude(), t.getDestination().getLongitude());
    }



}
