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
  @JsonProperty("first_name")
  @Column(name = "first_name")
  private String firstname;
  @JsonProperty("last_name")
  @Column(name = "last_name")
  private String lastname;

}