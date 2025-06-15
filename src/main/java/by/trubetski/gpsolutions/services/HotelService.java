package by.trubetski.gpsolutions.services;

import by.trubetski.gpsolutions.dto.request.RequestCreateHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseAllHotelsDto;
import by.trubetski.gpsolutions.dto.response.ResponseCreateHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseHotelDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Service interface for managing hotel-related operations.
 */
public interface HotelService {

    /**
     * Retrieves a paginated list of all hotels.
     *
     * @param pageable Pagination information (page number, size, and sort criteria).
     * @return A {@link Page} of {@link ResponseAllHotelsDto} containing the list of hotels.
     */
    Page<ResponseAllHotelsDto> findAll(Pageable pageable);

    /**
     * Retrieves a hotel by its unique identifier.
     *
     * @param id The ID of the hotel to retrieve.
     * @return A {@link ResponseHotelDto} object representing the hotel details.
     */
    ResponseHotelDto findById(Long id);

    /**
     * Searches for hotels based on a query string and pagination criteria.
     *
     * @param query    The search query string.
     * @param pageable Pagination information (page number, size, and sort criteria).
     * @return A {@link Page} of {@link ResponseAllHotelsDto} containing the search results.
     */
    Page<ResponseAllHotelsDto> search(String query, Pageable pageable);

    /**
     * Creates a new hotel based on the provided request data.
     *
     * @param requestCreateHotelDto The request object containing hotel creation details.
     * @return A {@link ResponseCreateHotelDto} object with the created hotel's details.
     */
    ResponseCreateHotelDto create(RequestCreateHotelDto requestCreateHotelDto);

    /**
     * Adds a list of amenities to an existing hotel.
     *
     * @param hotelId   The ID of the hotel to add amenities to.
     * @param amenities The list of amenities to be added.
     */
    void addAmenities(Long hotelId, List<String> amenities);

    /**
     * Retrieves a histogram (count) of hotels grouped by the specified parameter.
     *
     * @param param The parameter to group by (e.g., "city", "amenities").
     * @return A {@link Map} where the key is the parameter value (e.g., "Minsk") and
     * the value is the count of hotels (e.g., 1).
     */
    Map<String, Long> getHistogramByParam(String param);
}
