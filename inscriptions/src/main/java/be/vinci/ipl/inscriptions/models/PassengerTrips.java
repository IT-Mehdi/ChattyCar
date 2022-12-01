package be.vinci.ipl.inscriptions.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PassengerTrips {
  @JsonProperty("pending")
  @Column(name = "pending")
  List<Trip> pending;
  @JsonProperty("accepted")
  @Column(name = "accepted")
  List<Trip> accepted;
  @JsonProperty("refused")
  @Column(name = "refused")
  List<Trip> refused;
}
