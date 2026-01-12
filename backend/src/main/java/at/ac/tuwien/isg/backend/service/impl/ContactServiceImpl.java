package at.ac.tuwien.isg.backend.service.impl;

import at.ac.tuwien.isg.backend.endpoint.dto.user.ContactDto;
import at.ac.tuwien.isg.backend.endpoint.dto.user.ContactUpdateDto;
import at.ac.tuwien.isg.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.isg.backend.entity.ApplicationUser;
import at.ac.tuwien.isg.backend.entity.Chat;
import at.ac.tuwien.isg.backend.entity.UserContact;
import at.ac.tuwien.isg.backend.exception.NotFoundException;
import at.ac.tuwien.isg.backend.repository.ChatRepository;
import at.ac.tuwien.isg.backend.repository.PatientRepository;
import at.ac.tuwien.isg.backend.repository.UserRepository;
import at.ac.tuwien.isg.backend.service.ContactService;
import at.ac.tuwien.isg.backend.utils.UserContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private final UserContext userContext;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ChatRepository chatRepository;
    private final PatientRepository patientRepository;

    public ContactServiceImpl(UserContext userContext, UserRepository userRepository, UserMapper userMapper, ChatRepository chatRepository, PatientRepository patientRepository) {
        this.userContext = userContext;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.chatRepository = chatRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public ContactDto getPersonalSettings() {
        ApplicationUser user = userContext.getUser();
        return userMapper.toContactDto(userRepository.getUserContact(user).orElseThrow(NotFoundException::new), user.getId());
    }

    @Override
    @Transactional
    public ContactDto updatePersonalSettings(ContactUpdateDto contactDto) {
        ApplicationUser user = userContext.getUser();
        UserContact entity = userRepository.getUserContact(user).orElseThrow(NotFoundException::new);
        userMapper.updateEntity(contactDto, entity);
        userRepository.flush();
        return userMapper.toContactDto(entity, user.getId());
    }

    @Override
    public List<ContactDto> getAllUsersOfTheSystem(String search, String patientId) {
        ApplicationUser user = userContext.getUser();
        String withSearch = search == null ? "" : search;
        List<ApplicationUser> users = userRepository.getApplicationUsers(user, withSearch);

        if (patientId != null && !patientId.isEmpty()) {
            List<String> usersIds = chatRepository.getAllChats(user, patientRepository.getPatientById(user, patientId).orElseThrow(NotFoundException::new))
                .stream().map(c -> mapProfessionalId(user, c)).toList();
            return users.stream()
                .filter(u -> !usersIds.contains(u.getId()))
                .map(u -> userMapper.toContactDto(u.getContact(), u.getId())).toList();
        }
        return users.stream().map(u -> userMapper.toContactDto(u.getContact(), u.getId())).toList();

    }



    private String mapProfessionalId(ApplicationUser user, Chat chat) {
        if (chat.getProfessionalOne().getId().equals(user.getId())) {
            return chat.getCombinations().get(0).getUser().getId();
        } else {
            return chat.getProfessionalOne().getId();
        }
    }


}
