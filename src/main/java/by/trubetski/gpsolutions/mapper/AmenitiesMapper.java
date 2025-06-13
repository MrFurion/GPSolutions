package by.trubetski.gpsolutions.mapper;

import by.trubetski.gpsolutions.models.Amenities;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AmenitiesMapper {

    AmenitiesMapper INSTANCE = Mappers.getMapper(AmenitiesMapper.class);

    default Amenities toAmenities(List<String> amenities) {
        if (amenities == null) {
            return new Amenities();
        }

        Amenities result = new Amenities();
        result.setFreeParking(amenities.contains("Free parking"));
        result.setFreeWifi(amenities.contains("Free WiFi"));
        result.setNonSmokingRooms(amenities.contains("Non-smoking rooms"));
        result.setConcierge(amenities.contains("Concierge"));
        result.setOnSiteRestaurant(amenities.contains("On-site restaurant"));
        result.setFitnessCenter(amenities.contains("Fitness center"));
        result.setPetFriendlyRooms(amenities.contains("Pet-friendly rooms"));
        result.setRoomService(amenities.contains("Room service"));
        result.setBusinessCenter(amenities.contains("Business center"));
        result.setMeetingRooms(amenities.contains("Meeting rooms"));
        return result;
    }
}
