package at.ac.tuwien.isg.backend.endpoint.dto.documentation;

public record MedicationCreationDto(
    String medication,
    String morning,
    String midday,
    String evening,
    String night,
    String note
) {
}
