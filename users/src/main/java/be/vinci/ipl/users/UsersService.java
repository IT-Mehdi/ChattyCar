package be.vinci.ipl.users;

import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository repository;

    public UsersService(UsersRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a user
     * @param noIdUser User to create
     * @return the user created with its id, or null if it already existed
     */
    public User createOne(NoIdUser noIdUser) {
        if (repository.existsByEmail(noIdUser.getEmail())) return null;
        return repository.save(noIdUser.toUser());

    }

    /**
     * Reads a user
     * @param email Email of the user
     * @return The user found, or null if the user couldn't be found
     */
    public User readOneEmail(String email) {
        if (repository.findByEmail(email) == null){
            return null;
        }
        return repository.findByEmail(email);
    }

    /**
     * Reads a user in repository
     * @param id ID of the user
     * @return the user, or null if it couldn't be found
     */
    public User readOneId(int id){
        return repository.findById(id).orElse(null);
    }

    /**
     * Updates a user in repository
     * @param user New values of the user
     * @return True if the user was updated, or null if it couldn't be found
     */
    public boolean updateOne(User user){
        if (!repository.existsById(user.getId())) return false;
        repository.save(user);
        return true;
    }

    /**
     *  Deletes a user from repository
     * @param id ID of the user
     * @return True if the user was deleted, false if it couldn't be found
     */
    public boolean deleteOne(int id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
