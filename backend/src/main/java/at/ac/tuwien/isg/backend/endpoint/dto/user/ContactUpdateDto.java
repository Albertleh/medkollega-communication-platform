package at.ac.tuwien.isg.backend.endpoint.dto.user;

import jakarta.validation.constraints.NotNull;

public record ContactUpdateDto(
    @NotNull
    String firstName,
    @NotNull
    String lastName,
    @NotNull
    String zipCode,
    @NotNull
    String city,
    boolean placesAvailable
) {
}

