package be.vinci.ipl.inscriptions.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;

@Repository
@FeignClient(name = "users")
public interface UsersProxy {



}
