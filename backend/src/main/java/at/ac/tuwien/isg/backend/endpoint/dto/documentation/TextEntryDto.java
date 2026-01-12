package at.ac.tuwien.isg.backend.endpoint.dto.documentation;

import java.time.LocalDateTime;

public record TextEntryDto(
    String id,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String text) {
}
