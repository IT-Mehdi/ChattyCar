package be.vinci.ipl.chattycar.trips;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Embeddable
public class Position {

    private double latitude;
    private double longitude;
}
