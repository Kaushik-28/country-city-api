package com.kaushikdas.countrycity.controller;

import com.kaushikdas.countrycity.dto.CityDetailsResponse;
import com.kaushikdas.countrycity.dto.CityPageResponse;
import com.kaushikdas.countrycity.dto.CountryResponse;
import com.kaushikdas.countrycity.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Locations", description = "APIs for countries and cities")
public class LocationController {

    private final LocationService locationService;

    @GetMapping(value = "/countries",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get all countries",
            description = "Returns the list of available countries."
    )
    public List<CountryResponse> getCountries() {
        log.info("Received request to fetch all countries");
        return locationService.getCountries();
    }

    @GetMapping(value = "/countries/{countryId}/cities",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get cities by country",
            description = "Returns a paginated list of cities for the selected country."
    )
    public CityPageResponse getCitiesByCountry(
            @Parameter(description = "Country id", example = "1")
            @PathVariable
            @Min(value = 1, message = "Country id must be greater than or equal to 1")
            Long countryId,

            @Parameter(description = "Page number, starting from 0", example = "0")
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Page number must be greater than or equal to 0")
            int page,

            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "4")
            @Min(value = 1, message = "Page size must be greater than or equal to 1")
            @Max(value = 50, message = "Page size must not be greater than 50")
            int size
    ) {
        log.info("Received request to fetch cities for countryId: {}, page: {}, size: {}", countryId, page, size);
        return locationService.getCitiesByCountry(countryId, page, size);
    }

    @GetMapping("/cities/{cityId}")
    @Operation(
            summary = "Get city details",
            description = "Returns detailed information for a city by id."
    )
    public CityDetailsResponse getCityDetails(
            @Parameter(description = "City id", example = "1")
            @PathVariable
            @Min(value = 1, message = "City id must be greater than or equal to 1")
            Long cityId
    ) {
        log.info("Received request to fetch city details for cityId: {}", cityId);
        return locationService.getCityDetails(cityId);
    }
}