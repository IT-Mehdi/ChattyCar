package be.vinci.ipl.gateway.models;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String email;
  private String firstname;
  private String lastname;

  public NoIdUser toNoIdUser() {
    return new NoIdUser(email, firstname, lastname);
  }
}
