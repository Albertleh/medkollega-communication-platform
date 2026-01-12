package at.ac.tuwien.isg.backend.endpoint.dto.chat;

import java.util.List;

public record GroupChatCreationDto(
    String patientId,
    List<String> professionalIds,
    String groupName
) {
}
