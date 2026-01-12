package at.ac.tuwien.isg.backend.endpoint.dto.documentation;

import java.time.LocalDate;

public record DiagnosisToPastDto(
    LocalDate validUntil
) {
}
