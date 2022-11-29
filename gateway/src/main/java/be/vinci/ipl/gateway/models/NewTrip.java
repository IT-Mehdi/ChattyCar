package be.vinci.ipl.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewTrip {

  private Position origin;
  private Position destination;
  private String departure;
  private int driver_id;
  private int available_seating;

}
