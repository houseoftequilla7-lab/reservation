package hot.com.controller;

import hot.com.model.Reservation;
import hot.com.service.ReservationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(
            ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?> createReservation(
            @Valid @RequestBody ReservationRequest request) {

        Reservation reservation = new Reservation();

        reservation.setNumberOfGuests(request.numberOfGuests);
        reservation.setOccasion(request.occasion);
        reservation.setReservationDate(request.reservationDate);
        reservation.setSeatingZone(request.seatingZone);
        reservation.setReservationTime(request.reservationTime);

        reservation.setFullName(request.fullName);
        reservation.setPhoneNumber(request.phoneNumber);
        reservation.setEmail(request.email);
        reservation.setSpecialRequests(request.specialRequests);

        // Save reservation in MongoDB
        Reservation savedReservation =
                reservationService.createReservation(reservation);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Reservation saved successfully",
                        "reservationId", savedReservation.getId(),
                        "bookingStatus", savedReservation.getBookingStatus()
                )
        );
    }

    public static class ReservationRequest {

        @Min(1)
        public int numberOfGuests;

        @NotBlank
        public String occasion;

        @NotNull
        public LocalDate reservationDate;

        @NotBlank
        public String seatingZone;

        @NotBlank
        public String reservationTime;

        @NotBlank
        public String fullName;

        @NotBlank
        public String phoneNumber;

        @NotBlank
        @Email
        public String email;

        public String specialRequests;
    }
}