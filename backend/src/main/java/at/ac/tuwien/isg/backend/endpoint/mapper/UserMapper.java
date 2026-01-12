package at.ac.tuwien.isg.backend.endpoint.mapper;

import at.ac.tuwien.isg.backend.endpoint.dto.user.ContactDto;
import at.ac.tuwien.isg.backend.endpoint.dto.user.ContactUpdateDto;
import at.ac.tuwien.isg.backend.endpoint.dto.user.UserDto;
import at.ac.tuwien.isg.backend.entity.ApplicationUser;
import at.ac.tuwien.isg.backend.entity.UserContact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface UserMapper {

    UserDto toUserDto(ApplicationUser user);

    List<UserDto> toUserDto(List<ApplicationUser> users);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "professionalRoleUser", source = "user.role")
    ContactDto toContactDto(UserContact user, String userId);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateEntity(ContactUpdateDto dto, @MappingTarget UserContact userContact);
}

