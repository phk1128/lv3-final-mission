package finalmission.service;

import finalmission.domain.CanceledReservation;
import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationDate;
import finalmission.domain.ReservationTimeSlot;
import finalmission.dto.ReservationRequest;
import finalmission.dto.ReservationResponse;
import finalmission.dto.ReservationUpdateRequest;
import finalmission.repository.ReservationRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberService memberService;
    private final CanceledReservationService canceledReservationService;

    @Transactional
    public ReservationResponse save(final long memberId, final ReservationRequest reservationRequest) {
        final Member member = memberService.getMemberById(memberId);
        validateHoliday(reservationRequest.reservationDate());
        final ReservationDate reservationDate = new ReservationDate(reservationRequest.reservationDate());
        final ReservationTimeSlot reservationTimeSlot = new ReservationTimeSlot(reservationRequest.startTime(),
                reservationRequest.endTime());
        final Reservation newRes = new Reservation(reservationDate, reservationTimeSlot, member,
                reservationRequest.numberOfPeople());
        final List<Reservation> reservations = reservationRepository.findByMemberId(memberId);
        reservations.forEach(reservation -> reservation.validateDuplicate(newRes));
        return ReservationResponse.from(reservationRepository.save(newRes));
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByMemberId(final long memberId) {
        final List<Reservation> reservations = reservationRepository.findByMemberId(memberId);
        return reservations.stream().map(ReservationResponse::from).toList();
    }

    @Transactional
    public ReservationResponse update(final long reservationId, final long memberId, final ReservationUpdateRequest reservationUpdateRequest) {
        final Member member = memberService.getMemberById(memberId);
        final Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("예약을 찾을 수 없습니다."));
        validateHoliday(reservationUpdateRequest.reservationDate());
        reservation.updateReservationDate(new ReservationDate(reservationUpdateRequest.reservationDate()));
        reservation.updateNumberOfPeople(reservationUpdateRequest.numberOfPeople());
        reservation.updateReservationTimeSlot(new ReservationTimeSlot(reservationUpdateRequest.startTime(),
                reservationUpdateRequest.endTime()));
        reservation.updateMember(member);
        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    @Transactional
    public void delete(final long memberId, final long reservationId) {
        final Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("예약이 존재하지 않습니다."));
        final Member member = memberService.getMemberById(memberId);
        if (!Objects.equals(reservation.getMember(), member)) {
            throw new IllegalArgumentException("삭제할 수 없습니다.");
        }
        reservationRepository.deleteById(reservationId);
        canceledReservationService.save(CanceledReservation.from(reservation));
    }

    private void validateHoliday(final LocalDate date) {
        if (holidayService.isHoliday(date)) {
            throw new IllegalArgumentException("공휴일은 예약이 불가능 합니다.");
        }
    }
}
