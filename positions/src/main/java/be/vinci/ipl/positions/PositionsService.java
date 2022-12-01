package be.vinci.ipl.positions;

import org.springframework.stereotype.Service;

@Service
public class PositionsService {

    public double calculateDistance(Trip trip) {
        double lat1 = Math.toRadians(trip.getOrigin().getLatitude());
        double long1 = Math.toRadians(trip.getOrigin().getLongitude());
        double lat2 = Math.toRadians(trip.getDestination().getLatitude());
        double long2 = Math.toRadians(trip.getDestination().getLongitude());

        double earthRadius = 6371.01; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(long1 - long2));
    }
}
