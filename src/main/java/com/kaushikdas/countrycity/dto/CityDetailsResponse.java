package com.kaushikdas.countrycity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "City details response")
public record CityDetailsResponse(

        @Schema(description = "Unique city id", example = "1")
        Long id,

        @Schema(description = "City name", example = "Kolkata")
        String name,

        @Schema(description = "Country id", example = "1")
        Long countryId,

        @Schema(description = "Country name", example = "India")
        String countryName,

        @Schema(description = "city population", example = "148500")
        Long population,

        @Schema(description = "City zip code", example = "700001")
        String zipCode,

        @Schema(
                description = "Short city description",
                example = "Kolkata is a major city in eastern India, known for its culture and history."
        )
        String description
    )
{
}