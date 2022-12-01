package be.vinci.ipl.notifications.data;

import be.vinci.ipl.notifications.models.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface NotificationsRepository extends CrudRepository<Notification, Integer>{
    Iterable<Notification> findByUserId(int id);

    @Transactional
    void deleteByUserId(int user);

    Iterable<Notification> findByTripId(int id);

    @Transactional
    void deleteByTripId(int trip);
}
