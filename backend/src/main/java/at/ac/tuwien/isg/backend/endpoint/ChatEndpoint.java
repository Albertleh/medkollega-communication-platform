package at.ac.tuwien.isg.backend.endpoint;

import at.ac.tuwien.isg.backend.endpoint.dto.chat.ChatCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.ChatDto;
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
@RequestMapping(value = "/api/v1/patient/{patientId}/chat")
public class ChatEndpoint {

    private final ChatService chatService;

    public ChatEndpoint(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    @Operation(description = "Get all chats connecting to patient")
    public List<ChatDto> getAllChats(@PathVariable(value = "patientId") String patientId) {
        return chatService.getAllChatsOfPatient(patientId);
    }

    @GetMapping("{chatId}")
    @Operation(description = "Get information of chat.")
    public ChatDto getChat(@PathVariable(value = "patientId") String patientId,
                           @PathVariable(value = "chatId") String chatId) {
        return chatService.getChat(patientId, chatId);
    }

    @PostMapping
    @Operation(description = "Create new chat for patient.")
    public ChatDto createChat(@PathVariable("patientId") String patientId,
                              @Valid @RequestBody ChatCreationDto chatDto) {
        return chatService.createChat(patientId, chatDto);
    }


}
