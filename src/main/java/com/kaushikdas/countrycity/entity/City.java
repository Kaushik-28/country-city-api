package com.kaushikdas.countrycity.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class City {

    @NonNull
    @EqualsAndHashCode.Include
    private final Long id;

    @NonNull
    private final String name;

    @NonNull
    private final Country country;

    private final Long population;

    private final String zipCode;

    private final String description;
}
