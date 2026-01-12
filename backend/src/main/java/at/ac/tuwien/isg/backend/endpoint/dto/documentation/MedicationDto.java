package at.ac.tuwien.isg.backend.endpoint.dto.documentation;

import java.time.LocalDate;

public record MedicationDto(
    String id,
    LocalDate createdAt,
    String medication,
    String morning,
    String midday,
    String evening,
    String night,
    String note
) {

}
