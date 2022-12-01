package be.vinci.ipl.authentication.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "credentials")
public class Credentials {
    @Id
    @Column(name = "email")
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    @Column(name = "hashed_password")
    private String hashedPassword;
}
