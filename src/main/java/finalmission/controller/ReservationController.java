package finalmission.controller;

import finalmission.dto.ReservationRequest;
import finalmission.dto.ReservationResponse;
import finalmission.global.LoginMember;
import finalmission.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody final ReservationRequest reservationRequest, @LoginMember final long memberId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.save(memberId, reservationRequest));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations(@LoginMember final long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getReservationsByMemberId(memberId));
    }

}
