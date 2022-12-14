package be.vinci.ipl.users.data;

import be.vinci.ipl.users.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Integer>{
    boolean existsByEmail(String email);

    User findByEmail(String email);
}
