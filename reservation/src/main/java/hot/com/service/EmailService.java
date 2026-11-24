
package hot.com.service;

import hot.com.model.Reservation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Value("${resend.api.key}")
    private String apiKey;

    @Value("${resend.from}")
    private String fromEmail;

    @Value("${restaurant.email}")
    private String restaurantEmail;

    private final RestClient restClient = RestClient.create();

    public void sendReservationEmails(Reservation reservation) {

        String details =
                "Name: " + reservation.getFullName() + "\n" +
                        "Phone: " + reservation.getPhoneNumber() + "\n" +
                        "Email: " + reservation.getEmail() + "\n" +
                        "Guests: " + reservation.getNumberOfGuests() + "\n" +
                        "Occasion: " + reservation.getOccasion() + "\n" +
                        "Date: " + reservation.getReservationDate() + "\n" +
                        "Time: " + reservation.getReservationTime() + "\n" +
                        "Seating: " + reservation.getSeatingZone() + "\n" +
                        "Special Requests: " + reservation.getSpecialRequests();

        sendEmail(
                restaurantEmail,
                "New Restaurant Reservation",
                details
        );

        sendEmail(
                reservation.getEmail(),
                "Reservation Request Received",
                "Hello " + reservation.getFullName() +
                        ",\n\nYour reservation request has been received.\n\n" +
                        details +
                        "\n\nYour booking is currently pending confirmation."
        );
    }

    private void sendEmail(String to, String subject, String text) {

        Map<String, Object> payload = Map.of(
                "from", fromEmail,
                "to", List.of(to),
                "subject", subject,
                "text", text
        );

        restClient.post()
                .uri("https://api.resend.com/emails")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .toBodilessEntity();
    }
}
