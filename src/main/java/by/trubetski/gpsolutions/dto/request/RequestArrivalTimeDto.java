package by.trubetski.gpsolutions.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static by.trubetski.gpsolutions.dto.constants.RequestConstants.CHECK_IN_TIME_MUST_BE_IN_24_HOUR_FORMAT_HH_MM_E_G_14_00;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.CHECK_OUT_TIME_MUST_BE_IN_24_HOUR_FORMAT_HH_MM_E_G_12_00;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.SHOULD_BETWEEN_5_AND_255_CHARACTER;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.SHOULD_NOT_BE_EMPTY;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.TIME;

@Data
public class RequestArrivalTimeDto {
    @NotEmpty(message = "Check In" + SHOULD_NOT_BE_EMPTY)
    @Size(min = 5, max = 255, message = "Check In" + SHOULD_BETWEEN_5_AND_255_CHARACTER)
    @Pattern(regexp = TIME, message = CHECK_IN_TIME_MUST_BE_IN_24_HOUR_FORMAT_HH_MM_E_G_14_00)
    private String checkIn;
    @Pattern(regexp = TIME, message = CHECK_OUT_TIME_MUST_BE_IN_24_HOUR_FORMAT_HH_MM_E_G_12_00)
    private String checkOut;
}
