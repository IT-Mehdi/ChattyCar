package be.vinci.ipl.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;

public class Notification {

  @JsonProperty("user_id")
  @Column(name = "user_id")
  private int userId;

  @JsonProperty("trip_id")
  @Column(name = "trip_id")
  private int tripId;
  private String date;

  @JsonProperty("notification_text")
  @Column(name = "notification_text")
  private String notificationText;


}
