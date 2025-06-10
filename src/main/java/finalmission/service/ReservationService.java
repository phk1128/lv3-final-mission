package finalmission.service;

import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationDate;
import finalmission.domain.ReservationStatus;
import finalmission.domain.ReservationTimeSlot;
import finalmission.dto.ReservationRequest;
import finalmission.dto.ReservationResponse;
import finalmission.repository.ReservationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberService memberService;

    @Transactional
    public ReservationResponse save(final long memberId, final ReservationRequest reservationRequest) {
        final Member member = memberService.getMemberById(memberId);
        final ReservationDate reservationDate = new ReservationDate(reservationRequest.reservationDate());
        final ReservationTimeSlot reservationTimeSlot = new ReservationTimeSlot(reservationRequest.startTime(),
                reservationRequest.endTime());
        final Reservation newRes = new Reservation(reservationDate, reservationTimeSlot, member,
                reservationRequest.numberOfPeople(), ReservationStatus.SUCCESS);
        final List<Reservation> reservations = reservationRepository.findByMemberId(memberId);
        reservations.forEach(reservation -> reservation.validateDuplicate(newRes));
        return ReservationResponse.from(reservationRepository.save(newRes));
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByMemberId(final long memberId) {
        final List<Reservation> reservations = reservationRepository.findByMemberId(memberId);
        return reservations.stream().map(ReservationResponse::from).toList();
    }
}
