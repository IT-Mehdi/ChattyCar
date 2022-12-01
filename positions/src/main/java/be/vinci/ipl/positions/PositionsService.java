package be.vinci.ipl.positions;

import org.springframework.stereotype.Service;

@Service
public class PositionsService {

    public double calculateDistance(double originLatitude, double originLongitude, double destLatitude, double destLongitude) {

        double earthRadius = 6371.01; //Kilometers
        return earthRadius * Math.acos(Math.sin(originLatitude)*Math.sin(destLatitude) + Math.cos(originLatitude)*Math.cos(destLatitude)*Math.cos(originLongitude - destLongitude));
    }
}
