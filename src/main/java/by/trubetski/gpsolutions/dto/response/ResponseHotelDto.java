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
    private List<ResponseContactsDto> contacts = new ArrayList<>();
    private ResponseArrivalTimeDto arrivalTime;
    private ResponseAmenitiesDto amenities;
}
