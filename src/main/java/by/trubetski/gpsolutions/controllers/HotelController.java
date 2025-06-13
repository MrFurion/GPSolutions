package by.trubetski.gpsolutions.controllers;

import by.trubetski.gpsolutions.dto.request.RequestAmenitiesDto;
import by.trubetski.gpsolutions.dto.request.RequestCreateHotelDto;
import by.trubetski.gpsolutions.dto.request.RequestSearchHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseAllHotelsDto;
import by.trubetski.gpsolutions.dto.response.ResponseCreateHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseHotelDto;
import by.trubetski.gpsolutions.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/hotel")
    public ResponseEntity<Page<ResponseAllHotelsDto>> findAllHotels(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ResponseAllHotelsDto> responseAllHotelDto = hotelService.findAll(pageable);
        return ResponseEntity.ok(responseAllHotelDto);
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<ResponseHotelDto> findById(@PathVariable Long id) {
        ResponseHotelDto responseHotelDto = hotelService.findById(id);
        return ResponseEntity.ok(responseHotelDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ResponseAllHotelsDto>> searchHotel(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size,
                                                              @Validated @RequestBody RequestSearchHotelDto requestSearchHotelDto) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ResponseAllHotelsDto> responseAllHotelDto = hotelService.search(requestSearchHotelDto, pageable);
        return ResponseEntity.ok(responseAllHotelDto);
    }

    @PostMapping("/hotels")
    public ResponseEntity<ResponseCreateHotelDto> createHotel(@Validated @RequestBody RequestCreateHotelDto requestCreateHotelDto) {
        ResponseCreateHotelDto responseCreateHotelDto = hotelService.create(requestCreateHotelDto);
        URI location = URI.create("/hotels/" + responseCreateHotelDto.getId());
        return ResponseEntity.created(location).body(responseCreateHotelDto);
    }

    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<String> addAmenities(@PathVariable Long id,
                                               @RequestBody List<String> amenities) {
        hotelService.addAmenities(id, amenities );
        URI location = URI.create("/hotels/");
        return ResponseEntity.created(location).body("Amenities add successfully");
    }

    @GetMapping("/{param}")
    public ResponseEntity<Map<String, Long>> getHistogram(@PathVariable String param) {
        Map<String, Long> histogram = hotelService.getHistogramByParam(param.toLowerCase());
        return ResponseEntity.ok(histogram);
    }
}
