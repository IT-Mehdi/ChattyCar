package be.vinci.ipl.inscriptions.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @JsonProperty("id")
  @Column(name = "id")
  private Integer id;
  @JsonProperty("email")
  @Column(name = "email")
  private String email;
  @JsonProperty("firstname")
  @Column(name = "firstname")
  private String firstname;
  @JsonProperty("lastname")
  @Column(name = "lastname")
  private String lastname;

}