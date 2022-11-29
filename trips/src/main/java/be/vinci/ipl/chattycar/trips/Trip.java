package be.vinci.ipl.chattycar.trips;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "origin_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "origin_longitude")),
    })
    private Position origin;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "destination_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "destination_longitude")),
    })
    private Position destination;

    private LocalDate departure; //Si on veut utiliser ce param dans une route utilsier notation
    private int driverId;
    private int availableSeating;
}
