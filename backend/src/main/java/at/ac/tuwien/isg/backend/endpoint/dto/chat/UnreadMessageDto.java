package at.ac.tuwien.isg.backend.endpoint.dto.chat;

import java.time.LocalDateTime;

public record UnreadMessageDto(
    String id,
    String chatId,
    String senderFirstName,
    String senderLastName,
    String patientId,
    String patientFirstName,
    String patientLastName,
    String content,
    LocalDateTime createdAt,
    String groupName
) {
}
