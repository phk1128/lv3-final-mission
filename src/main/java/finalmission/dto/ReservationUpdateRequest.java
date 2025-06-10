package finalmission.dto;

import java.time.LocalDate;

public record ReservationUpdateRequest(
        LocalDate reservationDate,
        int startTime,
        int endTime,
        int numberOfPeople
) {
}
