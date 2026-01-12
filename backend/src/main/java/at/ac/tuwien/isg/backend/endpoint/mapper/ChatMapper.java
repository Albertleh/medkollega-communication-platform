package at.ac.tuwien.isg.backend.endpoint.mapper;

import at.ac.tuwien.isg.backend.endpoint.dto.chat.ChatDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.MessageDto;
import at.ac.tuwien.isg.backend.entity.Chat;
import at.ac.tuwien.isg.backend.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ChatMapper {

    ChatDto toChatDto(Chat chat);

    List<ChatDto> toChatDtoList(List<Chat> chats);

    @Mapping(target = "content", source = "chatMessage.text")
    @Mapping(target = "createdAt", source = "chatMessage.sent")
    MessageDto toMessageDto(ChatMessage chatMessage, boolean sentByMe);

}
