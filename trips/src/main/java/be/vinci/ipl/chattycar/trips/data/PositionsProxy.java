package be.vinci.ipl.chattycar.trips.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
@FeignClient(name = "positions")
public interface PositionsProxy {

    @GetMapping("/trips/position")
    double calculateDistance(@RequestParam(value = "originLatitude") double originLatitude,
                             @RequestParam(value = "originLongitude") double originLongitude,
                             @RequestParam(value = "destLatitude") double destLatitude,
                             @RequestParam(value = "destLongitude") double destLongitude);
}
