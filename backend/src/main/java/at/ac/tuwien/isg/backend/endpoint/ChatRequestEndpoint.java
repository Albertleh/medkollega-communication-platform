package at.ac.tuwien.isg.backend.endpoint;

import at.ac.tuwien.isg.backend.endpoint.dto.chat.ChatAcceptanceDto;
import at.ac.tuwien.isg.backend.endpoint.dto.chat.OpenChatRequestDto;
import at.ac.tuwien.isg.backend.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/chatRequest")
public class ChatRequestEndpoint {

    private final ChatService chatService;

    public ChatRequestEndpoint(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    @Operation(description = "Get all open chat requests.")
    public OpenChatRequestDto getAllOpenChatRequests() {
        return chatService.getOpenChatRequests();
    }

    @PostMapping("{chatId}/accept")
    @Operation(description = "Accept a chat request. Only possible if chat with same social insurance number exists.")
    void acceptChat(@PathVariable(value = "chatId") String chatId, @RequestBody ChatAcceptanceDto chatRequestDto) {
        chatService.acceptChat(chatRequestDto.patientId(), chatId);
    }

    @PostMapping("{chatId}/reject")
    @Operation(description = "Reject a chat request.")
    void rejectChat(@PathVariable(value = "chatId") String chatId) {
        chatService.rejectOpenChatRequest(chatId);
    }

    @PostMapping("read")
    @Operation(description = "Set all requests to read.")
    void setAllRequestsToRead() {
        chatService.setAllRequestsToRead();
    }
}
