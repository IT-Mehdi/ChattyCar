package be.vinci.ipl.chattycar.trips.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Embeddable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Embeddable
public class Position {

    private double latitude;
    private double longitude;
}
