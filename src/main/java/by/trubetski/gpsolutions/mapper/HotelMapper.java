package by.trubetski.gpsolutions.mapper;

import by.trubetski.gpsolutions.dto.request.RequestAddressDto;
import by.trubetski.gpsolutions.dto.request.RequestArrivalTimeDto;
import by.trubetski.gpsolutions.dto.request.RequestContactsDto;
import by.trubetski.gpsolutions.dto.request.RequestCreateHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseAllHotelsDto;
import by.trubetski.gpsolutions.dto.response.ResponseArrivalTimeDto;
import by.trubetski.gpsolutions.dto.response.ResponseCreateHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseHotelDto;
import by.trubetski.gpsolutions.models.Address;
import by.trubetski.gpsolutions.models.ArrivalTime;
import by.trubetski.gpsolutions.models.Contacts;
import by.trubetski.gpsolutions.models.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    HotelMapper INSTANCE = Mappers.getMapper(HotelMapper.class);

    @Mapping(source = "arrivalTimes", target = "arrivalTime", qualifiedByName = "mapArrivalTimesToArrivalTimeDto")
    @Mapping(source = "amenities", target = "amenities")
    ResponseHotelDto toDto(Hotel hotel);

    ResponseArrivalTimeDto toArrivalTimeDto(ArrivalTime arrivalTime);

    @Mapping(source = "address", target = "address", qualifiedByName = "mapAddressToString")
    @Mapping(source = "contacts", target = "phone", qualifiedByName = "mapContactsToPhone")
    ResponseAllHotelsDto toAllHotelsDto(Hotel hotel);

    @Mapping(source = "address", target = "address", qualifiedByName = "mapAddressToString")
    @Mapping(source = "contacts", target = "phone", qualifiedByName = "mapContactsToPhone")
    ResponseCreateHotelDto toCreateHotelDto(Hotel hotel);

    @Mapping(source = "address", target = "address")
    @Mapping(source = "contacts", target = "contacts", qualifiedByName = "toContactsList")
    @Mapping(source = "arrivalTime", target = "arrivalTimes", qualifiedByName = "toArrivalTimeSet")
    Hotel toHotel(RequestCreateHotelDto requestCreateHotelDto);

    @Named("mapArrivalTimesToArrivalTimeDto")
    default ResponseArrivalTimeDto mapArrivalTimesToArrivalTimeDto(Set<ArrivalTime> arrivalTimes) {
        if (arrivalTimes == null || arrivalTimes.isEmpty()) {
            return null;
        }
        ArrivalTime arrivalTime = arrivalTimes.iterator().next();
        return toArrivalTimeDto(arrivalTime);
    }

    @Named("mapAddressToString")
    default String mapAddressToString(Address address) {
        if (address == null) {
            return null;
        }
        return String.format("%s %s, %s, %s, %s",
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostalCode(),
                address.getCountry()
        ).trim();
    }

    @Named("mapContactsToPhone")
    default String mapContactsToPhone(List<Contacts> contacts) {
        if (contacts == null || contacts.isEmpty()) {
            return null;
        }
        return contacts.getFirst().getPhone();
    }

    @Mapping(target = "id", ignore = true)
    Address toAddress(RequestAddressDto requestAddressDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    Contacts toContacts(RequestContactsDto requestContactsDto);

    ArrivalTime toArrivalTime(RequestArrivalTimeDto requestArrivalTimeDto);

    @Named("toContactsList")
    default List<Contacts> toContactsList(RequestContactsDto contactsDto) {
        if (contactsDto == null) return Collections.emptyList();
        Contacts contacts = toContacts(contactsDto);
        contacts.setHotel(null);
        return Collections.singletonList(contacts);
    }

    @Named("toArrivalTimeSet")
    default Set<ArrivalTime> toArrivalTimeSet(RequestArrivalTimeDto arrivalTimeDto) {
        if (arrivalTimeDto == null) return Collections.emptySet();
        ArrivalTime arrivalTime = toArrivalTime(arrivalTimeDto);
        return Collections.singleton(new ArrivalTime(arrivalTime.getCheckIn(), arrivalTime.getCheckOut()));
    }
}
