package be.vinci.ipl.gateway.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserWithCredentials {

  private String email;
  private String firstname;
  private String lastname;
  private String password;

  public User toUser() {
    return new User(0,email, firstname, lastname);
  }

  public NoIdUser toNoIdUser() {return new NoIdUser(email, firstname, lastname);}
  public Credentials toCredentials() {
    return new Credentials(email, password);
  }
}
