package by.trubetski.gpsolutions.services.impl;

import by.trubetski.gpsolutions.dto.request.RequestAmenitiesDto;
import by.trubetski.gpsolutions.dto.request.RequestCreateHotelDto;
import by.trubetski.gpsolutions.dto.request.RequestSearchHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseAllHotelsDto;
import by.trubetski.gpsolutions.dto.response.ResponseCreateHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseHotelDto;
import by.trubetski.gpsolutions.exception.HotelNotFoundException;
import by.trubetski.gpsolutions.mapper.AmenitiesMapper;
import by.trubetski.gpsolutions.mapper.HotelMapper;
import by.trubetski.gpsolutions.models.Address;
import by.trubetski.gpsolutions.models.Amenities;
import by.trubetski.gpsolutions.models.ArrivalTime;
import by.trubetski.gpsolutions.models.Contacts;
import by.trubetski.gpsolutions.models.Hotel;
import by.trubetski.gpsolutions.repositories.HotelRepository;
import by.trubetski.gpsolutions.services.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

    public static final String HOTEL_ID_NOT_FOUND = "Hotel id {} not found";
    public static final String BRAND = "brand";
    private final HotelRepository hotelRepository;

    public Page<ResponseAllHotelsDto> findAll(Pageable pageable) {
        Page<Hotel> hotels = hotelRepository.findAll(pageable);
        return hotels.map(HotelMapper.INSTANCE::toAllHotelsDto);
    }

    public ResponseHotelDto findById(Long id) {
        return HotelMapper.INSTANCE.toDto(hotelRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(HOTEL_ID_NOT_FOUND, id);
                    return new HotelNotFoundException();
                }));
    }

    public Page<ResponseAllHotelsDto> search(RequestSearchHotelDto requestSearchHotelDto, Pageable pageable) {

        String name = nullIfEmpty(requestSearchHotelDto.getName());
        String brand = nullIfEmpty(requestSearchHotelDto.getBrand());
        String city = nullIfEmpty(requestSearchHotelDto.getCity());
        String country = nullIfEmpty(requestSearchHotelDto.getCountry());
        String amenities = nullIfEmpty(requestSearchHotelDto.getAmenities());

        Page<Hotel> hotels = hotelRepository.searchHotels(
                name,
                brand,
                city,
                country,
                amenities,
                pageable
        );
        return hotels.map(HotelMapper.INSTANCE::toAllHotelsDto);
    }

    @Transactional
    public ResponseCreateHotelDto create(RequestCreateHotelDto requestCreateHotelDto) {
        Hotel hotel = HotelMapper.INSTANCE.toHotel(requestCreateHotelDto);
        hotel.getAddress().setHotel(hotel);
        hotel.getContacts().forEach(contact -> contact.setHotel(hotel));
        Hotel hotelSaved = hotelRepository.save(hotel);
        return  HotelMapper.INSTANCE.toCreateHotelDto(hotelSaved);
    }

    @Transactional
    public void addAmenities(Long hotelId, List<String> updateAmenities){
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(()->{
            log.error(HOTEL_ID_NOT_FOUND, hotelId);
            return new HotelNotFoundException();
        });


        Amenities amenities = AmenitiesMapper.INSTANCE.toAmenities(updateAmenities);

        amenities.setHotel(hotel);
        hotel.setAmenities(amenities);
        hotelRepository.save(hotel);
    }

    public Map<String, Long> getHistogramByParam(String param){
        List<Hotel> hotels = hotelRepository.findAll();
        return  switch (param.toLowerCase()) {
            case BRAND -> hotels.stream().filter(hotel -> hotel.getBrand().equals(BRAND))
                    .collect(Collectors.groupingBy(Hotel::getBrand, Collectors.counting()));

            default -> throw new IllegalArgumentException("Unsupported parameter: " + param);
        };

    }

    private static String nullIfEmpty(String value) {
        return value != null && !value.isEmpty() ? value : null;
    }
}
