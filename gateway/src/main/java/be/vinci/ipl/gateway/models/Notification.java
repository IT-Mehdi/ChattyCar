package be.vinci.ipl.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notification {

  @JsonProperty("user_id")
  @Column(name = "user_id")
  private Integer userId;

  @JsonProperty("trip_id")
  @Column(name = "trip_id")
  private int tripId;
  private String date;

  @JsonProperty("notification_text")
  @Column(name = "notification_text")
  private String notificationText;


}
