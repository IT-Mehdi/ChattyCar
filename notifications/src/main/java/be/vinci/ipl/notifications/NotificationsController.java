package be.vinci.ipl.notifications;

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
    public Iterable<Notification> readFrom(@PathVariable long id){
        return service.readFromUser(id);
    }

    @DeleteMapping("/notifications/users/{id}")
    public void deleteFromUser(@PathVariable long id){
        service.deleteFromUser(id);
    }

    @GetMapping("/notifications/trips/{id}")
    public Iterable<Notification> readFrom(@PathVariable long id){
        return service.readFromTrip(id);
    }

    @DeleteMapping("/notifications/trips/{id}")
    public void deleteFromUser(@PathVariable long id){
        service.deleteFromUser(id);
    }


}
