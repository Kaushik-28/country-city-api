package com.kaushikdas.countrycity.service;

import com.kaushikdas.countrycity.dto.CityDetailsResponse;
import com.kaushikdas.countrycity.dto.CityPageResponse;
import com.kaushikdas.countrycity.dto.CitySummaryResponse;
import com.kaushikdas.countrycity.dto.CountryResponse;
import com.kaushikdas.countrycity.entity.City;
import com.kaushikdas.countrycity.entity.Country;
import com.kaushikdas.countrycity.exception.LocationNotFoundException;
import com.kaushikdas.countrycity.repository.InMemoryLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final InMemoryLocationRepository locationRepository;

    public List<CountryResponse> getCountries() {
        log.info("Fetching all countries");
        List<Country> countries = locationRepository.findAllCountries();
        List<CountryResponse> response = new ArrayList<>();

        for (Country country : countries) {
            response.add(new CountryResponse(
                    country.getId(),
                    country.getName()
            ));
        }

        return response;
    }

    public CityPageResponse getCitiesByCountry(Long countryId, int page, int size) {
        log.info("Fetching cities for countryId: {}, page: {}, size: {}", countryId, page, size);
        locationRepository.findCountryById(countryId)
                .orElseThrow(() -> {
                    log.warn("Country not found with id: {}", countryId);
                    return new LocationNotFoundException("Country not found with id: " + countryId);
                });

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));

        Page<City> cityPage = locationRepository.findCitiesByCountryId(countryId, pageRequest);

        List<CitySummaryResponse> content = new ArrayList<>();

        for (City city : cityPage.getContent()) {
            content.add(convertToCitySummaryResponse(city));
        }

        return new CityPageResponse(
                content,
                cityPage.getNumber(),
                cityPage.getSize(),
                cityPage.getTotalElements(),
                cityPage.getTotalPages(),
                cityPage.isFirst(),
                cityPage.isLast()
        );
    }

    public CityDetailsResponse getCityDetails(Long cityId) {
        log.info("Fetching city details for cityId: {}", cityId);
        City city = locationRepository.findCityById(cityId)
                .orElseThrow(() -> {
                    log.warn("City not found with id: {}", cityId);
                    return new LocationNotFoundException("City not found with id: " + cityId);
                });

        return convertToCityDetailsResponse(city);
    }



    private CitySummaryResponse convertToCitySummaryResponse(City city) {
        return new CitySummaryResponse(
                city.getId(),
                city.getName(),
                city.getCountry().getId(),
                city.getCountry().getName()
        );
    }

    private CityDetailsResponse convertToCityDetailsResponse(City city) {
        return new CityDetailsResponse(
                city.getId(),
                city.getName(),
                city.getCountry().getId(),
                city.getCountry().getName(),
                city.getPopulation(),
                city.getZipCode(),
                city.getDescription()
        );
    }
}