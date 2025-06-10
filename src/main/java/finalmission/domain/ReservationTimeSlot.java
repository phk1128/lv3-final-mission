package finalmission.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationTimeSlot {

    private int startTime;
    private int endTime;

    public ReservationTimeSlot(final int startTime, final int endTime) {
        validateTimeRange(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validateTimeRange(final int startTime, final int endTime) {
        if (isOutOfRange(startTime) || isOutOfRange(endTime)) {
            throw new IllegalArgumentException("시간은 0시 ~ 24시까지만 가능 합니다.");
        }

        if (startTime > endTime) {
            throw new IllegalArgumentException("시작 시간은 끝 시간보다 미래일 수 없습니다.");
        }
    }

    private boolean isOutOfRange(final int time) {
        return time < 0 || time > 24;
    }

    public boolean isInTime(final ReservationTimeSlot other) {
        return (other.startTime >= this.startTime && other.startTime <= this.endTime && other.endTime <= this.endTime) ||
                (other.endTime >= this.startTime && other.endTime <= this.endTime);
    }

}
