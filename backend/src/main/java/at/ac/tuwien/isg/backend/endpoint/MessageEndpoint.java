package at.ac.tuwien.isg.backend.endpoint;

import at.ac.tuwien.isg.backend.endpoint.dto.chat.MessageCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.MessageDto;
import at.ac.tuwien.isg.backend.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/patient/{patientId}/chat/{chatId}/message")
public class MessageEndpoint {

    private final ChatService chatService;

    public MessageEndpoint(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    @Operation(description = "Get all messages related to a chat")
    List<MessageDto> getAllMessages(@PathVariable(value = "patientId") String patientId,
                                    @PathVariable(value = "chatId") String chatId) {
        return chatService.getAllMessages(patientId, chatId);
    }

    @PostMapping
    @Operation(description = "Create a new message in a chat.")
    MessageDto createMessage(@PathVariable(value = "patientId") String patientId,
                             @PathVariable(value = "chatId") String chatId,
                             @RequestBody MessageCreationDto messageCreationDto) {
        return chatService.createMessage(chatId, messageCreationDto);
    }

    @PostMapping("read")
    @Operation(description = "Set all messages of given chat to read.")
    void setMessagesToRead(@PathVariable(value = "patientId") String patientId,
                           @PathVariable(value = "chatId") String chatId) {
        chatService.setMessagesToRead(chatId);
    }




}
