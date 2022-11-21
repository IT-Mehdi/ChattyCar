package be.vinci.ipl.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private String email;
  private String firstname;
  private String lastname;
}
