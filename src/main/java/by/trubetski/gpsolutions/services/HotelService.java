package by.trubetski.gpsolutions.services;

import by.trubetski.gpsolutions.dto.request.RequestAmenitiesDto;
import by.trubetski.gpsolutions.dto.request.RequestCreateHotelDto;
import by.trubetski.gpsolutions.dto.request.RequestSearchHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseAllHotelsDto;
import by.trubetski.gpsolutions.dto.response.ResponseCreateHotelDto;
import by.trubetski.gpsolutions.dto.response.ResponseHotelDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface HotelService {
    Page<ResponseAllHotelsDto> findAll(Pageable pageable);
    ResponseHotelDto findById(Long id);
    Page<ResponseAllHotelsDto> search(RequestSearchHotelDto requestSearchHotelDto, Pageable pageable);
    ResponseCreateHotelDto create(RequestCreateHotelDto requestCreateHotelDto);
    void addAmenities(Long hotelId, List<String> amenities);
    Map<String, Long> getHistogramByParam(String param);
}
