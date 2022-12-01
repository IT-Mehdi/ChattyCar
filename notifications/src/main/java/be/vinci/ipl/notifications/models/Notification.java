package be.vinci.ipl.notifications.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "notifications")
public class Notification {
    @Id
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private int userId;
    @JsonProperty("trip_id")
    @Column(name = "trip_id")
    private int tripId;
    private String date;
    private String notification_text;
}
