package at.ac.tuwien.isg.backend.endpoint.dto.chat;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record OpenChatRequestDto(
    @Schema(description = "Unseen chat requests.")
    int newRequests,
    List<ChatRequestDto> chatRequests
) {
}
