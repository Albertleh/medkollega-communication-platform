package at.ac.tuwien.isg.backend.endpoint.dto.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PatientModificationDto(
    @NotNull
    Integer insuranceNumber,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth,
    String firstName,
    String lastName,
    String zipCode,
    String city,
    String streetName,
    String streetNumber,
    String description

){

}
