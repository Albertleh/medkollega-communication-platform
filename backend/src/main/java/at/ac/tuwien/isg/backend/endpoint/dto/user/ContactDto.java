package at.ac.tuwien.isg.backend.endpoint.dto.user;

import at.ac.tuwien.isg.backend.common.ProfessionalRoleUser;

public record ContactDto(
    String userId,
    String firstName,
    String lastName,
    ProfessionalRoleUser professionalRoleUser,
    String zipCode,
    String city,
    boolean placesAvailable
) {
}
