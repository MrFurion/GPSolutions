package by.trubetski.gpsolutions.services.impl;

import by.trubetski.gpsolutions.dto.request.RequestCreateHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseAllHotelsDto;
import by.trubetski.gpsolutions.dto.response.ResponseCreateHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseHotelDto;
import by.trubetski.gpsolutions.enam.SearchField;
import by.trubetski.gpsolutions.exception.HotelNotFoundException;
import by.trubetski.gpsolutions.mapper.HotelMapper;
import by.trubetski.gpsolutions.models.Hotel;
import by.trubetski.gpsolutions.repositories.HotelRepository;
import by.trubetski.gpsolutions.services.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

    public static final String HOTEL_ID_NOT_FOUND = "Hotel id {} not found";
    public static final String REGEX = "=";
    private final HotelRepository hotelRepository;

    @Cacheable(value = "cardsPage", key = "'page:' + #pageable.pageNumber + ':size:' " +
                                          "+ #pageable.pageSize + ':sort:' + " +
                                          "#pageable.sort.toString()")
    public Page<ResponseAllHotelsDto> findAll(Pageable pageable) {
        Page<Hotel> hotels = hotelRepository.findAll(pageable);
        return hotels.map(HotelMapper.INSTANCE::toAllHotelsDto);
    }

    @Cacheable(value = "hotelById", key = "#id")
    public ResponseHotelDto findById(Long id) {
        return HotelMapper.INSTANCE.toDto(hotelRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(HOTEL_ID_NOT_FOUND, id);
                    return new HotelNotFoundException();
                }));
    }

    @Cacheable(value = "searchResults", key = "'query:' + #query + ':page:' " +
                                              "+ #pageable.pageNumber + ':size:' " +
                                              "+ #pageable.pageSize + ':sort:' " +
                                              "+ #pageable.sort.toString().replace(' ', '_')")
    public Page<ResponseAllHotelsDto> search(String query, Pageable pageable) {
        if (query == null || query.isEmpty()) {
            return hotelRepository.findAll(pageable)
                    .map(HotelMapper.INSTANCE::toAllHotelsDto);
        }

        String[] parts = query.split(REGEX, 2);
        if (parts.length == 2) {
            String field = parts[0].toLowerCase();
            String value = parts[1].toLowerCase();
            SearchField searchField = SearchField.fromDisplayName(field);
            if (searchField != null) {
                return switch (searchField) {
                    case NAME -> hotelRepository.findByNameContainingIgnoreCase(value, pageable)
                            .map(HotelMapper.INSTANCE::toAllHotelsDto);
                    case BRAND -> hotelRepository.findByBrandContainingIgnoreCase(value, pageable)
                            .map(HotelMapper.INSTANCE::toAllHotelsDto);
                    case CITY -> hotelRepository.findByAddressCityContainingIgnoreCase(value, pageable)
                            .map(HotelMapper.INSTANCE::toAllHotelsDto);
                    case COUNTRY -> hotelRepository.findByAddressCountryContainingIgnoreCase(value, pageable)
                            .map(HotelMapper.INSTANCE::toAllHotelsDto);
                    case AMENITIES -> hotelRepository.findByAmenitiesContainingValue(value, pageable)
                            .map(HotelMapper.INSTANCE::toAllHotelsDto);
                };
            }
        }
        return hotelRepository.searchHotels(parts.length == 2 ? parts[1] : query, null, null, null, null, pageable)
                .map(HotelMapper.INSTANCE::toAllHotelsDto);
    }


    @Transactional
    public ResponseCreateHotelDto create(RequestCreateHotelDto requestCreateHotelDto) {
        Hotel hotel = HotelMapper.INSTANCE.toHotel(requestCreateHotelDto);
        hotel.getAddress().setHotel(hotel);
        hotel.getContacts().setHotel(hotel);
        Hotel hotelSaved = hotelRepository.save(hotel);
        return HotelMapper.INSTANCE.toCreateHotelDto(hotelSaved);
    }

    @Transactional
    public void addAmenities(Long hotelId, List<String> updateAmenities) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> {
            log.error(HOTEL_ID_NOT_FOUND, hotelId);
            return new HotelNotFoundException();
        });

        hotel.getAmenities().clear();
        hotel.setAmenities(updateAmenities);
        hotelRepository.save(hotel);
    }

    @Cacheable(value = "histogramResults", key = "#param.toLowerCase()")
    public Map<String, Long> getHistogramByParam(String param) {
        SearchField searchField = SearchField.fromDisplayName(param.toLowerCase());

        if (searchField != null) {
            return switch (searchField) {
                case BRAND -> hotelRepository.countByBrand();
                case CITY -> hotelRepository.countByCity();
                case COUNTRY -> hotelRepository.countByCountry();
                case AMENITIES -> hotelRepository.countByAmenities();
                case NAME -> Collections.emptyMap();
            };
        }
        return Collections.emptyMap();
    }
}
