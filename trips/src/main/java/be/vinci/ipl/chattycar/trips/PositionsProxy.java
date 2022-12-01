package be.vinci.ipl.chattycar.trips;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "positions")
public interface PositionsProxy {

    @PostMapping("/trips/position")
    double calculateDistance(@RequestBody Trip trip);
}
