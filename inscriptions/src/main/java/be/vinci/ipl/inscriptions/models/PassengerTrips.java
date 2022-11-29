package be.vinci.ipl.inscriptions.models;

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
  List<Trip> pending;
  List<Trip> accepted;
  List<Trip> refused;
}
