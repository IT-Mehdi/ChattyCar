package be.vinci.ipl.notifications;

import org.springframework.stereotype.Service;

@Service
public class NotificationsService {

    private final NotificationsRepository repository;

    public NotificationsService(NotificationsRepository repository){
        this.repository = repository;
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
     * @return all notifications from this user
     */
    public Iterable<Notification> readFromUser(long user){
        return repository.findByUser(user);
    }

    /**
     * Deletes all notifications from a specific user from repository
     * @param user the user to delete the notifications from
     */
    public void deleteFromUser(long user) {
        repository.deleteByUser(user);
    }

    /**
     * Reads all notifications from a specific trip in repository
     * @param trip the trip to search for
     * @return all notifications frm this trip
     */
    public Iterable<Notification> readFromTrip(long trip) {
        return repository.findByTrip(trip);
    }

    /**
     * Deletes all notifications from a specific trip from repository
     * @param trip the trip to delete the notifications from
     */
    public void deleteFromTrip(long trip) {
        repository.deleteByTrip(trip);
    }
}
