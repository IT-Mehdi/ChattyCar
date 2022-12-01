package be.vinci.ipl.inscriptions.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Trip {
  @JsonProperty("id")
  @Column(name = "id")
  private Integer id;
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "latitude", column = @Column(name = "origin_latitude")),
      @AttributeOverride(name = "longitude", column = @Column(name = "origin_longitude")),
  })
  @JsonProperty("origin")
  @Column(name = "origin")
  private Position origin;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "latitude", column = @Column(name = "destination_latitude")),
      @AttributeOverride(name = "longitude", column = @Column(name = "destination_longitude")),
  })
  @JsonProperty("destination")
  @Column(name = "destination")
  private Position destination;

  @JsonProperty("departure")
  @Column(name = "departure")
  private LocalDate departure; //Si on veut utiliser ce param dans une route utiliser notation
  @JsonProperty("driver_id")
  @Column(name = "driver_id")
  private Integer driverId;
  @JsonProperty("available_seating")
  @Column(name = "available_seating")
  private Integer availableSeating;

}
