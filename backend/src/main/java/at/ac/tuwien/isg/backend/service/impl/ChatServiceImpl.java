package at.ac.tuwien.isg.backend.service.impl;

import at.ac.tuwien.isg.backend.endpoint.dto.chat.ChatCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.ChatDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.ChatRequestDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.GroupChatCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.GroupChatDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.GroupChatMemberDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.GroupChatMessageDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.MessageCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.MessageDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.OpenChatRequestDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.UnreadMessageDto;
import at.ac.tuwien.isg.backend.endpoint.mapper.ChatMapper;
import at.ac.tuwien.isg.backend.entity.ApplicationUser;
import at.ac.tuwien.isg.backend.entity.BaseEntity;
import at.ac.tuwien.isg.backend.entity.Chat;
import at.ac.tuwien.isg.backend.entity.ChatMessage;
import at.ac.tuwien.isg.backend.entity.ChatMessageRead;
import at.ac.tuwien.isg.backend.entity.ChatParticipant;
import at.ac.tuwien.isg.backend.entity.Patient;
import at.ac.tuwien.isg.backend.exception.HandledException;
import at.ac.tuwien.isg.backend.exception.NotFoundException;
import at.ac.tuwien.isg.backend.repository.ChatRepository;
import at.ac.tuwien.isg.backend.repository.PatientRepository;
import at.ac.tuwien.isg.backend.repository.UserRepository;
import at.ac.tuwien.isg.backend.service.ChatService;
import at.ac.tuwien.isg.backend.utils.UserContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    private final UserContext userContext;
    private final ChatRepository chatRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    public ChatServiceImpl(UserContext userContext, ChatRepository chatRepository, PatientRepository patientRepository, UserRepository userRepository, ChatMapper chatMapper) {
        this.userContext = userContext;
        this.chatRepository = chatRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.chatMapper = chatMapper;
    }


    @Override
    public ChatDto createChat(String patientId, ChatCreationDto chatCreationDto) {
        ApplicationUser user = userContext.getUser();
        Patient patient = patientRepository.getPatientById(user, chatCreationDto.patientId()).orElseThrow(NotFoundException::new);
        Chat chat = new Chat();
        chat.setPatient(patient);
        chat.setGroupDocumentation(false);
        chat.setProfessionalOne(user);


        ApplicationUser partUser = userRepository.findUserById(chatCreationDto.professionalId()).orElseThrow(NotFoundException::new);
        ChatParticipant participant = new ChatParticipant();
        participant.setChat(chat);
        participant.setUser(partUser);
        participant.setCreatedAt(LocalDate.now());

        chat.setCombinations(List.of(participant));
        chatRepository.save(chat);

        return new ChatDto(
            chat.getId(),
            chat.getCombinations().get(0).getUser().getContact().getFirstName(),
            chat.getCombinations().get(0).getUser().getContact().getLastName(),
            chat.getCombinations().get(0).getUser().getContact().getRole(),
            0,
            false,
            true
        );
    }

    @Override
    public List<ChatDto> getAllChatsOfPatient(String patientId) {
        ApplicationUser user = userContext.getUser();
        Patient patient = patientRepository.getPatientById(user, patientId).orElseThrow(NotFoundException::new);
        return chatRepository.getAllChats(user, patient).stream().filter(c -> c.getCombinations().size() == 1)
            .map(c -> mapChat(c, user)
            ).toList();
    }

    @Override
    @Transactional
    public void acceptChat(String patientId, String chatId) {
        ApplicationUser user = userContext.getUser();
        Chat chat = chatRepository.getChat(chatId).orElseThrow(NotFoundException::new);
        Patient patient = patientRepository.getPatientById(user, patientId).orElseThrow(NotFoundException::new);

        if (chat.getPatient().getInsuranceNumber() != patient.getInsuranceNumber()
            || !chat.getPatient().getDateOfBirth().equals(patient.getDateOfBirth())) {
            throw new HandledException("Patient insurance number does not match");
        }

        ChatParticipant chatParticipant = chat.getCombinations().stream()
            .filter(combination -> combination.getUser().equals(user))
            .filter(cp -> !cp.isAccepted() && !cp.isRejected())
            .findFirst().orElseThrow(() ->
                new HandledException("Chat request is not open anymore. Request has already been rejected or accepted."));

        chatParticipant.setAccepted(true);
        chatParticipant.setRejected(false);
        chatParticipant.setSeen(true);
        chatParticipant.setPatient(patient);

        chatRepository.flush();
    }

    @Override
    public OpenChatRequestDto getOpenChatRequests() {
        ApplicationUser user = userContext.getUser();
        List<Chat> openRequests = chatRepository.getOpenChatRequests(user);
        List<ChatRequestDto> requests = openRequests.stream()
            .map(c -> {
                Optional<Patient> pat = patientRepository.getPatientBySozNr(user, c.getPatient().getInsuranceNumber(), c.getPatient().getDateOfBirth());
                return new ChatRequestDto(
                    c.getId(),
                    c.getProfessionalOne().getContact().getFirstName(),
                    c.getProfessionalOne().getContact().getLastName(),
                    c.getProfessionalOne().getContact().getRole(),
                    c.getPatient().getInsuranceNumber(),
                    c.getPatient().getDateOfBirth(),
                    pat.map(BaseEntity::getId).orElse(null),
                    pat.isPresent(),
                    c.getCombinations().size() > 1,
                    c.getGroupName()
                );
            }).toList();
        return new OpenChatRequestDto(
            openRequests.stream()
                .map(Chat::getCombinations)
                .map(l -> l.get(0))
                .map(c -> !c.isSeen())
                .filter(c -> c)
                .toList().size(), requests);
    }

    @Override
    public ChatDto getChat(String patientId, String chatId) {
        ApplicationUser user = userContext.getUser();
        Chat chat = chatRepository.getChat(chatId).orElseThrow(NotFoundException::new);
        if (chat.getCombinations().size() > 1) {
            throw new HandledException("Chat is of type group chat!");
        }
        return mapChat(chat, user);
    }

    @Override
    @Transactional
    public void rejectOpenChatRequest(String chatId) {
        ApplicationUser user = userContext.getUser();
        Chat chat = chatRepository.getChat(chatId).orElseThrow(NotFoundException::new);
        if (chat.getCombinations().stream().map(ChatParticipant::getUser).noneMatch(user::equals)) {
            throw new HandledException("User is not part of chat");
        }
        ChatParticipant part = chat.getCombinations().stream()
            .filter(combination -> combination.getUser().equals(user))
            .findFirst().orElseThrow(NotFoundException::new);
        if (part.isAccepted() || part.isRejected()) {
            throw new HandledException("Chat request is not open anymore. "
                + "Request has already been rejected or accepted.");
        }
        part.setAccepted(false);
        part.setRejected(true);
        chatRepository.flush();
    }

    @Override
    @Transactional
    public void setAllRequestsToRead() {
        chatRepository.setChatRequestsToSeen(userContext.getUser());
        chatRepository.flush();
    }


    @Override
    public List<MessageDto> getAllMessages(String patientId, String chatId) {
        Chat chat = chatRepository.getChat(chatId).orElseThrow(NotFoundException::new);

        if (!chat.getCombinations().get(0).isAccepted()) {
            throw new HandledException("Chat is not accepted");
        }

        ApplicationUser user = userContext.getUser();
        return chatRepository.getAllMessagesOfChat(chat).stream().map(
            m -> mapMessage(m, user)).toList();
    }

    @Override
    public List<UnreadMessageDto> getAllUnreadMessages() {
        ApplicationUser user = userContext.getUser();
        List<ChatMessage> messages = chatRepository.getAllUnreadMessages(user);
        return messages.stream().map(m -> {
            List<String> patientData = mapNames(m.getChat(), user);
            return new UnreadMessageDto(
                m.getId(),
                m.getChat().getId(),
                m.getSender().getContact().getFirstName(),
                m.getSender().getContact().getLastName(),
                patientData.get(0),
                patientData.get(1),
                patientData.get(2),
                m.getText(),
                m.getSent(),
                m.getChat().getGroupName()
            );
        }).toList();
    }

    @Override
    public MessageDto createMessage(String chatId, MessageCreationDto messageDto) {
        ApplicationUser user = userContext.getUser();
        Chat chat = chatRepository.getChat(chatId).orElseThrow(NotFoundException::new);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(user);
        chatMessage.setChat(chat);
        chatMessage.setSent(LocalDateTime.now());
        chatMessage.setText(messageDto.text());

        chatRepository.save(chatMessage);

        getParticipantsOfChat(chat).stream()
            .filter(u -> !u.getId().equals(user.getId()))
            .forEach(u -> {
                ChatMessageRead read = new ChatMessageRead();
                read.setChatMessage(chatMessage);
                read.setUser(u);
                chatRepository.save(read);
            });
        return chatMapper.toMessageDto(chatMessage, true);
    }

    @Override
    @Transactional
    public void setMessagesToRead(String chatId) {
        Chat chat = chatRepository.getChat(chatId).orElseThrow(NotFoundException::new);
        chatRepository.setMessagesToRead(userContext.getUser(), chat);
        chatRepository.flush();
    }

    @Override
    public GroupChatDto createGroupChat(String patientId, GroupChatCreationDto chatCreationDto) {
        ApplicationUser user = userContext.getUser();
        Patient patient = patientRepository.getPatientById(user, chatCreationDto.patientId()).orElseThrow(NotFoundException::new);
        Chat chat = new Chat();
        chat.setPatient(patient);
        chat.setGroupDocumentation(false);
        chat.setProfessionalOne(user);
        chat.setGroupName(chatCreationDto.groupName());


        List<ChatParticipant> participants = new ArrayList<>();

        for (String professionalId : chatCreationDto.professionalIds()) {
            ApplicationUser partUser = userRepository.findUserById(professionalId).orElseThrow(NotFoundException::new);
            ChatParticipant participant = new ChatParticipant();
            participant.setChat(chat);
            participant.setUser(partUser);
            participant.setCreatedAt(LocalDate.now());

            participants.add(participant);

        }

        chat.setCombinations(participants);
        chatRepository.save(chat);

        return mapGroupChat(chat, user);
    }

    @Override
    public List<GroupChatDto> getAllGroupChats(String patientId) {
        ApplicationUser user = userContext.getUser();
        Patient patient = patientRepository.getPatientById(user, patientId).orElseThrow(NotFoundException::new);
        return chatRepository.getAllChats(user, patient).stream().filter(c -> c.getCombinations().size() > 1)
            .map(c -> mapGroupChat(c, user)
            ).toList();
    }

    @Override
    public List<GroupChatMessageDto> getAllGroupChatMessages(String chatId) {
        Chat chat = chatRepository.getChat(chatId).orElseThrow(NotFoundException::new);
        ApplicationUser user = userContext.getUser();

        if (!chat.getProfessionalOne().getId().equals(user.getId())
            && !chat.getCombinations().stream()
            .filter(c -> c.getUser().getId().equals(user.getId())
        ).map(ChatParticipant::isAccepted).findFirst().orElseThrow(NotFoundException::new)
        ) {
            throw new HandledException("Chat is not accepted");
        }


        return chatRepository.getAllMessagesOfChat(chat).stream().map(
            m -> mapGroupChatMessage(m, user)).toList();
    }


    private List<String> mapNames(Chat chat, ApplicationUser user) {
        if (!chat.getProfessionalOne().getId().equals(user.getId())) {
            ChatParticipant participant = chat.getCombinations().stream()
                .filter(c -> c.getUser().getId().equals(user.getId()))
                .findFirst().orElseThrow(IllegalStateException::new);
            return List.of(
                participant
                    .getPatient().getId(),
                participant
                    .getPatient().getFirstName(),
                participant
                    .getPatient().getLastName()
            );
        } else {
            return List.of(
                chat.getPatient().getId(),
                chat.getPatient().getFirstName(),
                chat.getPatient().getLastName()
            );
        }
    }

    private ChatDto mapChat(Chat c, ApplicationUser user) {
        if (c.getProfessionalOne().getId().equals(user.getId())) {
            return new ChatDto(
                c.getId(),
                c.getCombinations().get(0).getUser().getContact().getFirstName(),
                c.getCombinations().get(0).getUser().getContact().getLastName(),
                c.getCombinations().get(0).getUser().getContact().getRole(),
                chatRepository.getUnreadMessagesOfChat(c, user).size(),
                c.getCombinations().get(0).isAccepted(),
                true
            );
        } else {
            return new ChatDto(
                c.getId(),
                c.getProfessionalOne().getContact().getFirstName(),
                c.getProfessionalOne().getContact().getLastName(),
                c.getProfessionalOne().getContact().getRole(),
                chatRepository.getUnreadMessagesOfChat(c, user).size(),
                c.getCombinations().get(0).isAccepted(),
                false);
        }
    }

    private MessageDto mapMessage(ChatMessage message, ApplicationUser user) {
        if (message.getSender().getId().equals(user.getId())) {
            return new MessageDto(
                message.getId(),
                message.getText(),
                message.getSent(),
                true
            );
        } else {
            return new MessageDto(
                message.getId(),
                message.getText(),
                message.getSent(),
                false
            );
        }
    }

    private List<ApplicationUser> getParticipantsOfChat(Chat chat) {
        List<ApplicationUser> participants = new ArrayList<>(chat.getCombinations().stream()
            .map(ChatParticipant::getUser).toList());
        participants.add(chat.getProfessionalOne());
        return participants;
    }

    private GroupChatDto mapGroupChat(Chat c, ApplicationUser user) {
        if (c.getProfessionalOne().getId().equals(user.getId())) {
            return new GroupChatDto(
                c.getId(),
                c.getGroupName(),
                c.getCombinations().stream().map(
                    p -> new GroupChatMemberDto(
                        p.getUser().getContact().getFirstName(),
                        p.getUser().getContact().getLastName(),
                        p.getUser().getContact().getRole(),
                        p.isAccepted()
                    )
                ).toList(),
                chatRepository.getUnreadMessagesOfChat(c, user).size(),
                true,
                true
            );
        } else {
            List<GroupChatMemberDto> members = new ArrayList<>(c.getCombinations().stream()
                .filter(p -> !p.getUser().getId().equals(user.getId()))
                .map(
                    p -> new GroupChatMemberDto(
                        p.getUser().getContact().getFirstName(),
                        p.getUser().getContact().getLastName(),
                        p.getUser().getContact().getRole(),
                        p.isAccepted()
                    )
                ).toList());
            members.add(
                new GroupChatMemberDto(
                    c.getProfessionalOne().getContact().getFirstName(),
                    c.getProfessionalOne().getContact().getLastName(),
                    c.getProfessionalOne().getContact().getRole(),
                    true
                )
            );

            return new GroupChatDto(
                c.getId(),
                c.getGroupName(),
                members,
                chatRepository.getUnreadMessagesOfChat(c, user).size(),
                false,
                c.getCombinations().stream()
                    .filter(p -> p.getUser().getId().equals(user.getId()))
                    .map(ChatParticipant::isAccepted).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "User not part of the chat"
                        )
                    ));
        }
    }

    private GroupChatMessageDto mapGroupChatMessage(ChatMessage message, ApplicationUser user) {
        if (message.getSender().getId().equals(user.getId())) {
            return new GroupChatMessageDto(
                message.getId(),
                message.getText(),
                message.getSent(),
                null,
                null,
                true
            );
        } else {
            return new GroupChatMessageDto(
                message.getId(),
                message.getText(),
                message.getSent(),
                message.getSender().getContact().getFirstName(),
                message.getSender().getContact().getLastName(),
                false
            );
        }
    }
}



