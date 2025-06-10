package finalmission.external;

import finalmission.dto.HolidayApiResponse;
import finalmission.dto.HolidayApiResponse.Body;
import finalmission.dto.HolidayApiResponse.Items;
import finalmission.dto.HolidayResponse;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.client.RestClient.RequestHeadersUriSpec;
import org.springframework.web.client.RestClient.ResponseSpec;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class HolidayRequesterImplTest {

    @Mock
    private RestClient restClient;
    
    @Mock
    private RequestHeadersUriSpec requestHeadersUriSpec;
    
    @Mock
    private ResponseSpec responseSpec;
    
    @InjectMocks
    private HolidayRequesterImpl holidayRequester;

    @Test
    void getHolidays_정상_응답시_휴일_목록_반환() {
        // given
        HolidayResponse holiday = new HolidayResponse("20241225", "Y", "크리스마스");
        Items items = new Items(List.of(holiday));
        Body body = new Body(items);
        HolidayApiResponse response = new HolidayApiResponse(body);
        
        given(restClient.get()).willReturn(requestHeadersUriSpec);
        given(requestHeadersUriSpec.uri(any(Function.class))).willReturn(requestHeadersUriSpec);
        given(requestHeadersUriSpec.retrieve()).willReturn(responseSpec);
        given(responseSpec.body(HolidayApiResponse.class)).willReturn(response);
        
        // when
        List<HolidayResponse> holidays = holidayRequester.getHolidays(LocalDate.of(2024, 12, 25));
        
        // then
        assertThat(holidays).hasSize(1);
        assertThat(holidays.get(0).dateName()).isEqualTo("크리스마스");
    }

    @Test
    void getHolidays_응답이_null일때_빈_목록_반환() {
        // given
        given(restClient.get()).willReturn(requestHeadersUriSpec);
        given(requestHeadersUriSpec.uri(any(Function.class))).willReturn(requestHeadersUriSpec);
        given(requestHeadersUriSpec.retrieve()).willReturn(responseSpec);
        given(responseSpec.body(HolidayApiResponse.class)).willReturn(null);
        
        // when
        List<HolidayResponse> holidays = holidayRequester.getHolidays(LocalDate.of(2024, 12, 25));
        
        // then
        assertThat(holidays).isEmpty();
    }
}
