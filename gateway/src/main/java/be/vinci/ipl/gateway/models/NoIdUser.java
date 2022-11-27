package be.vinci.ipl.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoIdUser {
  private String email;
  private String firstname;
  private String lastname;

  public User toUser() {
    return new User(0, email, firstname, lastname);
  }
}
