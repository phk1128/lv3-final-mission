package finalmission.external;

import finalmission.domain.Holiday;
import finalmission.dto.HolidayApiResponse;
import finalmission.dto.HolidayResponse;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class HolidayRequesterImpl implements HolidaysRequester {

    private final HolidayRestClient holidayRestClient;

    @Override
    public List<HolidayResponse> getHolidays(final LocalDate date) {
        try {
            final HolidayApiResponse response = holidayRestClient.getHolidaysByYearAndMonth(date.getYear(), date.getMonthValue());
            return getHolidayResponseByApiResponse(response);
        } catch (Exception e) {
            log.error("API 호출 중 예상치 못한 예외 발생");
            return Collections.emptyList();
        }
    }

    private List<HolidayResponse> getHolidayResponseByApiResponse(final HolidayApiResponse response) {
        if (response.body() == null) {
            return Collections.emptyList();
        }
        final HolidayApiResultCode resultCode = getHolidayApiResultCodeByApiResponse(response);
        if (!resultCode.isSuccess()) {
            log.error("API 오류 : {} {}", resultCode, resultCode.getDescription());
        }
        return extractHolidaysData(response);

    }

    private List<HolidayResponse> extractHolidaysData(final HolidayApiResponse response) {
        if (response.body() == null ||
                response.body().items() == null ||
                response.body().items().item() == null) {
            return Collections.emptyList();
        }

        return response.body().items().item();
    }

    private HolidayApiResultCode getHolidayApiResultCodeByApiResponse(final HolidayApiResponse response) {
        if (response.header() == null || response.header().resultCode() == null) {
            return HolidayApiResultCode.UNKNOWN_ERROR;
        }
        return HolidayApiResultCode.fromCode(response.header().resultCode());
    }

}
