package be.vinci.ipl.users.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NoIdUser {
    private String email;
    private String firstname;
    private String lastname;


    public User toUser() {
        return new User(0, email, firstname, lastname);
    }
}