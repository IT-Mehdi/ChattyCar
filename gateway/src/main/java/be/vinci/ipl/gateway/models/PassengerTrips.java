package be.vinci.ipl.gateway.models;

import java.util.List;
import lombok.*;

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
