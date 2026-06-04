package com.kaushikdas.countrycity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Paginated response for cities")
public record CityPageResponse(

        @Schema(description = "Cities available in the current page")
        List<CitySummaryResponse> content,

        @Schema(description = "Current page number", example = "0")
        int page,

        @Schema(description = "Requested page size", example = "5")
        int size,

        @Schema(description = "Total number of cities", example = "7")
        long totalElements,

        @Schema(description = "Total number of pages", example = "2")
        int totalPages,

        @Schema(description = "Whether this is the first page", example = "true")
        boolean first,

        @Schema(description = "Whether this is the last page", example = "false")
        boolean last
) {
}