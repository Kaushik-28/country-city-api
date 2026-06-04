package com.kaushikdas.countrycity.repository;

import com.kaushikdas.countrycity.entity.City;
import com.kaushikdas.countrycity.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryLocationRepository {

    private final List<Country> countries = new ArrayList<>();
    private final Map<Long, List<City>> citiesByCountryId = new HashMap<>();

    public InMemoryLocationRepository() {
        Country india = new Country(1L, "India");
        Country luxembourg = new Country(2L, "Luxembourg");
        Country germany = new Country(3L, "Germany");
        Country france = new Country(4L, "France");

        countries.add(india);
        countries.add(luxembourg);
        countries.add(germany);
        countries.add(france);

        List<City> indianCities = new ArrayList<>();

        indianCities.add(new City(1L, "Kolkata", india, 14_850_000L, "700001",
                "Kolkata is a major city in eastern India, known for its culture and history."));

        indianCities.add(new City(2L, "Bangalore", india, 13_600_000L, "560001",
                "Bangalore is a major technology hub in India."));

        indianCities.add(new City(3L, "Mumbai", india, 21_600_000L, "400001",
                "Mumbai is one of India's largest cities and a major financial centre."));

        indianCities.add(new City(4L, "Delhi", india, 32_900_000L, "110001",
                "Delhi is the capital territory of India and a major administrative centre."));

        indianCities.add(new City(5L, "Chennai", india, 11_900_000L, "600001",
                "Chennai is a major city in southern India and an important industrial and cultural centre."));

        indianCities.add(new City(6L, "Hyderabad", india, 10_800_000L, "500001",
                "Hyderabad is a major technology and business hub in southern India."));

        indianCities.add(new City(7L, "Pune", india, 7_200_000L, "411001",
                "Pune is known for its education, technology, and manufacturing sectors."));

        indianCities.add(new City(8L, "Ahmedabad", india, 8_400_000L, "380001",
                "Ahmedabad is a major city in western India with strong commercial and textile industries."));

        indianCities.add(new City(9L, "Jaipur", india, 4_100_000L, "302001",
                "Jaipur is the capital of Rajasthan and is known for its heritage and architecture."));

        indianCities.add(new City(10L, "Lucknow", india, 3_900_000L, "226001",
                "Lucknow is the capital of Uttar Pradesh and is known for its history and culture."));

        indianCities.add(new City(11L, "Kochi", india, 2_100_000L, "682001",
                "Kochi is a major port city in Kerala and an important commercial centre."));

        indianCities.add(new City(12L, "Indore", india, 3_200_000L, "452001",
                "Indore is a major city in central India known for trade, education, and food culture."));

        indianCities.add(new City(13L, "Bhubaneswar", india, 1_200_000L, "751001",
                "Bhubaneswar is the capital of Odisha and is known for its planned city layout and temples."));

        indianCities.add(new City(14L, "Guwahati", india, 1_100_000L, "781001",
                "Guwahati is a major city in north-eastern India and an important regional gateway."));

        indianCities.add(new City(15L, "Chandigarh", india, 1_200_000L, "160001",
                "Chandigarh is a planned city and serves as the capital of Punjab and Haryana."));

        citiesByCountryId.put(india.getId(), indianCities);

        List<City> luxembourgCities = new ArrayList<>();

        luxembourgCities.add(new City(16L, "Luxembourg City", luxembourg, 135_000L, "L-1111",
                "Luxembourg City is the capital of Luxembourg."));

        luxembourgCities.add(new City(17L, "Esch-sur-Alzette", luxembourg, 36_000L, "L-4001",
                "Esch-sur-Alzette is one of the largest cities in Luxembourg."));

        luxembourgCities.add(new City(18L, "Differdange", luxembourg, 29_000L, "L-4501",
                "Differdange is a city in south-western Luxembourg."));

        citiesByCountryId.put(luxembourg.getId(), luxembourgCities);

        List<City> germanCities = new ArrayList<>();

        germanCities.add(new City(19L, "Berlin", germany, 3_700_000L, "10115",
                "Berlin is the capital city of Germany."));

        germanCities.add(new City(20L, "Munich", germany, 1_500_000L, "80331",
                "Munich is a major city in southern Germany."));

        germanCities.add(new City(21L, "Hamburg", germany, 1_900_000L, "20095",
                "Hamburg is a major port city in northern Germany."));

        citiesByCountryId.put(germany.getId(), germanCities);

        List<City> frenchCities = new ArrayList<>();

        frenchCities.add(new City(22L, "Paris", france, 11_200_000L, "75001",
                "Paris is the capital of France."));

        frenchCities.add(new City(23L, "Lyon", france, 1_700_000L, "69001",
                "Lyon is a major city in France known for its history and cuisine."));

        citiesByCountryId.put(france.getId(), frenchCities);
    }

    public List<Country> findAllCountries() {
        return new ArrayList<>(countries);
    }

    public Optional<Country> findCountryById(Long countryId) {
        return countries.stream()
                .filter(country -> country.getId().equals(countryId))
                .findFirst();
    }

    public Page<City> findCitiesByCountryId(Long countryId, PageRequest pageRequest) {
        List<City> citiesForCountry = citiesByCountryId.get(countryId);

        if (citiesForCountry == null) {
            citiesForCountry = new ArrayList<>();
        }

        int start = (int) pageRequest.getOffset();

        if (start >= citiesForCountry.size()) {
            return new PageImpl<>(
                    List.of(),
                    pageRequest,
                    citiesForCountry.size()
            );
        }
        int end = Math.min(start + pageRequest.getPageSize(), citiesForCountry.size());

        List<City> pageContent = new ArrayList<>();

        if (start < citiesForCountry.size()) {
            pageContent = new ArrayList<>(citiesForCountry.subList(start, end));
        }

        return new PageImpl<>(pageContent, pageRequest, citiesForCountry.size());
    }

    public Optional<City> findCityById(Long cityId) {
        for (List<City> cities : citiesByCountryId.values()) {
            for (City city : cities) {
                if (city.getId().equals(cityId)) {
                    return Optional.of(city);
                }
            }
        }

        return Optional.empty();
    }
}