package be.vinci.ipl.notifications.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "notifications")
public class Notification {
    @Id
    private int user_id;
    private int trip_id;
    private String date;
    private String notification_text;
}
