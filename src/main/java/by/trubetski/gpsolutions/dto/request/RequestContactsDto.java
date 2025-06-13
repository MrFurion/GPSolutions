package by.trubetski.gpsolutions.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static by.trubetski.gpsolutions.dto.constants.RequestConstants.EMAIL;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.MESSAGE_PHONE_FORMAT;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.PHONE;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.PHONE_FORMAT;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.SHOULD_BETWEEN_1_AND_255_CHARACTER;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.SHOULD_BE_YOUR_NAME_EMAIL_HILTON_COM_FORMAT;
import static by.trubetski.gpsolutions.dto.constants.RequestConstants.SHOULD_NOT_BE_EMPTY;

@Data
public class RequestContactsDto {
    @NotEmpty(message = PHONE + SHOULD_NOT_BE_EMPTY)
    @Size(min = 1, max = 255, message = PHONE + SHOULD_BETWEEN_1_AND_255_CHARACTER)
    @Pattern(regexp = PHONE_FORMAT, message = MESSAGE_PHONE_FORMAT)
    private String phone;
    @NotEmpty(message = EMAIL + SHOULD_NOT_BE_EMPTY)
    @Size(min = 1, max = 255, message = EMAIL + SHOULD_BETWEEN_1_AND_255_CHARACTER)
    @Email(message = EMAIL + SHOULD_BE_YOUR_NAME_EMAIL_HILTON_COM_FORMAT)
    private String email;
}
