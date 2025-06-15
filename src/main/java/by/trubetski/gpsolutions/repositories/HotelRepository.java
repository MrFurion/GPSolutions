package by.trubetski.gpsolutions.repositories;

import by.trubetski.gpsolutions.models.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Page<Hotel> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Hotel> findByBrandContainingIgnoreCase(String brand, Pageable pageable);

    Page<Hotel> findByAddressCityContainingIgnoreCase(String city, Pageable pageable);

    Page<Hotel> findByAddressCountryContainingIgnoreCase(String country, Pageable pageable);

    @Query("SELECT DISTINCT h FROM Hotel h " +
           "WHERE (:amenity IS NULL OR EXISTS (SELECT 1 FROM h.amenities a WHERE LOWER(a) = LOWER(:amenity)))")
    Page<Hotel> findByAmenitiesContainingValue(String amenity, Pageable pageable);


    @Query(value = "SELECT DISTINCT h.* FROM hotel h " +
                   "LEFT JOIN address a ON h.id = a.hotel_id " +
                   "WHERE (:name IS NULL OR LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
                   "AND (:brand IS NULL OR LOWER(h.brand) LIKE LOWER(CONCAT('%', :brand, '%'))) " +
                   "AND (:city IS NULL OR LOWER(a.city) LIKE LOWER(CONCAT('%', :city, '%'))) " +
                   "AND (:country IS NULL OR LOWER(a.country) LIKE LOWER(CONCAT('%', :country, '%'))) " +
                   "AND (:amenities IS NULL OR EXISTS (SELECT 1 FROM hotel_amenities ha WHERE ha.hotel_id = h.id AND LOWER(ha.amenity) = LOWER(:amenities)))",
            nativeQuery = true)
    Page<Hotel> searchHotels(String name, String brand, String city, String country, String amenities, Pageable pageable);

    @Query(value = "SELECT a.city, COUNT(h.id) FROM hotel h LEFT JOIN address a ON h.id = a.hotel_id GROUP BY a.city", nativeQuery = true)
    List<Object[]> countByCityRaw();

    @Query(value = "SELECT h.brand, COUNT(h.id) FROM hotel h GROUP BY h.brand", nativeQuery = true)
    List<Object[]> countByBrandRaw();

    @Query(value = "SELECT a.country, COUNT(h.id) FROM hotel h LEFT JOIN address a ON h.id = a.hotel_id GROUP BY a.country", nativeQuery = true)
    List<Object[]> countByCountryRaw();

    @Query(value = "SELECT ha.amenity, COUNT(h.id) FROM hotel h LEFT JOIN hotel_amenities ha ON h.id = ha.hotel_id GROUP BY ha.amenity", nativeQuery = true)
    List<Object[]> countByAmenitiesRaw();

    default Map<String, Long> countByCity() {
        return countByCityRaw().stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> ((Number) row[1]).longValue()
                ));
    }

    default Map<String, Long> countByBrand() {
        return countByBrandRaw().stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> ((Number) row[1]).longValue()
                ));
    }

    default Map<String, Long> countByCountry() {
        return countByCountryRaw().stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> ((Number) row[1]).longValue()
                ));
    }

    default Map<String, Long> countByAmenities() {
        return countByAmenitiesRaw().stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> ((Number) row[1]).longValue()
                ));
    }
}
