package by.trubetski.gpsolutions.services;

import by.trubetski.gpsolutions.dto.request.RequestAddressDto;
import by.trubetski.gpsolutions.dto.request.RequestContactsDto;
import by.trubetski.gpsolutions.dto.request.RequestCreateHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseAllHotelsDto;
import by.trubetski.gpsolutions.dto.response.ResponseCreateHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseHotelDto;
import by.trubetski.gpsolutions.exception.HotelNotFoundException;
import by.trubetski.gpsolutions.models.Address;
import by.trubetski.gpsolutions.models.Contacts;
import by.trubetski.gpsolutions.models.Hotel;
import by.trubetski.gpsolutions.repositories.HotelRepository;
import by.trubetski.gpsolutions.services.impl.HotelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private static final Long HOTEL_ID = 1L;
    private static final String QUERY = "name=HotelA";
    private static final Pageable PAGEABLE = PageRequest.of(0, 5);

    private Hotel hotel;
    private ResponseAllHotelsDto responseAllHotelsDto;
    private ResponseHotelDto responseHotelDto;
    private RequestCreateHotelDto requestCreateHotelDto;
    private ResponseCreateHotelDto responseCreateHotelDto;
    private List<String> amenities;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        hotel.setId(HOTEL_ID);
        hotel.setName("TestHotel");

        Address address = new Address();
        hotel.setAddress(address);
        Contacts contacts = new Contacts();
        hotel.setContacts(contacts);

        responseAllHotelsDto = new ResponseAllHotelsDto();
        responseAllHotelsDto.setId(HOTEL_ID);
        responseAllHotelsDto.setName("TestHotel");
        responseHotelDto = new ResponseHotelDto();
        responseHotelDto.setId(HOTEL_ID);
        responseHotelDto.setName("TestHotel");
        requestCreateHotelDto = new RequestCreateHotelDto();
        requestCreateHotelDto.setName("TestHotel");
        RequestAddressDto requestAddress = new RequestAddressDto();
        requestCreateHotelDto.setAddress(requestAddress);
        RequestContactsDto requestContacts = new RequestContactsDto();
        requestCreateHotelDto.setContacts(requestContacts);
        responseCreateHotelDto = new ResponseCreateHotelDto();
        responseCreateHotelDto.setId(HOTEL_ID);
        amenities = List.of("Free WiFi", "Free Parking");
    }

    @Test
    void findAllWhenHotelsExist() {
        // Given
        Page<Hotel> hotelPage = new PageImpl<>(List.of(hotel), PAGEABLE, 1);
        when(hotelRepository.findAll(PAGEABLE)).thenReturn(hotelPage);

        // When
        Page<ResponseAllHotelsDto> result = hotelService.findAll(PAGEABLE);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        ResponseAllHotelsDto actualDto = result.getContent().get(0);
        assertEquals(hotel.getId(), actualDto.getId()); // Проверяем id
        assertEquals(hotel.getName(), actualDto.getName()); // Проверяем name
        verify(hotelRepository, times(1)).findAll(PAGEABLE);
    }

    @Test
    void findAllWhenNoHotelsExist() {
        // Given
        Page<Hotel> emptyPage = new PageImpl<>(Collections.emptyList(), PAGEABLE, 0);
        when(hotelRepository.findAll(PAGEABLE)).thenReturn(emptyPage);

        // When
        Page<ResponseAllHotelsDto> result = hotelService.findAll(PAGEABLE);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());
        verify(hotelRepository, times(1)).findAll(PAGEABLE);
    }

    @Test
    void findByIdWhenHotelExists() {
        // Given
        when(hotelRepository.findById(HOTEL_ID)).thenReturn(Optional.of(hotel));

        // When
        ResponseHotelDto result = hotelService.findById(HOTEL_ID);

        // Then
        assertNotNull(result);
        assertEquals(hotel.getId(), result.getId());
        assertEquals(hotel.getName(), result.getName());
        verify(hotelRepository, times(1)).findById(HOTEL_ID);
    }

    @Test
    void findByIdWhenHotelNotFound() {
        // Given
        when(hotelRepository.findById(HOTEL_ID)).thenReturn(Optional.empty());

        // When & Then
        HotelNotFoundException exception = assertThrows(HotelNotFoundException.class, () -> hotelService.findById(HOTEL_ID));
        assertNotNull(exception);
        verify(hotelRepository, times(1)).findById(HOTEL_ID);
    }

    @Test
    void searchWhenQueryIsValid() {
        // Given
        Page<Hotel> hotelPage = new PageImpl<>(List.of(hotel), PAGEABLE, 1);
        when(hotelRepository.findByNameContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(hotelPage);

        // When
        Page<ResponseAllHotelsDto> result = hotelService.search(QUERY, PAGEABLE);
        ResponseAllHotelsDto actualDto = result.getContent().get(0);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(hotel.getId(), actualDto.getId());
        assertEquals(hotel.getName(), actualDto.getName());
        verify(hotelRepository, times(1)).findByNameContainingIgnoreCase(anyString(), any(Pageable.class));
    }

    @Test
    void searchWhenQueryIsNull() {
        // Given
        Page<Hotel> hotelPage = new PageImpl<>(List.of(hotel), PAGEABLE, 1);
        when(hotelRepository.findAll(PAGEABLE)).thenReturn(hotelPage);

        // When
        Page<ResponseAllHotelsDto> result = hotelService.search(null, PAGEABLE);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(hotelRepository, times(1)).findAll(PAGEABLE);
    }

    @Test
    void createWhenValidRequest() {
        // Given
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        // When
        ResponseCreateHotelDto result = hotelService.create(requestCreateHotelDto);

        // Then
        assertNotNull(result);
        assertEquals(HOTEL_ID, result.getId());
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    void addAmenitiesWhenHotelExists() {
        // Given
        when(hotelRepository.findById(HOTEL_ID)).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        // When
        hotelService.addAmenities(HOTEL_ID, amenities);

        // Then
        verify(hotelRepository, times(1)).findById(HOTEL_ID);
        verify(hotelRepository, times(1)).save(any(Hotel.class));
        assertEquals(amenities, hotel.getAmenities());
    }

    @Test
    void addAmenitiesWhenHotelNotFound() {
        // Given
        when(hotelRepository.findById(HOTEL_ID)).thenReturn(Optional.empty());

        // When & Then
        HotelNotFoundException exception = assertThrows(HotelNotFoundException.class, () -> hotelService.addAmenities(HOTEL_ID, amenities));
        assertNotNull(exception);
        verify(hotelRepository, times(1)).findById(HOTEL_ID);
        verify(hotelRepository, never()).save(any(Hotel.class));
    }

    @Test
    void getHistogramByParamWhenParamValid() {
        // Given
        Map<String, Long> histogram = Map.of("Minsk", 1L, "Moskow", 2L);
        when(hotelRepository.countByCity()).thenReturn(histogram);

        // When
        Map<String, Long> result = hotelService.getHistogramByParam("city");

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get("Minsk"));
        assertEquals(2L, result.get("Moskow"));
        verify(hotelRepository, times(1)).countByCity();
    }

    @Test
    void getHistogramByParamWhenParamInvalid() {
        // Given When
        Map<String, Long> result = hotelService.getHistogramByParam("invalid");

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(hotelRepository, never()).countByCity();
        verify(hotelRepository, never()).countByBrand();
        verify(hotelRepository, never()).countByCountry();
        verify(hotelRepository, never()).countByAmenities();
    }
}