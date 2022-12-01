package be.vinci.ipl.chattycar.trips;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    @JsonProperty("driver_id")
    @Column(name =  "driver_id")
    private int driverId;

    @JsonProperty("available_seating")
    @Column(name = "available_seating")
    private int availableSeating;
}
