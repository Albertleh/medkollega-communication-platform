package at.ac.tuwien.isg.backend.endpoint;

import at.ac.tuwien.isg.backend.endpoint.dto.chat.GroupChatCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.GroupChatDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.GroupChatMessageDto;
import at.ac.tuwien.isg.backend.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/patient/{patientId}/groupChat")
public class GroupChatEndpoints {

    private final ChatService chatService;

    public GroupChatEndpoints(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    @Operation(description = "Create new group chat for patient.")
    public GroupChatDto createChat(@PathVariable("patientId") String patientId,
                                   @Valid @RequestBody GroupChatCreationDto chatDto) {
        return chatService.createGroupChat(patientId, chatDto);
    }

    @GetMapping
    @Operation(description = "Get all group chats for patient.")
    public List<GroupChatDto> getAllGroupChats(@PathVariable("patientId") String patientId) {
        return chatService.getAllGroupChats(patientId);
    }


    @GetMapping("{chatId}")
    @Operation(description = "Get all messages of group chat.")
    public List<GroupChatMessageDto> getAllMessages(@PathVariable("patientId") String patientId,
                                                    @PathVariable("chatId") String chatId) {
        return chatService.getAllGroupChatMessages(chatId);
    }

    //send message kann den gleichen endpoint verwenden wie ChatEndpoint!
}
