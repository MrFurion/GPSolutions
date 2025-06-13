package by.trubetski.gpsolutions.dto.response;

import lombok.Data;

@Data
public class ResponseCreateHotelDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
