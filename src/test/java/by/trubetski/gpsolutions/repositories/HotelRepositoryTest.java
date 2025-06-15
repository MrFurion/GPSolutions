package by.trubetski.gpsolutions.repositories;

import by.trubetski.gpsolutions.models.Address;
import by.trubetski.gpsolutions.models.Hotel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("testcontainers")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:db/data.sql"})
class HotelRepositoryTest {
    @Autowired
    private HotelRepository hotelRepository;

    private static final Pageable PAGEABLE = PageRequest.of(0, 10);

    @Test
    @DirtiesContext
    void searchHotelsWhenNoFiltersReturnsAllHotels() {
        // Given

        // When
        Page<Hotel> result = hotelRepository.searchHotels(null, null, null, null, null, PAGEABLE);

        // Then
        assertNotNull(result);
        assertEquals(10, result.getTotalElements());
        assertTrue(result.getContent().stream().allMatch(h -> h.getId() != null));
    }

    @Test
    @DirtiesContext
    void searchHotelsWhenNameFilterMatchesReturnsMatchingHotels() {
        // Given
        String nameFilter = "Grand";

        // When
        Page<Hotel> result = hotelRepository.searchHotels(nameFilter, null, null, null, null, PAGEABLE);

        // Then
        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0);
        assertTrue(result.getContent().stream().allMatch(h -> h.getName().toLowerCase().contains(nameFilter.toLowerCase())));
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DirtiesContext
    void searchHotelsWhenBrandFilterMatchesReturnsMatchingHotels() {
        // Given
        String brandFilter = "Marriott";

        // When
        Page<Hotel> result = hotelRepository.searchHotels(null, brandFilter, null, null, null, PAGEABLE);

        // Then
        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0);
        assertTrue(result.getContent().stream().allMatch(h -> h.getBrand().toLowerCase().contains(brandFilter.toLowerCase())));
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DirtiesContext
    void searchHotelsWhenCityFilterMatchesReturnsMatchingHotels() {
        // Given
        String cityFilter = "New York";

        // When
        Page<Hotel> result = hotelRepository.searchHotels(null, null, cityFilter, null, null, PAGEABLE);

        // Then
        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0);
        assertTrue(result.getContent().stream().allMatch(h -> {
            Address address = h.getAddress();
            return address != null && address.getCity().toLowerCase().contains(cityFilter.toLowerCase());
        }));
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DirtiesContext
    void searchHotelsWhenAmenityFilterMatchesReturnsMatchingHotels() {
        // Given
        String amenityFilter = "Free WiFi";

        // When
        Page<Hotel> result = hotelRepository.searchHotels(null, null, null, null, amenityFilter, PAGEABLE);

        // Then
        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0);
        assertTrue(result.getContent().stream().allMatch(h -> h.getAmenities() != null &&
                                                              h.getAmenities().stream()
                                                                      .anyMatch(a -> a.toLowerCase()
                                                                              .contains(amenityFilter.toLowerCase()))));
        assertTrue(result.getTotalElements() >= 6);
    }

    @Test
    @DirtiesContext
    void searchHotelsWhenNoMatchReturnsEmptyPage() {
        // Given
        String nameFilter = "NonExistentHotel";

        // When
        Page<Hotel> result = hotelRepository.searchHotels(nameFilter, null, null, null, null, PAGEABLE);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }
}
