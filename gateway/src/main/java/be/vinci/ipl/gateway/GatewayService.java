package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.data.AuthenticationProxy;
import be.vinci.ipl.gateway.data.UsersProxy;
import be.vinci.ipl.gateway.models.Credentials;
import be.vinci.ipl.gateway.models.User;
import be.vinci.ipl.gateway.models.UserWithCredentials;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {

  private final AuthenticationProxy authenticationProxy;
  private final UsersProxy usersProxy;

  public GatewayService(AuthenticationProxy authenticationProxy, UsersProxy usersProxy) {
    this.authenticationProxy = authenticationProxy;
    this.usersProxy = usersProxy;
  }

  public String connect(Credentials credentials) {
    return authenticationProxy.connect(credentials);
  }

  public String verify(String token) {
    return authenticationProxy.verify(token);
  }
  public void createUser(UserWithCredentials user) {
    usersProxy.createUser(user.getEmail(), user.toUser());
    authenticationProxy.createCredentials(user.getEmail(), user.toCredentials());
  }


}
