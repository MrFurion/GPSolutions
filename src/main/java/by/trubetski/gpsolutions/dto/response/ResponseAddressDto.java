package by.trubetski.gpsolutions.dto.response;

import lombok.Data;

@Data
public class ResponseAddressDto {
    private String houseNumber;
    private String street;
    private String city;
    private String country;
    private String postalCode;
}
