package by.trubetski.gpsolutions.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseHotelDto {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private ResponseAddressDto address;
    private ResponseContactsDto contacts;
    private ResponseArrivalTimeDto arrivalTime;
    private List<String> amenities = new ArrayList<>();
}
