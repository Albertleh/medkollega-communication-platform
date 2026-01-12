import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { GroupChatDto } from '../dtos/group-chat';
import { GroupChatCreationDto } from '../dtos/group-chat-creation';
import { GroupChatMessageDto } from '../dtos/group-chat-message';
import { MessageCreationDto } from '../dtos/message-creation';
import { MessageDto } from '../dtos/message';

@Injectable({ providedIn: 'root' })
export class GroupChatService {
  private readonly baseUrl = 'http://localhost:8080/api/v1/patient';

  constructor(private http: HttpClient) {}

  getAllGroupChats(patientId: string): Observable<GroupChatDto[]> {
    return this.http.get<GroupChatDto[]>(
      `${this.baseUrl}/${patientId}/groupChat`
    );
  }

  createGroupChat(patientId: string, dto: GroupChatCreationDto): Observable<GroupChatDto> {
    return this.http.post<GroupChatDto>(
      `${this.baseUrl}/${patientId}/groupChat`,
      dto
    );
  }

  // entspricht deinem Backend: GET /api/v1/patient/{patientId}/groupChat/{chatId}
  getAllMessages(patientId: string, chatId: string): Observable<GroupChatMessageDto[]> {
    return this.http.get<GroupChatMessageDto[]>(
      `${this.baseUrl}/${patientId}/groupChat/${chatId}`
    );
  }

  /**
   * Es gibt bei dir keinen GroupChat-POST für Messages.
   * Deshalb: Senden über den vorhandenen Chat-Message-Endpoint.
   * -> funktioniert nur, wenn Backend GroupChat-IDs dort akzeptiert.
   */
  sendMessageViaExistingChatEndpoint(
    patientId: string,
    chatId: string,
    dto: MessageCreationDto
  ): Observable<MessageDto> {
    return this.http.post<MessageDto>(
      `${this.baseUrl}/${patientId}/chat/${chatId}/message`,
      dto
    );
  }

  markMessagesAsReadViaExistingChatEndpoint(patientId: string, chatId: string): Observable<void> {
    return this.http.post<void>(
      `${this.baseUrl}/${patientId}/chat/${chatId}/message/read`,
      {}
    );
  }
}
