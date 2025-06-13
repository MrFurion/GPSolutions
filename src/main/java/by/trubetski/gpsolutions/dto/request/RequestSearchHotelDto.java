package by.trubetski.gpsolutions.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static by.trubetski.gpsolutions.dto.constants.RequestConstants.MESSAGE_FOR_AMENITIES_FIELD;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.PATTERN_FOR_AMENITIES_FIELD;

@Data
public class RequestSearchHotelDto {
    private String name;
    private String brand;
    private String city;
    private String country;
    @Pattern(regexp = PATTERN_FOR_AMENITIES_FIELD,
            message = MESSAGE_FOR_AMENITIES_FIELD)
    private String amenities;
}
