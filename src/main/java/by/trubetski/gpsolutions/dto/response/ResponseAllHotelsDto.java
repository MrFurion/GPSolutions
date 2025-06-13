package by.trubetski.gpsolutions.dto.response;

import lombok.Data;

@Data
public class ResponseAllHotelsDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
