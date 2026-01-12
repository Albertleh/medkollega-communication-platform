package at.ac.tuwien.isg.backend.endpoint.dto.chat;

import java.time.LocalDateTime;

//if message is sent by me: senderFirstName and SenderLastName will be null
public record GroupChatMessageDto(
    String id,
    String content,
    LocalDateTime createdAt,
    String senderFirstName,
    String senderLastName,
    boolean sentByMe
) {
}
