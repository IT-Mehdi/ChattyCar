package be.vinci.ipl.inscriptions.data;

import be.vinci.ipl.inscriptions.models.Trip;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "trips")
public interface TripsProxy {

  @GetMapping("/trips/{trip_id}")
  Trip getTripById(@PathVariable("trip_id") Integer tripId);

}
