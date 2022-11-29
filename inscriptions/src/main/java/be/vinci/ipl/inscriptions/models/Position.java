package be.vinci.ipl.inscriptions.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Embeddable
public class Position {

  private double latitude;
  private double longitude;
}
