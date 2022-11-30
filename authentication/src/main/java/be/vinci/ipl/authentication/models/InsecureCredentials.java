package be.vinci.ipl.authentication.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsecureCredentials {
    @JsonProperty("email")
    @Column(name = "email")
    private String email;
    @JsonProperty("password")
    @Column(name = "hashed_password")
    private String password;

    public Credentials toCredentials(String hashedPassword) {
        return new Credentials(email, hashedPassword);
    }
}
