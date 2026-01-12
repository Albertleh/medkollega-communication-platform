package at.ac.tuwien.isg.backend.endpoint.dto.chat;

import java.util.List;

public record GroupChatDto(
    String id,
    String name,
    List<GroupChatMemberDto> members,
    int unreadMessages,
    boolean createdByMe,
    boolean accepted
) {
}
