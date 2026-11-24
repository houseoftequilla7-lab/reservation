package hot.com.repository;

import hot.com.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReservationRepository
        extends MongoRepository<Reservation, String> {
}