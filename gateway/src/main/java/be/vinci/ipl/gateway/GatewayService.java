package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.data.AuthenticationProxy;
import be.vinci.ipl.gateway.data.InscriptionProxy;
import be.vinci.ipl.gateway.data.NotificationProxy;
import be.vinci.ipl.gateway.data.TripsProxy;
import be.vinci.ipl.gateway.data.UsersProxy;
import be.vinci.ipl.gateway.models.Credentials;
import be.vinci.ipl.gateway.models.NewTrip;
import be.vinci.ipl.gateway.models.Notification;
import be.vinci.ipl.gateway.models.PassengerTrips;
import be.vinci.ipl.gateway.models.Passengers;
import be.vinci.ipl.gateway.models.Trip;
import be.vinci.ipl.gateway.models.User;
import be.vinci.ipl.gateway.models.NewUser;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class GatewayService {

  private final AuthenticationProxy authenticationProxy;
  private final UsersProxy usersProxy;
  private final TripsProxy tripsProxy;
  private final InscriptionProxy inscriptionProxy;
  private final NotificationProxy notificationProxy;

  public GatewayService(AuthenticationProxy authenticationProxy, UsersProxy usersProxy,
      TripsProxy tripsProxy, InscriptionProxy inscriptionProxy, NotificationProxy notificationProxy) {
    this.authenticationProxy = authenticationProxy;
    this.usersProxy = usersProxy;
    this.tripsProxy = tripsProxy;
    this.inscriptionProxy = inscriptionProxy;
    this.notificationProxy = notificationProxy;
  }

  public String connect(Credentials credentials) {
    return authenticationProxy.connect(credentials);
  }

  public String verify(String token) {
    return authenticationProxy.verify(token);
  }

  public User createUser(NewUser user) {
    authenticationProxy.createCredentials(user.toCredentials());
    return usersProxy.createUser(user.toNoIdUser());
  }
  public User readUserByEmail(String email){
    return usersProxy.readUserByEmail(email);
  }

  public User readUserById(int id){
    return usersProxy.readUserById(id);
  }

  public void updateCredentials(Credentials credentials){
     authenticationProxy.updateCredentials(credentials);
  }

  public void updateUser(int id, User user){
    usersProxy.updateUser(id , user);
  }

  public void deleteUser(@PathVariable int id){
    tripsProxy.deleteTripsByDriver(id);
    User userEmail = readUserById(id);
    authenticationProxy.deleteCredentials(userEmail.getEmail());
    usersProxy.deleteUser(id);
  }

  public Iterable<Trip> readTripsByDriver(int id){
    return tripsProxy.readTripsByDriver(id);
  }

  public PassengerTrips readTripsByPassenger(int id){
    return inscriptionProxy.readAllTripsPassenger(id);
  }

  public Trip createTrip(NewTrip newTrip){
    return tripsProxy.createTrip(newTrip);
  }

  public Iterable<Trip> readTrips(String departure_date, double originLat, double originLon,
      double destinationLat, double destinationLon){
    return tripsProxy.readTrips( departure_date,  originLat,  originLon, destinationLat, destinationLon);
  }

  public Trip readTripById(int id){
    return tripsProxy.readTripById(id);
  }

  public void deleteTrip(int id){
    tripsProxy.deleteTripById(id);
  }

  public Passengers readAllPassengersTrip(int id){
    return inscriptionProxy.readAllPassengersTrip(id);
  }

  public String readPassengerStatus(int idTrip, int idPassenger){
    return inscriptionProxy.readInscription(idTrip, idPassenger);
  }
  public void createInscription(int tripId, int passengerId){
    inscriptionProxy.createInscription(tripId, passengerId);
  }

  public void updatePassengerStatus(int idTrip, int idPassenger, String status){
    inscriptionProxy.updateInscription(idTrip, idPassenger, status);
  }

  public void deletePassenger(int idTrip, int idPassenger){
    inscriptionProxy.deleteInscription(idTrip, idPassenger);
  }

  public Iterable<Notification> readUserNotifications(int id){
    return notificationProxy.readUserNotifications(id);
  }

  public void deleteUserNotifications(int id){
    notificationProxy.deleteUserNotifications(id);
  }
}
