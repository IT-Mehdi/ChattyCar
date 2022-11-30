package be.vinci.ipl.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Inscription {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @JsonProperty("trip_id")
  @Column(name = "trip_id")
  private Integer tripId;

  @JsonProperty("passenger_id")
  @Column(name = "passenger_id")
  private Integer passengerId;

  private String status;
}
