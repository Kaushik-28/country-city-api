package com.kaushikdas.countrycity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Country response")
public record CountryResponse(

        @Schema(description = "Unique country id", example = "1")
        Long id,

        @Schema(description = "Country name", example = "India")
        String name
    )
{
}