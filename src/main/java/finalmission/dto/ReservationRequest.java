package finalmission.dto;

import java.time.LocalDate;

public record ReservationRequest(
        LocalDate reservationDate,
        int startTime,
        int endTime,
        int numberOfPeople
) {
}
