package at.ac.tuwien.isg.backend.endpoint.dto.chat;

import at.ac.tuwien.isg.backend.common.ProfessionalRoleUser;

import java.time.LocalDate;

public record ChatRequestDto(
    String chatId,
    String senderFirstName,
    String senderLastName,
    ProfessionalRoleUser senderProfessionalRoleUser,
    Integer patientInsuranceNumber,
    LocalDate patientDateOfBirth,
    String patientId,
    boolean patientCreated,
    boolean groupChat,
    String groupChatName
) {
}


