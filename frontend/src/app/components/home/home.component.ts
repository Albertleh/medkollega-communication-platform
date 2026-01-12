import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { forkJoin, of } from 'rxjs';
import { catchError, map, switchMap } from 'rxjs/operators';

import { MessageService } from '../../services/message.service';
import { PatientService } from 'src/app/services/patient.service';
import { UserService } from 'src/app/services/user.service.service';
import { UnreadMessageFrontendDto } from 'src/app/dtos/unread-message';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  standalone: false
})
export class HomeComponent implements OnInit {

  todayMessages: UnreadMessageFrontendDto[] = [];
  yesterdayMessages: UnreadMessageFrontendDto[] = [];
  weekMessages: UnreadMessageFrontendDto[] = [];

  userName = ''; // 🔹 kommt vom Backend
  private patientNameCache: Record<string, string> = {};

  constructor(
    private messageService: MessageService,
    private patientService: PatientService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadUserName();
    this.loadUnreadMessages();
  }

  get unreadCount(): number {
    return (
      this.todayMessages.length +
      this.yesterdayMessages.length +
      this.weekMessages.length
    );
  }

  get greeting(): string {
    const hour = new Date().getHours();
    if (hour < 11) return 'Guten Morgen';
    if (hour < 17) return 'Guten Tag';
    return 'Guten Abend';
  }

  private loadUserName(): void {
    this.userService.getPersonalSettings().subscribe({
      next: contact => {
        this.userName = contact.firstName;
      },
      error: err => {
        console.error('LOAD USER ERROR', err);
        this.userName = '';
      }
    });
  }

  openUnreadMessage(msg: UnreadMessageFrontendDto): void {
    this.router.navigate(['/patients', msg.patientId], {
      queryParams: { chatId: msg.chatId }
    });
  }

  loadUnreadMessages(): void {
    this.messageService.getUnreadMessages().pipe(
      map(messages => messages.map(m => this.mapToFrontend(m))),

      switchMap(mapped => {
        const missingIds = Array.from(new Set(mapped.map(m => m.patientId)))
          .filter(id => id && !this.patientNameCache[id]);

        if (missingIds.length === 0) {
          return of(mapped);
        }

        return forkJoin(
          missingIds.map(id =>
            this.patientService.getPatient(id).pipe(
              map(p => ({ id, name: `${p.firstName} ${p.lastName}` })),
              catchError(() => of({ id, name: '' }))
            )
          )
        ).pipe(
          map(results => {
            for (const r of results) this.patientNameCache[r.id] = r.name;
            return mapped;
          })
        );
      }),

      map(mapped =>
        mapped.map(m => ({
          ...m,
          patientName: this.patientNameCache[m.patientId] ?? ''
        }) as UnreadMessageFrontendDto)
      )
    ).subscribe({
      next: msgs => this.groupByDate(msgs),
      error: err => console.error('UNREAD MESSAGE ERROR', err)
    });
  }

  markAsRead(msg: UnreadMessageFrontendDto): void {
    this.messageService
      .markMessagesAsRead(msg.patientId, msg.chatId)
      .subscribe(() => this.loadUnreadMessages());
  }

  markAllAsRead(): void {
    const uniqueChats = new Map<string, UnreadMessageFrontendDto>();

    [...this.todayMessages, ...this.yesterdayMessages, ...this.weekMessages]
      .forEach(m => uniqueChats.set(m.chatId, m));

    forkJoin(
      Array.from(uniqueChats.values()).map(m =>
        this.messageService.markMessagesAsRead(m.patientId, m.chatId)
      )
    ).subscribe(() => this.loadUnreadMessages());
  }

  private mapToFrontend(m: any): UnreadMessageFrontendDto {
    const initials =
      (m.senderFirstName?.[0] ?? '') +
      (m.senderLastName?.[0] ?? '');

    return {
      id: m.id,
      chatId: m.chatId,
      patientId: m.patientId,
      senderName: `${m.senderFirstName} ${m.senderLastName}`,
      initials,
      preview: m.content,
      createdAt: m.createdAt,
      unread: true,
      color: this.colorFor(initials)
    } as UnreadMessageFrontendDto;
  }

  private groupByDate(messages: UnreadMessageFrontendDto[]): void {
    const today = new Date();
    const yesterday = new Date();
    yesterday.setDate(today.getDate() - 1);

    this.todayMessages = [];
    this.yesterdayMessages = [];
    this.weekMessages = [];

    for (const msg of messages) {
      const d = new Date(msg.createdAt);

      if (this.sameDay(d, today)) {
        msg.time = this.formatTime(d);
        this.todayMessages.push(msg);
      } else if (this.sameDay(d, yesterday)) {
        msg.time = 'Gestern';
        this.yesterdayMessages.push(msg);
      } else {
        msg.time = this.formatWeekday(d);
        this.weekMessages.push(msg);
      }
    }
  }

  private sameDay(a: Date, b: Date): boolean {
    return a.toDateString() === b.toDateString();
  }

  private formatTime(d: Date): string {
    const diffMin = Math.floor((Date.now() - d.getTime()) / 60000);
    if (diffMin < 1) return 'Gerade eben';
    if (diffMin < 60) return `Vor ${diffMin} Minuten`;
    return d.toLocaleTimeString('de-DE', { hour: '2-digit', minute: '2-digit' });
  }

  private formatWeekday(d: Date): string {
    return d.toLocaleDateString('de-DE', { weekday: 'long' });
  }

  private colorFor(seed: string): string {
    const colors = ['#BEE7DA', '#CDB9F3', '#BFD3E8', '#FFD6A5', '#FBC4C4'];
    return colors[seed.charCodeAt(0) % colors.length];
  }
}
