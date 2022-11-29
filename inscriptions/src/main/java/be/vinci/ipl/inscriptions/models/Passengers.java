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
public class Passengers {
  List<User> pending;
  List<User> accepted;
  List<User> refused;
}
