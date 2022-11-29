package be.vinci.ipl.inscriptions;

import be.vinci.ipl.inscriptions.data.InscriptionsRepository;
import be.vinci.ipl.inscriptions.data.TripsProxy;
import be.vinci.ipl.inscriptions.data.UsersProxy;
import be.vinci.ipl.inscriptions.models.Inscription;
import be.vinci.ipl.inscriptions.models.Passengers;
import be.vinci.ipl.inscriptions.models.Trip;
import be.vinci.ipl.inscriptions.models.PassengerTrips;
import be.vinci.ipl.inscriptions.models.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InscriptionsService {

      private final InscriptionsRepository repository;
      private final TripsProxy tripsProxy;
      private final UsersProxy usersProxy;

      public InscriptionsService(InscriptionsRepository repository,TripsProxy tripsProxy, UsersProxy usersProxy) {
            this.repository = repository;
            this.tripsProxy = tripsProxy;
            this.usersProxy = usersProxy;
      }

      public Passengers getAllPassengersOfATrip(Integer tripId){
          List<Inscription> inscriptions = repository.getAllInscriptionsByTripId(tripId);
          if(inscriptions.isEmpty()){
              return null;
          }
          List<User> accepted = new ArrayList<>();
          List<User> refused = new ArrayList<>();
          List<User> pending = new ArrayList<>();
          for(Inscription inscription : inscriptions){
              if(inscription.getStatus().equals("accepted")){
                  accepted.add(usersProxy.getUserById(inscription.getPassengerId()));
              }else if(inscription.getStatus().equals("refused")){
                  refused.add(usersProxy.getUserById(inscription.getPassengerId()));
              }else{
                  pending.add(usersProxy.getUserById(inscription.getPassengerId()));
              }
          }
          return new Passengers(pending,accepted,refused);
      }

      public boolean deleteAllInscriptionOfATrip(Integer tripId){
          List<Inscription> inscriptions = repository.getAllInscriptionsByTripId(tripId);
          if(inscriptions.isEmpty()){
              return false;
          }
          repository.deleteAll(inscriptions);
          return true;
      }

      public PassengerTrips getAllInscriptionsOfAPassenger(Integer passengerId) {
        List<Inscription> inscriptions = repository.getAllInscriptionsByPassengerId(passengerId);
        if(inscriptions.isEmpty()) return null;
        List<Trip> pending = new ArrayList<>();
        List<Trip> accepted = new ArrayList<>();
        List<Trip> refused = new ArrayList<>();
        for (Inscription inscription : inscriptions) {
          Trip trip = tripsProxy.getTrip(inscription.getTripId());
          if(inscription.getStatus().equals("pending")) pending.add(trip);
          else if(inscription.getStatus().equals("accepted")) accepted.add(trip);
          else refused.add(trip);
        }
        return new PassengerTrips(pending,accepted,refused);
      }

      public boolean deleteAllInscriptionOfAPassenger(Integer passengerId){
        List<Inscription> inscriptions = repository.getAllInscriptionsByPassengerId(passengerId);
        if(inscriptions.isEmpty()) return false;
        repository.deleteAll(inscriptions);
        return true;
      }

      public Inscription createInscription(Integer trip_id, Integer user_id) {
          if(repository.existsByTripIdAndPassengerId(trip_id,user_id)){
              return null;
          }
          Inscription inscription = new Inscription(0, trip_id, user_id, "pending");
          return repository.save(inscription);
      }

      public Inscription getInscription(Integer trip_id, Integer user_id) {
          return repository.findByTripIdAndPassengerId(trip_id,user_id);
      }

      public Inscription deleteInscription(Integer trip_id, Integer user_id) {
        Inscription inscription = repository.findByTripIdAndPassengerId(trip_id,user_id);
        if(inscription == null){
            return null;
        }
        repository.delete(inscription);
        return inscription;
      }

      public Inscription editInscriptionStatus(Integer trip_id, Integer user_id, String status) {
        Inscription inscription = repository.findByTripIdAndPassengerId(trip_id,user_id);
        if(inscription == null){
            return null;
        }
        inscription.setStatus(status);
        return repository.save(inscription);
      }

}
