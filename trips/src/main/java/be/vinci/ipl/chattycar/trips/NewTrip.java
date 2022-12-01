package be.vinci.ipl.chattycar.trips;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class NewTrip {

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
    private int driverId;

    @JsonProperty("available_seating")
    private int availableSeating;

    public Trip toTrip() {
        return new Trip(0, origin, destination, departure, driverId, availableSeating);
    }
}
