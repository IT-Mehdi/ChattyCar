package be.vinci.ipl.notifications.data;

import be.vinci.ipl.notifications.models.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface NotificationsRepository extends CrudRepository<Notification, Integer>{
    Iterable<Notification> findByUser(int id);

    @Transactional
    void deleteByUser(int user);

    Iterable<Notification> findByTrip(int id);

    @Transactional
    void deleteByTrip(int trip);
}
