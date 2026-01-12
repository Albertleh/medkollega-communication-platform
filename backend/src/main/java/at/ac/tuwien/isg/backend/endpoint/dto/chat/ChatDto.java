package at.ac.tuwien.isg.backend.endpoint.dto.chat;

import at.ac.tuwien.isg.backend.common.ProfessionalRoleUser;

public record ChatDto(
    String id,
    String partnerFirstName,
    String partnerLastName,
    ProfessionalRoleUser partnerRole,
    int unreadMessages,
    boolean accepted,
    boolean createdByMe
) {
}
