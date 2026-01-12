package at.ac.tuwien.isg.backend.service;

import at.ac.tuwien.isg.backend.endpoint.dto.chat.ChatCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.ChatDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.GroupChatCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.GroupChatDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.GroupChatMessageDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.MessageCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.MessageDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.OpenChatRequestDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.UnreadMessageDto;

import java.util.List;

public interface ChatService {

    ChatDto createChat(String patientId, ChatCreationDto chatCreationDto);

    List<ChatDto> getAllChatsOfPatient(String patientId);

    ChatDto getChat(String patientId, String chatId);



    OpenChatRequestDto getOpenChatRequests();

    void acceptChat(String patientId, String chatId);

    void rejectOpenChatRequest(String chatId);

    void setAllRequestsToRead();


    List<UnreadMessageDto> getAllUnreadMessages();


    List<MessageDto> getAllMessages(String patientId, String chatId);

    MessageDto createMessage(String chatId, MessageCreationDto messageDto);

    void setMessagesToRead(String chatId);



    GroupChatDto createGroupChat(String patientId, GroupChatCreationDto chatCreationDto);


    List<GroupChatDto> getAllGroupChats(String patientId);

    List<GroupChatMessageDto> getAllGroupChatMessages(String chatId);




    //update message

    //delete message

    //add new Use

}
