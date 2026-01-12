package at.ac.tuwien.isg.backend.endpoint.dto.patient;

import java.time.LocalDate;

public record PatientDetailDto(
    String id,
    Integer insuranceNumber,
    LocalDate dateOfBirth,
    String firstName,
    String lastName,
    String zipCode,
    String city,
    String streetName,
    String streetNumber,
    String description
) {
}
