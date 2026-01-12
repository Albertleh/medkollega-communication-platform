package at.ac.tuwien.isg.backend.repository;

import at.ac.tuwien.isg.backend.entity.ApplicationUser;
import at.ac.tuwien.isg.backend.entity.Chat;
import at.ac.tuwien.isg.backend.entity.ChatMessage;
import at.ac.tuwien.isg.backend.entity.ChatMessageRead;
import at.ac.tuwien.isg.backend.entity.Patient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static at.ac.tuwien.isg.backend.entity.QChat.chat;
import static at.ac.tuwien.isg.backend.entity.QChatMessage.chatMessage;
import static at.ac.tuwien.isg.backend.entity.QChatMessageRead.chatMessageRead;
import static at.ac.tuwien.isg.backend.entity.QChatParticipant.chatParticipant;

@Repository
public class ChatRepository extends AbstractBaseRepository {

    public List<Chat> getAllChats(ApplicationUser applicationUser, Patient patient) {
        return getResults(query(chat)
            .leftJoin(chat.combinations, chatParticipant)
            .where((chatParticipant.user.eq(applicationUser).and(chatParticipant.patient.eq(patient)))
                    .or((chat.professionalOne.eq(applicationUser).and(chat.patient.eq(patient))))));
    }

    public Optional<Chat> getChat(String chatId) {
        return getResult(query(chat).where(chat.id.eq(chatId)));
    }

    public List<Chat> getOpenChatRequests(ApplicationUser user) {
        return getResults(query(chat)
            .leftJoin(chat.combinations, chatParticipant)
            .where(chatParticipant.user.eq(user), chatParticipant.accepted.eq(false),
                chatParticipant.rejected.eq(false)));
    }

    public List<ChatMessage> getAllMessagesOfChat(Chat chat) {
        return getResults(query(chatMessage).where(chatMessage.chat.eq(chat)));
    }

    public List<ChatMessage> getAllUnreadMessages(ApplicationUser user) {
        return getResults(query(chatMessage)
            .leftJoin(chatMessageRead).on(chatMessageRead.chatMessage.eq(chatMessage))
            .leftJoin(chatMessage.chat, chat)
            .leftJoin(chat.combinations, chatParticipant)
            .where(chatMessageRead.user.eq(user), chatMessageRead.readDate.isNull(),
                chat.professionalOne.eq(user)
                    .or(chatParticipant.user.eq(user).and(chatParticipant.accepted.isTrue())))
            .orderBy(chatMessage.sent.desc()));
    }

    public long setMessagesToRead(ApplicationUser user, Chat c) {
        return updateClause(chatMessageRead)
            .set(chatMessageRead.readDate, LocalDateTime.now())
            .where(chatMessageRead.chatMessage.chat.eq(c), chatMessageRead.user.eq(user)).execute();

    }


    public long setChatRequestsToSeen(ApplicationUser user) {
        return updateClause(chatParticipant)
            .set(chatParticipant.seen, true)
            .where(chatParticipant.user.eq(user)).execute();
    }


    public List<ChatMessageRead> getUnreadMessagesOfChat(Chat c, ApplicationUser user) {
        return getResults(query(chatMessageRead)
            .leftJoin(chatMessageRead.chatMessage, chatMessage)
            .leftJoin(chatMessage.chat, chat)
            .where(chat.eq(c), chatMessageRead.user.eq(user), chatMessageRead.readDate.isNull())
            .orderBy(chatMessage.sent.desc()));

    }


}
