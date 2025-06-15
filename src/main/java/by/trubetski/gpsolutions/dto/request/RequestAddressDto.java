package by.trubetski.gpsolutions.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static by.trubetski.gpsolutions.dto.constants.RequestConstants.CITY;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.COUNTRY;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.HOUSE_NUMBER;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.POSTAL_CODE;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.SHOULD_BETWEEN_1_AND_255_CHARACTER;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.SHOULD_NOT_BE_EMPTY;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.STREET;

@Data
public class RequestAddressDto {
    @NotEmpty(message = HOUSE_NUMBER + SHOULD_NOT_BE_EMPTY)
    @Size(min = 1, max = 255, message = HOUSE_NUMBER + SHOULD_BETWEEN_1_AND_255_CHARACTER)
    private String houseNumber;
    @NotEmpty(message = STREET + SHOULD_NOT_BE_EMPTY)
    @Size(min = 1, max = 255, message = STREET + SHOULD_BETWEEN_1_AND_255_CHARACTER)
    private String street;
    @NotEmpty(message = CITY + SHOULD_NOT_BE_EMPTY)
    @Size(min = 1, max = 255, message = CITY + SHOULD_BETWEEN_1_AND_255_CHARACTER)
    private String city;
    @NotEmpty(message = COUNTRY + SHOULD_NOT_BE_EMPTY)
    @Size(min = 1, max = 255, message = COUNTRY + SHOULD_BETWEEN_1_AND_255_CHARACTER)
    private String country;
    @NotEmpty(message = POSTAL_CODE + SHOULD_NOT_BE_EMPTY)
    @Size(min = 1, max = 255, message = POSTAL_CODE + SHOULD_BETWEEN_1_AND_255_CHARACTER)
    private String postCode;
}
