package by.trubetski.gpsolutions.util;

import by.trubetski.gpsolutions.dto.response.ResponseAmenitiesDto;
import by.trubetski.gpsolutions.enam.Amenity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.Getter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

public class AmenitiesSerializer extends JsonSerializer<ResponseAmenitiesDto> {
    @Override
    public void serialize(ResponseAmenitiesDto value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        gen.writeStartArray();

        List<AmenityCheck> checks = Arrays.asList(
                new AmenityCheck(value::isFreeParking, Amenity.FREE_PARKING),
                new AmenityCheck(value::isFreeWifi, Amenity.FREE_WIFI),
                new AmenityCheck(value::isNonSmokingRooms, Amenity.NON_SMOKING_ROOMS),
                new AmenityCheck(value::isConcierge, Amenity.CONCIERGE),
                new AmenityCheck(value::isOnSiteRestaurant, Amenity.ON_SITE_RESTAURANT),
                new AmenityCheck(value::isFitnessCenter, Amenity.FITNESS_CENTER),
                new AmenityCheck(value::isPetFriendlyRooms, Amenity.PET_FRIENDLY_ROOMS),
                new AmenityCheck(value::isRoomService, Amenity.ROOM_SERVICE),
                new AmenityCheck(value::isBusinessCenter, Amenity.BUSINESS_CENTER),
                new AmenityCheck(value::isMeetingRooms, Amenity.MEETING_ROOMS)
        );

        for (AmenityCheck check : checks) {
            if (check.isActive()) {
                gen.writeString(check.getAmenity().getDisplayName());
            }
        }

        gen.writeEndArray();
    }

    private static class AmenityCheck {
        private final BooleanSupplier isActive;
        @Getter
        private final Amenity amenity;

        AmenityCheck(BooleanSupplier isActive, Amenity amenity) {
            this.isActive = isActive;
            this.amenity = amenity;
        }

        public boolean isActive() {
            return isActive.getAsBoolean();
        }
    }
}
