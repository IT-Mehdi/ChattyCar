package be.vinci.ipl.inscriptions.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;

@Repository
@FeignClient(name = "trips")
public interface TripsProxy {

}
