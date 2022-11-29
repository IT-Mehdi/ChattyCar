package be.vinci.ipl.notifications;

import be.vinci.ipl.notifications.models.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class NotificationsController {
    private final NotificationsService service;

    public NotificationsController(NotificationsService service) {
        this.service = service;
    }

    @PostMapping("/notifications")
    public ResponseEntity<Notification> createOne(@RequestBody Notification notification){
        if (notification.getUser_id() <0  || notification.getTrip_id() < 0 || notification.getDate() == null
        || notification.getNotification_text() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        boolean created = service.createOne(notification);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/notifications/users/{id}")
    public Iterable<Notification> readFromUser(@PathVariable int id){
        Iterable<Notification> notifications = service.readFromUser(id);
        if (notifications == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return notifications;
    }

    @DeleteMapping("/notifications/users/{id}")
    public void deleteFromUser(@PathVariable int id){
        Boolean ok = service.deleteFromUser(id);
        if (!ok) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/notifications/trips/{id}")
    public Iterable<Notification> readFromTrip(@PathVariable int id){
        Iterable<Notification> notifications = service.readFromTrip(id);
        if (notifications == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return notifications;
    }

    @DeleteMapping("/notifications/trips/{id}")
    public void deleteFromTrip(@PathVariable int id){
        Boolean ok = service.deleteFromTrip(id);
        if (!ok) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


}
