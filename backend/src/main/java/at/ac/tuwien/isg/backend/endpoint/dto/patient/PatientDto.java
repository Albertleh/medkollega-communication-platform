package at.ac.tuwien.isg.backend.endpoint.dto.patient;

import java.time.LocalDate;

public record PatientDto(
    String id,
    Integer insuranceNumber,
    LocalDate dateOfBirth,
    String firstName,
    String lastName
) {
}
