package com.kaushikdas.countrycity.controller;

import com.kaushikdas.countrycity.dto.CityDetailsResponse;
import com.kaushikdas.countrycity.dto.CityPageResponse;
import com.kaushikdas.countrycity.dto.CitySummaryResponse;
import com.kaushikdas.countrycity.dto.CountryResponse;
import com.kaushikdas.countrycity.service.LocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocationController.class)
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LocationService locationService;

    @Test
    void getCountriesShouldReturnCountries() throws Exception {
        List<CountryResponse> countries = List.of(
                new CountryResponse(1L, "India"),
                new CountryResponse(2L, "Luxembourg")
        );

        when(locationService.getCountries()).thenReturn(countries);

        mockMvc.perform(get("/countries"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("India"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Luxembourg"));

        verify(locationService).getCountries();
    }

    @Test
    void getCitiesByCountryShouldReturnPaginatedCities() throws Exception {
        List<CitySummaryResponse> cities = List.of(
                new CitySummaryResponse(1L, "Kolkata", 1L, "India"),
                new CitySummaryResponse(2L, "Bangalore", 1L, "India")
        );

        CityPageResponse pageResponse = new CityPageResponse(
                cities,
                0,
                5,
                15,
                3,
                true,
                false
        );

        when(locationService.getCitiesByCountry(1L, 0, 5))
                .thenReturn(pageResponse);

        mockMvc.perform(get("/countries/1/cities")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Kolkata"))
                .andExpect(jsonPath("$.content[0].countryId").value(1))
                .andExpect(jsonPath("$.content[0].countryName").value("India"))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(5))
                .andExpect(jsonPath("$.totalElements").value(15))
                .andExpect(jsonPath("$.totalPages").value(3))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(false));

        verify(locationService).getCitiesByCountry(1L, 0, 5);
    }

    @Test
    void getCitiesByCountryShouldUseDefaultPaginationValues() throws Exception {
        CityPageResponse pageResponse = new CityPageResponse(
                List.of(),
                0,
                4,
                0,
                0,
                true,
                true
        );

        when(locationService.getCitiesByCountry(1L, 0, 4))
                .thenReturn(pageResponse);

        mockMvc.perform(get("/countries/1/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(4));

        verify(locationService).getCitiesByCountry(1L, 0, 4);
    }

    @Test
    void getCityDetailsShouldReturnCityDetails() throws Exception {
        CityDetailsResponse cityDetails = new CityDetailsResponse(
                1L,
                "Kolkata",
                1L,
                "India",
                14_850_000L,
                "700001",
                "Kolkata is a major city in eastern India."
        );

        when(locationService.getCityDetails(1L)).thenReturn(cityDetails);

        mockMvc.perform(get("/cities/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Kolkata"))
                .andExpect(jsonPath("$.countryId").value(1))
                .andExpect(jsonPath("$.countryName").value("India"))
                .andExpect(jsonPath("$.population").value(14850000))
                .andExpect(jsonPath("$.zipCode").value("700001"))
                .andExpect(jsonPath("$.description").value("Kolkata is a major city in eastern India."));

        verify(locationService).getCityDetails(1L);
    }

    @Test
    void getCityDetailsShouldReturnBadRequestForInvalidCityId() throws Exception {
        mockMvc.perform(get("/cities/0"))
                .andExpect(status().isBadRequest());
    }

    //TODO more tests are required for Bad request for invalid inputs like invalid country/city Id, pagination details etc.


}