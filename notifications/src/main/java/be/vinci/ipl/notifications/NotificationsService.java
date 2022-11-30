package be.vinci.ipl.notifications;

import be.vinci.ipl.notifications.data.NotificationsRepository;
import be.vinci.ipl.notifications.data.TripsProxy;
import be.vinci.ipl.notifications.data.UsersProxy;
import be.vinci.ipl.notifications.models.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {

    private final NotificationsRepository repository;
    private final UsersProxy usersProxy;
    private final TripsProxy tripsProxy;

    public NotificationsService(NotificationsRepository repository, UsersProxy usersProxy, TripsProxy tripsProxy){
        this.repository = repository;
        this.usersProxy = usersProxy;
        this.tripsProxy = tripsProxy;
    }

    /**
     * Creates a notification
     * @param notification Notification to create
     * @return the notification created
     */
    public Boolean createOne(Notification notification){
        repository.save(notification);
        return true;
    }

    /**
     * Reads all notifications from a specific user in repository
     * @param user the user to search for
     * @return all notifications from this user or false if user doesn't exist
     */
    public Iterable<Notification> readFromUser(int user){
        if (usersProxy.readOne(user) == null) return null;
        return repository.findByUser(user);
    }

    /**
     * Deletes all notifications from a specific user from repository
     * @param user the user to delete the notifications from
     * @return True if successfully deleted, false if user doesn't exist
     */
    public boolean deleteFromUser(int user) {
        if (usersProxy.readOne(user) == null) return false;
        repository.deleteByUser(user);
        return true;
    }

    /**
     * Reads all notifications from a specific trip in repository
     * @param trip the trip to search for
     * @return all notifications frm this trip or null if trip doesn't exist
     */
    public Iterable<Notification> readFromTrip(int trip) {
        if (tripsProxy.readOne(trip) == null) return null;
        return repository.findByTrip(trip);
    }

    /**
     * Deletes all notifications from a specific trip from repository
     * @param trip the trip to delete the notifications from
     * @return True if successfully deleted, false if trip doesn't exist
     */
    public boolean deleteFromTrip(int trip) {
        if (tripsProxy.readOne(trip) == null) return false;
        repository.deleteByTrip(trip);
        return true;
    }
}
