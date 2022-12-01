package be.vinci.ipl.gateway.models;

import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Passengers {
  List<User> pending;
  List<User> accepted;
  List<User> refused;
}
