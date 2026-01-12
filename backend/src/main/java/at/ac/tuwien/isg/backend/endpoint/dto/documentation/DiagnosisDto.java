package at.ac.tuwien.isg.backend.endpoint.dto.documentation;

import java.time.LocalDate;

public record DiagnosisDto(
    String id,
    LocalDate createdAt,
    String diagnosisText,
    String icdCode,
    LocalDate validUntil
){
}
