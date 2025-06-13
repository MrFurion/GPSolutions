package by.trubetski.gpsolutions.repositories;

import by.trubetski.gpsolutions.models.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("SELECT DISTINCT h FROM Hotel h " +
           "LEFT JOIN h.address a " +
           "LEFT JOIN h.amenities am " +
           "WHERE (:name IS NULL OR LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
           "AND (:brand IS NULL OR LOWER(h.brand) LIKE LOWER(CONCAT('%', :brand, '%'))) " +
           "AND (:city IS NULL OR LOWER(a.city) LIKE LOWER(CONCAT('%', :city, '%'))) " +
           "AND (:country IS NULL OR LOWER(a.country) LIKE LOWER(CONCAT('%', :country, '%'))) " +
           "AND ((:amenities IS NULL) OR " +
           "     (:amenities = 'Free parking' AND am.freeParking = TRUE) OR " +
           "     (:amenities = 'Free WiFi' AND am.freeWifi = TRUE) OR " +
           "     (:amenities = 'Non-smoking rooms' AND am.nonSmokingRooms = TRUE) OR " +
           "     (:amenities = 'Concierge' AND am.concierge = TRUE) OR " +
           "     (:amenities = 'On-site restaurant' AND am.onSiteRestaurant = TRUE) OR " +
           "     (:amenities = 'Fitness center' AND am.fitnessCenter = TRUE) OR " +
           "     (:amenities = 'Pet-friendly rooms' AND am.petFriendlyRooms = TRUE) OR " +
           "     (:amenities = 'Room service' AND am.roomService = TRUE) OR " +
           "     (:amenities = 'Business center' AND am.businessCenter = TRUE) OR " +
           "     (:amenities = 'Meeting rooms' AND am.meetingRooms = TRUE))")
    Page<Hotel> searchHotels(
            @Param("name") String name,
            @Param("brand") String brand,
            @Param("city") String city,
            @Param("country") String country,
            @Param("amenities") String amenities,
            Pageable pageable);
}
