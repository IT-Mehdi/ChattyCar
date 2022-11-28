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
     * @param user the user to delete for
     */
    public void deleteFromUser(long user) {
        repository.deleteByUser(user);
    }
}
