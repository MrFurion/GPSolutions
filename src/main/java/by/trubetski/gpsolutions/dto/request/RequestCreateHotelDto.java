package by.trubetski.gpsolutions.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static by.trubetski.gpsolutions.dto.constants.RequestConstants.BRAND;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.NAME;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.SHOULD_BETWEEN_5_AND_255_CHARACTER;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.SHOULD_NOT_BE_EMPTY;

@Data
public class RequestCreateHotelDto {
    @NotEmpty(message = NAME + SHOULD_NOT_BE_EMPTY)
    @Size(min = 5, max = 255, message = NAME + SHOULD_BETWEEN_5_AND_255_CHARACTER)
    private String name;
    private String description;
    @NotEmpty(message = BRAND + SHOULD_NOT_BE_EMPTY)
    @Size(min = 5, max = 255, message = BRAND + SHOULD_BETWEEN_5_AND_255_CHARACTER)
    private String brand;
    @Valid
    private RequestAddressDto address;
    @Valid
    private RequestContactsDto contacts;
    @Valid
    private RequestArrivalTimeDto arrivalTime;
}
