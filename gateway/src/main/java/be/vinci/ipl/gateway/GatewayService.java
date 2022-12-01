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
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException.NotFound;

@Service
public class GatewayService {

  private final AuthenticationProxy authenticationProxy;
  private final UsersProxy usersProxy;
  private final TripsProxy tripsProxy;
  private final InscriptionProxy inscriptionProxy;
  private final NotificationProxy notificationProxy;

  public GatewayService(AuthenticationProxy authenticationProxy, UsersProxy usersProxy,
      TripsProxy tripsProxy, InscriptionProxy inscriptionProxy,
      NotificationProxy notificationProxy) {
    this.authenticationProxy = authenticationProxy;
    this.usersProxy = usersProxy;
    this.tripsProxy = tripsProxy;
    this.inscriptionProxy = inscriptionProxy;
    this.notificationProxy = notificationProxy;
  }

  /**
   * Connects the user to the application.
   *
   * @param credentials the credentials of the user.
   * @return the user's token
   */
  public String connect(Credentials credentials) {
    return authenticationProxy.connect(credentials);
  }

  /**
   * Verify if the token is valid and send the user's information.
   *
   * @param token the user's token
   * @return the user's email
   */
  public String verify(String token) {
    return authenticationProxy.verify(token);
  }

  /**
   * Create a new user.
   *
   * @param user the user's information
   * @return the user
   */
  public User createUser(NewUser user) {
    authenticationProxy.createCredentials(user.toCredentials());
    return usersProxy.createUser(user.toNoIdUser());
  }

  /**
   * Get the user's information by email & send the user's information.
   *
   * @param email the user's email
   * @return the user
   */
  public User readUserByEmail(String email) {
    return usersProxy.readUserByEmail(email);
  }

  /**
   * Get the user's information by id & send the user's information.
   *
   * @param id the user's id
   * @return the user
   */
  public User readUserById(int id) {
    return usersProxy.readUserById(id);
  }

  /**
   * Update the user's password.
   *
   * @param credentials the user's information
   */
  public void updateCredentials(Credentials credentials) {
    authenticationProxy.updateCredentials(credentials);
  }

  /**
   * Update the user's information.
   *
   * @param user the user's information
   * @param id   the user's id
   */
  public void updateUser(int id, User user) {
    usersProxy.updateUser(id, user);
  }

  /**
   * Delete the user's account.
   *
   * @param id the user's id
   */
  public void deleteUser(@PathVariable int id) {
    Iterable<Trip> trip = tripsProxy.readTripsByDriver(id);
    for (Trip t : trip) {
      inscriptionProxy.deleteAllPassengersTrip(t.getId());
      notificationProxy.deleteTripNotification(t.getId());
    }
    tripsProxy.deleteTripsByDriver(id);
    notificationProxy.deleteUserNotifications(id);
    User userEmail = readUserById(id);
    authenticationProxy.deleteCredentials(userEmail.getEmail());
    usersProxy.deleteUser(id);
  }

  /**
   * Get the driver's trips.
   *
   * @param id the driver's id
   * @return the driver's trips
   */
  public Iterable<Trip> readTripsByDriver(int id) {
    return tripsProxy.readTripsByDriver(id);
  }

  /**
   * Get the passenger's trips.
   *
   * @param id the passenger's id
   * @return the passenger's trips
   */
  public PassengerTrips readTripsByPassenger(int id) {
    return inscriptionProxy.readAllTripsPassenger(id);
  }

  /**
   * Create a new trip.
   *
   * @param newTrip the trip's information
   * @return the trip
   */
  public Trip createTrip(NewTrip newTrip) {
    return tripsProxy.createTrip(newTrip);
  }

  /**
   * Get Trips by optional parameters.
   *
   * @param departureDate  the departure date
   * @param originLat      the origin latitude
   * @param originLon      the origin longitude
   * @param destinationLat the destination latitude
   * @param destinationLon the destination longitude
   * @return the trips
   */
  public Iterable<Trip> readTrips(String departureDate, Double originLat, Double originLon,
      Double destinationLat, Double destinationLon) {
    return tripsProxy.readTrips(departureDate, originLat, originLon, destinationLat,
        destinationLon);
  }

  /**
   * Get the trip's information by id & send the trip's information.
   *
   * @param id the trip's id
   * @return the trip
   */
  public Trip readTripById(int id) {
    return tripsProxy.readTripById(id);
  }

  /**
   * Delete the trip.
   *
   * @param tripId the trip's id
   */
  public void deleteTrip(int tripId) {
    try {
      inscriptionProxy.deleteAllPassengersTrip(tripId);
    }catch (Exception ignored){

    }
    tripsProxy.deleteTripById(tripId);
  }

  /**
   * Get the passengers of the trip.
   *
   * @param id the trip's id
   * @return the passengers in pending, accepted and refused status
   */
  public Passengers readAllPassengersTrip(int id) {
    return inscriptionProxy.readAllPassengersTrip(id);
  }

  /**
   * Get the passenger status.
   *
   * @param idTrip      the trip's id
   * @param idPassenger the passenger's id
   * @return the passenger status
   */
  public String readPassengerStatus(int idTrip, int idPassenger) {
    return inscriptionProxy.readInscription(idTrip, idPassenger);
  }

  /**
   * Add a passenger in a trip.
   *
   * @param tripId      the trip's id
   * @param passengerId the passenger's id
   */
  public void createInscription(int tripId, int passengerId) {
    Trip trip = tripsProxy.readTripById(tripId);
    notificationProxy.createNotification(
        new Notification(trip.getDriverId(), tripId, LocalDate.now().toString(),
            "Un passager s'est inscrit à votre voyage "));
    inscriptionProxy.createInscription(tripId, passengerId);
    decreaseNumberOfAvailableSeat(tripId);
  }

  /**
   * Update the passenger status.
   *
   * @param tripId      the trip's id
   * @param passengerId the passenger's id
   * @param status      the new status passenger
   */
  public void updatePassengerStatus(int tripId, int passengerId, String status) {
    String notificationText = "Votre demande à été acceptée";
    if (status.equals("refused")) {
      notificationText = "Votre demande à été refusée";
    }
    notificationProxy.createNotification(
        new Notification(passengerId, tripId, LocalDate.now().toString(), notificationText));
    inscriptionProxy.updateInscription(tripId, passengerId, status);
  }

  /**
   * Delete the passenger from the trip.
   *
   * @param idTrip      the trip's id
   * @param idPassenger the passenger's id
   */
  public void deletePassenger(int idTrip, int idPassenger) {
    inscriptionProxy.deleteInscription(idTrip, idPassenger);
  }

  /**
   * Get the notifications of the user.
   *
   * @param id the user's id
   * @return the notifications
   */
  public Iterable<Notification> readUserNotifications(int id) {
    return notificationProxy.readUserNotifications(id);
  }

  /**
   * Delete the user notifications.
   *
   * @param id the user's id
   */
  public void deleteUserNotifications(int id) {
    notificationProxy.deleteUserNotifications(id);
  }

  /**
   * Decrease the number of seats of the trip.
   *
   * @param tripId the trip's id
   */
  public void decreaseNumberOfAvailableSeat(int tripId) {
    tripsProxy.decreaseNumberOfAvailableSeat(tripId);
  }
}
