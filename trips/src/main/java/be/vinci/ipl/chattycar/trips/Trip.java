package be.vinci.ipl.chattycar.trips;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "trips")
public class Trip {

    @Id
    private int id;
    @Embedded
    private Position origin;
    @Embedded
    private Position destination;
    private LocalDate departure; //Si on veut utiliser ce param dans une route utilsier notation
    private int driver_id;
    private int available_seating;
}
