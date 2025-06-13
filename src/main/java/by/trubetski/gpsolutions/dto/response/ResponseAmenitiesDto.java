package by.trubetski.gpsolutions.dto.response;

import by.trubetski.gpsolutions.util.AmenitiesSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@JsonSerialize(using = AmenitiesSerializer.class)
@Data
public class ResponseAmenitiesDto {
    private boolean freeParking;
    private boolean freeWifi;
    private boolean nonSmokingRooms;
    private boolean concierge;
    private boolean onSiteRestaurant;
    private boolean fitnessCenter;
    private boolean petFriendlyRooms;
    private boolean roomService;
    private boolean businessCenter;
    private boolean meetingRooms;
}
