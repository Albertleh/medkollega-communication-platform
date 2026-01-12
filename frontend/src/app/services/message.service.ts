import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MessageDto } from '../dtos/message';
import { MessageCreationDto } from '../dtos/message-creation';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private readonly baseUrl = 'http://localhost:8080/api/v1/patient';
  private readonly unreadUrl = 'http://localhost:8080/api/v1/unreadMessages';

  constructor(private http: HttpClient) {}


  getAllMessages(patientId: string, chatId: string): Observable<MessageDto[]> {
    const url = `${this.baseUrl}/${patientId}/chat/${chatId}/message`;
    return this.http.get<MessageDto[]>(url);
  }

  sendMessage(
    patientId: string,
    chatId: string,
    dto: MessageCreationDto
  ): Observable<MessageDto> {
    const url = `${this.baseUrl}/${patientId}/chat/${chatId}/message`;
    return this.http.post<MessageDto>(url, dto);
  }

  markMessagesAsRead(patientId: string, chatId: string): Observable<void> {
    const url = `${this.baseUrl}/${patientId}/chat/${chatId}/message/read`;
    return this.http.post<void>(url, {});
  }


  getUnreadMessages(): Observable<any[]> {
    return this.http.get<any[]>(this.unreadUrl);
  }
}
