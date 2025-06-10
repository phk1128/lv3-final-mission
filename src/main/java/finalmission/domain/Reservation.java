package finalmission.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private ReservationDate reservationDate;

    @Embedded
    private ReservationTimeSlot reservationTimeSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int numberOfPeople;

    public Reservation(final ReservationDate reservationDate, final ReservationTimeSlot reservationTimeSlot,
                       final Member member,
                       final int numberOfPeople) {
        this.reservationDate = reservationDate;
        this.reservationTimeSlot = reservationTimeSlot;
        this.member = member;
        this.numberOfPeople = numberOfPeople;
    }

    public void validateDuplicate(final Reservation other) {
        if (reservationDate.isEqual(other.reservationDate) && reservationTimeSlot.isInTime(other.reservationTimeSlot)) {
            throw new IllegalArgumentException("이미 예약된 시간 입니다.");
        }
    }

    public void updateReservationDate(final ReservationDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void updateReservationTimeSlot(final ReservationTimeSlot reservationTimeSlot) {
        this.reservationTimeSlot = reservationTimeSlot;
    }

    public void updateNumberOfPeople(final int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public void updateMember(final Member member) {
        this.member = member;
    }
}
