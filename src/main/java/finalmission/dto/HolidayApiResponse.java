package finalmission.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

@JacksonXmlRootElement(localName = "response")
public record HolidayApiResponse(
        @JacksonXmlProperty(localName = "body")
        Body body
) {
    
    public record Body(
            @JacksonXmlProperty(localName = "items")
            Items items
    ) {}
    
    public record Items(
            @JacksonXmlElementWrapper(useWrapping = false)
            @JacksonXmlProperty(localName = "item")
            List<HolidayResponse> item
    ) {}
}