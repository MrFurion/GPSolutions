package by.trubetski.gpsolutions.controllers;

import by.trubetski.gpsolutions.dto.request.RequestCreateHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseAllHotelsDto;
import by.trubetski.gpsolutions.dto.response.ResponseCreateHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseHotelDto;
import by.trubetski.gpsolutions.services.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import static by.trubetski.gpsolutions.util.SearchQuery.buildQuery;

@RestController
@RequestMapping("/property-view")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @Operation(summary = "Get all hotels with pagination", description = "Retrieve a paginated list of all hotels.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of hotels",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)))
    })
    @GetMapping("/hotels")
    public ResponseEntity<Page<ResponseAllHotelsDto>> findAllHotels(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ResponseAllHotelsDto> responseAllHotelDto = hotelService.findAll(pageable);
        return ResponseEntity.ok(responseAllHotelDto);
    }

    @Operation(summary = "Get hotel by ID", description = "Retrieve a specific hotel by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the hotel",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseHotelDto.class))),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @GetMapping("/hotels/{id}")
    public ResponseEntity<ResponseHotelDto> findById(@PathVariable Long id) {
        ResponseHotelDto responseHotelDto = hotelService.findById(id);
        return ResponseEntity.ok(responseHotelDto);
    }

    @Operation(summary = "Search hotels by criteria", description = "Search hotels based on name, brand, city, country, or amenities with pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved search results",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<Page<ResponseAllHotelsDto>> searchHotel(@RequestParam(required = false) String name,
                                                                  @RequestParam(required = false) String brand,
                                                                  @RequestParam(required = false) String city,
                                                                  @RequestParam(required = false) String country,
                                                                  @RequestParam(required = false) String amenities,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        String query = buildQuery(name, brand, city, country, amenities);
        Page<ResponseAllHotelsDto> responseAllHotelDto = hotelService.search(query, pageable);
        return ResponseEntity.ok(responseAllHotelDto);
    }

    @Operation(summary = "Create a new hotel", description = "Create a new hotel with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hotel successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseCreateHotelDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/hotels")
    public ResponseEntity<ResponseCreateHotelDto> createHotel(@Validated @RequestBody RequestCreateHotelDto requestCreateHotelDto) {
        ResponseCreateHotelDto responseCreateHotelDto = hotelService.create(requestCreateHotelDto);
        URI location = URI.create("/hotels/" + responseCreateHotelDto.getId());
        return ResponseEntity.created(location).body(responseCreateHotelDto);
    }

    @Operation(summary = "Add amenities to a hotel", description = "Add a list of amenities to an existing hotel by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Amenities successfully added",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<String> addAmenities(@PathVariable Long id,
                                               @RequestBody List<String> amenities) {
        hotelService.addAmenities(id, amenities);
        URI location = URI.create("/hotels/");
        return ResponseEntity.created(location).body("Amenities add successfully");
    }

    @Operation(summary = "Get histogram of hotels by parameter", description = "Retrieve a histogram (count) of hotels grouped by the specified parameter: brand, city, country, or amenities.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved histogram",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)))
    })
    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Long>> getHistogram(@PathVariable String param) {
        Map<String, Long> histogram = hotelService.getHistogramByParam(param);
        return ResponseEntity.ok(histogram);
    }
}
