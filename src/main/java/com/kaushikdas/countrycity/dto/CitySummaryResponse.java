package com.kaushikdas.countrycity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "City summary response")
public record CitySummaryResponse(

        @Schema(description = "Unique city id", example = "1")
        Long id,

        @Schema(description = "City name", example = "Kolkata")
        String name,

        @Schema(description = "Country id", example = "1")
        Long countryId,

        @Schema(description = "Country name", example = "India")
        String countryName
) {
}