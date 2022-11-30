package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.Notification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "notifications")
public interface NotificationProxy {

  @PostMapping("/notifications")
  Notification createNotification(@RequestBody Notification notification);

  @GetMapping("/notifications/users/{id}")
  Iterable<Notification> readUserNotifications(@PathVariable int id);

  @DeleteMapping("/notifications/users/{id}")
  void deleteAllNotificationsUser(@PathVariable int id);

  @GetMapping("/notifications/trips/{id}")
  Iterable<Notification> readTripNotifications(@PathVariable int id);

  @DeleteMapping("notifications/trips/{id}")
  void deleteAllNotificationsTrip(@PathVariable int id);
}
