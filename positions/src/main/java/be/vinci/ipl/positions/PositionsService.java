package be.vinci.ipl.positions;

import org.springframework.stereotype.Service;

@Service
public class PositionsService {

    public double calculateDistance(double originLatitude, double originLongitude, double destLatitude, double destLongitude) {
        double lat1 = Math.toRadians(originLatitude);
        double long1 = Math.toRadians(originLongitude);
        double lat2 = Math.toRadians(destLatitude);
        double long2 = Math.toRadians(destLongitude);

        double earthRadius = 6371.01; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(long1 - long2));
    }
}
