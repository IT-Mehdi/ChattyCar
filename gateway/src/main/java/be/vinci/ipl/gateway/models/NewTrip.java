package be.vinci.ipl.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewTrip {

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "latitude", column = @Column(name = "origin_latitude")),
      @AttributeOverride(name = "longitude", column = @Column(name = "origin_longitude")),
  })
  private Position origin;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "latitude", column = @Column(name = "destination_latitude")),
      @AttributeOverride(name = "longitude", column = @Column(name = "destination_longitude")),
  })
  private Position destination;
  private String departure;

  @JsonProperty("driver_id")
  @Column(name = "driver_id")
  private int driverId;
  @JsonProperty("available_seating")
  @Column(name = "available_seating")
  private int availableSeating;

}
