package be.vinci.ipl.gateway.models;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Inscription {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer tripId;
  private Integer passengerId;
  private String status;
}
