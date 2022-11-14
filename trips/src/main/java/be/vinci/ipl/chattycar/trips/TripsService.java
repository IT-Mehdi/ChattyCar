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

}
