package be.vinci.ipl.inscriptions.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity(name="inscriptions")
public class Inscription {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("id")
  @Column(name = "id")
  private Integer id;
  @JsonProperty("trip_id")
  @Column(name = "trip_id")
  private Integer tripId;
  @JsonProperty("passenger_id")
  @Column(name = "passenger_id")
  private Integer passengerId;
  @JsonProperty("status")
  @Column(name = "status")
  private String status;
}
