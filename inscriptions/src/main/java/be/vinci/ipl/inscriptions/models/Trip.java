package be.vinci.ipl.inscriptions.models;

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

  private int id;
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

  private LocalDate departure; //Si on veut utiliser ce param dans une route utilsier notation
  private int driverId;
  private int availableSeating;

}
