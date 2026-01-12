import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { ChatRequestDto } from '../dtos/chat-request';
import { ChatAcceptanceDto } from '../dtos/chat-acceptance';
import { ChatDto } from '../dtos/chat';

@Injectable({ providedIn: 'root' })
export class ChatRequestService {

  private readonly chatRequestBaseUrl = 'http://localhost:8080/api/v1/chatRequest';
  private readonly patientChatBaseUrl = 'http://localhost:8080/api/v1/patient';

  constructor(private http: HttpClient) {}


  getAllOpenRequests(): Observable<ChatRequestDto[]> {
    console.log('CALL → getAllOpenRequests');

    return this.http.get<ChatRequestDto[]>(this.chatRequestBaseUrl).pipe(
      tap(res => console.log('RESPONSE ← getAllOpenRequests', res))
    );
  }

  accept(chatId: string, patientId: string): Observable<void> {
    const body: ChatAcceptanceDto = { patientId };

    console.log('CALL → accept', { chatId, patientId });

    return this.http.post<void>(
      `${this.chatRequestBaseUrl}/${chatId}/accept`,
      body
    ).pipe(
      tap(() => console.log('RESPONSE ← accept OK'))
    );
  }

  reject(chatId: string): Observable<void> {
    console.log('CALL → reject', chatId);

    return this.http.post<void>(
      `${this.chatRequestBaseUrl}/${chatId}/reject`,
      {}
    ).pipe(
      tap(() => console.log('RESPONSE ← reject OK'))
    );
  }

  markAllAsRead(): Observable<void> {
    console.log('CALL → markAllAsRead');

    return this.http.post<void>(
      `${this.chatRequestBaseUrl}/read`,
      {}
    ).pipe(
      tap(() => console.log('RESPONSE ← markAllAsRead OK'))
    );
  }

  getAllChats(patientId: string): Observable<ChatDto[]> {
    const url = `${this.patientChatBaseUrl}/${patientId}/chat`;

    console.log('CALL → getAllChats', url);

    return this.http.get<ChatDto[]>(url).pipe(
      tap(res => console.log('RESPONSE ← getAllChats', res))
    );
  }

  getChat(patientId: string, chatId: string): Observable<ChatDto> {
    const url = `${this.patientChatBaseUrl}/${patientId}/chat/${chatId}`;
    return this.http.get<ChatDto>(url);
  }

  createChat(patientId: string, professionalId: string): Observable<ChatDto> {
    const url = `${this.patientChatBaseUrl}/${patientId}/chat`;

    return this.http.post<ChatDto>(url, {
      patientId,
      professionalId
    });
  }

}
