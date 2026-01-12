package at.ac.tuwien.isg.backend.endpoint.dto.chat;

import java.time.LocalDateTime;

public record MessageDto(
    String id,
    String content,
    LocalDateTime createdAt,
    boolean sentByMe
) {
}
