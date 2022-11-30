package be.vinci.ipl.inscriptions.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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
  @JsonProperty("latitude")
  @Column(name = "latitude")
  private double latitude;
@JsonProperty("longitude")
  @Column(name = "longitude")
  private double longitude;
}
