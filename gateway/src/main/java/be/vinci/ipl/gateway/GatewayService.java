package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.data.AuthenticationProxy;
import be.vinci.ipl.gateway.data.TripsProxy;
import be.vinci.ipl.gateway.data.UsersProxy;
import be.vinci.ipl.gateway.models.Credentials;
import be.vinci.ipl.gateway.models.User;
import be.vinci.ipl.gateway.models.NewUser;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class GatewayService {

  private final AuthenticationProxy authenticationProxy;
  private final UsersProxy usersProxy;
  private final TripsProxy tripsProxy;

  public GatewayService(AuthenticationProxy authenticationProxy, UsersProxy usersProxy,
      TripsProxy tripsProxy) {
    this.authenticationProxy = authenticationProxy;
    this.usersProxy = usersProxy;
    this.tripsProxy = tripsProxy;
  }

  public String connect(Credentials credentials) {
    return authenticationProxy.connect(credentials);
  }

  public String verify(String token) {
    return authenticationProxy.verify(token);
  }

  public User createUser(NewUser user) {
    authenticationProxy.createCredentials(user.toCredentials());
//    return usersProxy.createUser(user.toUser());
    return new User(0,user.getEmail(), user.getFirstname(), user.getLastname());
  }
  public User readUserByEmail(String email){
//    return usersProxy.readUserByEmail(email);
    return new User(0,email, "user.getFirstname()", "user.getLastname()");
  }

  public User readUserById(int id){
//    return usersProxy.readUserById(id);
    return new User(id,"email", "user.getFirstname()", "user.getLastname()");
  }

  public void updateCredentials(Credentials credentials){
     authenticationProxy.updateCredentials(credentials);
  }

  public void updateUser(int id, User user){
//    usersProxy.updateUser(id , user);
  }

  void deleteUser(@PathVariable int id){
//    tripsProxy.deleteTripsByDriver(id);
    usersProxy.deleteUser(id);
  }


}
