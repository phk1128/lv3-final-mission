package finalmission.service;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import finalmission.domain.Holiday;
import finalmission.dto.HolidayResponse;
import finalmission.external.HolidaysRequester;
import finalmission.repository.HolidayRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidaysRequester holidaysRequester;
    private final HolidayRepository holidayRepository;

    public boolean isHoliday(final LocalDate date) {
        return holidayRepository.existsByDate(date);
    }

    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @Scheduled(cron = "0 0 0 1 * ?")
    public void saveHoliday() {
        final List<Holiday> holidays = holidaysRequester.getHolidays(LocalDate.now()).stream()
                .map(this::convertHoliday)
                .toList();
        holidayRepository.saveAll(holidays);
    }

    private Holiday convertHoliday(final HolidayResponse holiday) {
        final LocalDate date = LocalDate.parse(holiday.locdate(), DateTimeFormatter.ofPattern("yyyyMMdd"));
        boolean isHoliday = Objects.equals(holiday.isHoliday(), "Y");
        return new Holiday(date, holiday.dateName(), isHoliday);
    }

}
