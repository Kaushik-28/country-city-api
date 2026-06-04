package com.kaushikdas.countrycity.service;

import com.kaushikdas.countrycity.dto.CityDetailsResponse;
import com.kaushikdas.countrycity.dto.CityPageResponse;
import com.kaushikdas.countrycity.dto.CountryResponse;
import com.kaushikdas.countrycity.entity.City;
import com.kaushikdas.countrycity.entity.Country;
import com.kaushikdas.countrycity.exception.LocationNotFoundException;
import com.kaushikdas.countrycity.repository.InMemoryLocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private InMemoryLocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @Test
    void getCountriesShouldReturnAllCountries() {
        Country india = new Country(1L, "India");
        Country germany = new Country(2L, "Germany");

        when(locationRepository.findAllCountries())
                .thenReturn(List.of(india, germany));

        List<CountryResponse> response = locationService.getCountries();

        assertThat(response).hasSize(2);

        assertThat(response.get(0).id()).isEqualTo(1L);
        assertThat(response.get(0).name()).isEqualTo("India");

        assertThat(response.get(1).id()).isEqualTo(2L);
        assertThat(response.get(1).name()).isEqualTo("Germany");

        verify(locationRepository).findAllCountries();
    }

    @Test
    void getCitiesByCountryShouldReturnPaginatedCities() {
        Country india = new Country(1L, "India");

        City kolkata = new City(
                1L,
                "Kolkata",
                india,
                14_850_000L,
                "700001",
                "Kolkata is a major city in eastern India."
        );

        City bangalore = new City(
                2L,
                "Bangalore",
                india,
                13_600_000L,
                "560001",
                "Bangalore is a major technology hub in India."
        );

        when(locationRepository.findCountryById(1L))
                .thenReturn(Optional.of(india));

        when(locationRepository.findCitiesByCountryId(any(Long.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(kolkata, bangalore),
                        org.springframework.data.domain.PageRequest.of(0, 5),
                        15));

        CityPageResponse response = locationService.getCitiesByCountry(1L, 0, 5);

        assertThat(response.page()).isEqualTo(0);
        assertThat(response.size()).isEqualTo(5);
        assertThat(response.totalElements()).isEqualTo(15);
        assertThat(response.totalPages()).isEqualTo(3);
        assertThat(response.first()).isTrue();
        assertThat(response.last()).isFalse();

        assertThat(response.content()).hasSize(2);

        assertThat(response.content().get(0).id()).isEqualTo(1L);
        assertThat(response.content().get(0).name()).isEqualTo("Kolkata");
        assertThat(response.content().get(0).countryId()).isEqualTo(1L);
        assertThat(response.content().get(0).countryName()).isEqualTo("India");

        assertThat(response.content().get(1).id()).isEqualTo(2L);
        assertThat(response.content().get(1).name()).isEqualTo("Bangalore");

        verify(locationRepository).findCountryById(1L);
        verify(locationRepository).findCitiesByCountryId(any(Long.class), any(PageRequest.class));
    }

    @Test
    void getCitiesByCountryShouldPassPageableToRepository() {
        Country india = new Country(1L, "India");

        when(locationRepository.findCountryById(1L))
                .thenReturn(Optional.of(india));

        when(locationRepository.findCitiesByCountryId(any(Long.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(),
                        org.springframework.data.domain.PageRequest.of(1, 10),
                        0));

        locationService.getCitiesByCountry(1L, 1, 10);

        ArgumentCaptor<PageRequest> pageableCaptor = ArgumentCaptor.forClass(PageRequest.class);

        verify(locationRepository).findCitiesByCountryId(
                org.mockito.ArgumentMatchers.eq(1L),
                pageableCaptor.capture()
        );

        Pageable pageable = pageableCaptor.getValue();

        assertThat(pageable.getPageNumber()).isEqualTo(1);
        assertThat(pageable.getPageSize()).isEqualTo(10);
        assertThat(pageable.getSort().getOrderFor("id")).isNotNull();
    }

    @Test
    void getCitiesByCountryShouldThrowExceptionWhenCountryNotFound() {
        when(locationRepository.findCountryById(99L))
                .thenReturn(Optional.empty());

        LocationNotFoundException exception = assertThrows(
                LocationNotFoundException.class,
                () -> locationService.getCitiesByCountry(99L, 0, 5)
        );

        assertEquals("Country not found with id: 99", exception.getMessage());

        verify(locationRepository).findCountryById(99L);
        verify(locationRepository, never()).findCitiesByCountryId(any(Long.class), any(PageRequest.class));
    }

    @Test
    void getCityDetailsShouldReturnCityDetails() {
        Country india = new Country(1L, "India");

        City city = new City(
                1L,
                "Kolkata",
                india,
                14_850_000L,
                "700001",
                "Kolkata is a major city in eastern India."
        );

        when(locationRepository.findCityById(1L))
                .thenReturn(Optional.of(city));

        CityDetailsResponse response = locationService.getCityDetails(1L);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Kolkata");
        assertThat(response.countryId()).isEqualTo(1L);
        assertThat(response.countryName()).isEqualTo("India");
        assertThat(response.population()).isEqualTo(14_850_000L);
        assertThat(response.zipCode()).isEqualTo("700001");
        assertThat(response.description()).isEqualTo("Kolkata is a major city in eastern India.");

        verify(locationRepository).findCityById(1L);
    }

    //TODO more tests are required for invalid city id etc.
}