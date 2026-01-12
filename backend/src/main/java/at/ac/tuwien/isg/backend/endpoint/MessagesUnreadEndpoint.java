package at.ac.tuwien.isg.backend.endpoint;

import at.ac.tuwien.isg.backend.endpoint.dto.chat.UnreadMessageDto;
import at.ac.tuwien.isg.backend.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/unreadMessages")
public class MessagesUnreadEndpoint {

    private final ChatService chatService;

    public MessagesUnreadEndpoint(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    @Operation(description = "Get all unread messages of all chats involved.")
    List<UnreadMessageDto> getUnreadMessages() {
        return chatService.getAllUnreadMessages();
    }
}
