package at.ac.tuwien.isg.backend.endpoint.dto.chat;

public record ChatCreationDto(
    String patientId,
    String professionalId
) {
}
