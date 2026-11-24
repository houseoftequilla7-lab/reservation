package hot.com.service;

import hot.com.model.Reservation;
import hot.com.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(Reservation reservation) {

        reservation.setBookingStatus("PENDING");
        reservation.setCreatedAt(Instant.now());

        return reservationRepository.save(reservation);
    }
}