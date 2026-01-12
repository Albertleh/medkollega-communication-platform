package at.ac.tuwien.isg.backend.endpoint.dto.chat;

import at.ac.tuwien.isg.backend.common.ProfessionalRoleUser;

public record GroupChatMemberDto(
    String partnerFirstName,
    String partnerLastName,
    ProfessionalRoleUser partnerRole,
    boolean accepted
) {
}
