package finalmission.external;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class HolidaysRestClient {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService")
                .build();
    }
}
