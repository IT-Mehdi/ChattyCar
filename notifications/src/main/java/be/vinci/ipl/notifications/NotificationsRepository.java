package be.vinci.ipl.notifications;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface NotificationsRepository extends CrudRepository<Notification, Long>{
    Iterable<Notification> findByUser(long id);

    @Transactional
    void deleteByUser(long user);

    Iterable<Notification> findByTrip(long id);

    @Transactional
    void deleteByTrip(long trip);
}
