package com.kaushikdas.countrycity.entity;

import lombok.*;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Country {

    @NonNull
    @EqualsAndHashCode.Include
    private final Long id;

    @NonNull
    private final String name;
}
