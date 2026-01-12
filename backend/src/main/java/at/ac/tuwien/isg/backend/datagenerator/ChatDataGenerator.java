package at.ac.tuwien.isg.backend.datagenerator;

import at.ac.tuwien.isg.backend.entity.ApplicationUser;
import at.ac.tuwien.isg.backend.entity.Chat;
import at.ac.tuwien.isg.backend.entity.ChatMessage;
import at.ac.tuwien.isg.backend.entity.ChatMessageRead;
import at.ac.tuwien.isg.backend.entity.ChatParticipant;
import at.ac.tuwien.isg.backend.entity.Patient;
import at.ac.tuwien.isg.backend.repository.ChatRepository;
import at.ac.tuwien.isg.backend.repository.PatientRepository;
import at.ac.tuwien.isg.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static at.ac.tuwien.isg.backend.datagenerator.ChatData.CONVERSATION_ONE;
import static at.ac.tuwien.isg.backend.datagenerator.ChatData.CONVERSATION_THREE;
import static at.ac.tuwien.isg.backend.datagenerator.ChatData.CONVERSATION_TWO;

@Component
public class ChatDataGenerator {

    private final PatientDataGenerator patientDataGenerator;
    private final UserDataGenerator userDataGenerator;
    private final ChatRepository chatRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;


    public ChatDataGenerator(PatientDataGenerator patientDataGenerator, UserDataGenerator userDataGenerator, ChatRepository chatRepository, PatientRepository patientRepository, UserRepository userRepository) {
        this.patientDataGenerator = patientDataGenerator;
        this.userDataGenerator = userDataGenerator;
        this.chatRepository = chatRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void generateChats() {
        userDataGenerator.generateUsers();
        ApplicationUser user = userRepository.findUserByEmail("doctor@email.com").get();
        Optional<Patient> patient = patientRepository.getPatientBySozNr(user, 2344, LocalDate.of(2002, 2, 22));
        if (patient.isEmpty()) {
            patientDataGenerator.generatePatients();
        }
        if (chatRepository.getAllChats(user,  patientRepository.getPatientBySozNr(user, 2344, LocalDate.of(2002, 2, 22)).get())
            .isEmpty()) {
            //fullChat
            ApplicationUser therapist = userRepository.findUserByEmail("therapist@email.com").get();
            ApplicationUser internist = userRepository.findUserByEmail("internist@email.com").get();
            ApplicationUser nurse = userRepository.findUserByEmail("nurse@email.com").get();

            chatWithMessages(user, therapist, 2344, LocalDate.of(2002, 2, 22), CONVERSATION_ONE);
            chatWithMessages(user, internist, 2347, LocalDate.of(1974, 4, 19), CONVERSATION_TWO);
            chatWithMessages(user, nurse, 2346, LocalDate.of(1985, 11, 3), CONVERSATION_THREE);

            //groupChat empty
            createGroupChat(user, 2344, LocalDate.of(2002, 2, 22), List.of(internist, therapist), "Zentrum 1040");


            //chatRequest waiting for answer

            chatOpenRequest(user, List.of(internist), 2344, LocalDate.of(2002, 2, 22), null);

            //chatRequests open
            chatOpenRequest(internist, List.of(user), 2345, LocalDate.of(1998, 7, 12), null);
            chatOpenRequest(internist, List.of(user, therapist), 2347, LocalDate.of(1974, 4, 19), "Zentrum 1050");

            //chatRequest user not created
            chatOpenRequestRandomUser(internist, user);

            //chatRequestGroup


        }

    }

    private void chatWithMessages(ApplicationUser user, ApplicationUser therapist, int sozNr, LocalDate date,
                                  List<ChatData.Message> messages) {
        Chat chat = new Chat();
        chat.setPatient(patientRepository.getPatientBySozNr(user, sozNr, date).get());
        chat.setProfessionalOne(user);
        chatRepository.save(chat);

        ChatParticipant part = new ChatParticipant();
        part.setUser(therapist);
        part.setChat(chat);
        part.setSeen(true);
        part.setAccepted(true);
        part.setRejected(false);
        part.setPatient(patientRepository.getPatientBySozNr(therapist, sozNr, date).get());
        chatRepository.save(part);

        chat.setCombinations(List.of(part));
        chatRepository.flush();


        for (int i = 0; i < messages.size(); i++) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setChat(chat);
            chatMessage.setSender(i % 2 == 0 ? user : therapist);
            chatMessage.setText(messages.get(i).text());
            chatMessage.setSent(messages.get(i).date());
            chatRepository.save(chatMessage);

            ChatMessageRead chatMessageRead = new ChatMessageRead();
            chatMessageRead.setChatMessage(chatMessage);
            chatMessageRead.setUser(i % 2 == 0 ? therapist : user);
            chatMessageRead.setReadDate(messages.get(i).read() ? messages.get(i).date().plusDays(1) : null);
            chatRepository.save(chatMessageRead);
        }
    }

    private void chatOpenRequest(ApplicationUser user, List<ApplicationUser> participants, int sozNr, LocalDate date, String groupName) {
        Chat chat = new Chat();
        chat.setPatient(patientRepository.getPatientBySozNr(user, sozNr, date).get());
        chat.setProfessionalOne(user);
        chat.setGroupName(groupName);
        chatRepository.save(chat);

        List<ChatParticipant> chatParticipants = new ArrayList<>();
        for (ApplicationUser participant : participants) {
            ChatParticipant part = new ChatParticipant();
            part.setUser(participant);
            part.setChat(chat);
            part.setSeen(false);
            part.setAccepted(false);
            part.setRejected(false);
            chatRepository.save(part);
            chatParticipants.add(part);
        }

        chat.setCombinations(chatParticipants);
        chatRepository.flush();
    }

    private void chatOpenRequestRandomUser(ApplicationUser userChatCreator, ApplicationUser userChatRequest) {
        Chat chat = new Chat();
        List<Patient> patients = patientRepository.getPatients(userChatCreator, "");
        Optional<Patient> patient = patients.stream().filter(p -> p.getInsuranceNumber() > 3000).findFirst();
        if (patient.isPresent()) {
            chat.setPatient(patient.get());
            chat.setProfessionalOne(userChatCreator);
            chatRepository.save(chat);

            ChatParticipant part = new ChatParticipant();
            part.setUser(userChatRequest);
            part.setChat(chat);
            part.setSeen(false);
            part.setAccepted(false);
            part.setRejected(false);
            chatRepository.save(part);

            chat.setCombinations(List.of(part));
            chatRepository.flush();
        }
    }

    private void createGroupChat(ApplicationUser user, int sozNr, LocalDate date, List<ApplicationUser> members, String groupName) {
        Chat chat = new Chat();
        chat.setPatient(patientRepository.getPatientBySozNr(user, sozNr, date).get());
        chat.setProfessionalOne(user);
        chat.setGroupName(groupName);
        chatRepository.save(chat);

        List<ChatParticipant> participants = new ArrayList<>();
        for (int i = 0; i < members.size(); i++) {
            ChatParticipant part = new ChatParticipant();
            part.setUser(members.get(i));
            part.setChat(chat);
            part.setPatient(i % 2 == 0 ? patientRepository.getPatientBySozNr(members.get(i), sozNr, date).get() : null);
            part.setSeen(true);
            part.setAccepted(i % 2 == 0);
            part.setRejected(i % 2 == 1);

            chatRepository.save(part);
            participants.add(part);
        }

        chat.setCombinations(participants);
        chatRepository.flush();
    }
}
