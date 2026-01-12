import { CommonModule } from "@angular/common";
import { Component, Input, OnChanges, SimpleChanges, ViewChild, ElementRef, OnDestroy } from "@angular/core";
import { Output, EventEmitter } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { forkJoin, Subscription, interval, EMPTY } from "rxjs";
import { switchMap, startWith, catchError } from "rxjs/operators";

import { ChatDto } from "src/app/dtos/chat";
import { MessageCreationDto } from "src/app/dtos/message-creation";

import { GroupChatDto } from "src/app/dtos/group-chat";
import { GroupChatMessageDto } from "src/app/dtos/group-chat-message";

import { ChatRequestService } from "src/app/services/chat.service.service";
import { MessageService } from "src/app/services/message.service";
import { GroupChatService } from "src/app/services/group-chat.service";

import { CreateDoctorChatModalComponent } from "./create-doctor-chat-modal/create-doctor-chat-modal.component";
import { CreateGroupChatModalComponent } from "./create-group-chat-modal/create-group-chat-modal.component";

type ChatType = 'direct' | 'group';

type UnifiedMessage = {
  id: string;
  content: string;
  createdAt: any;
  sentByMe: boolean;
  senderFirstName?: string | null;
  senderLastName?: string | null;
};

@Component({
  selector: 'app-patient-right-column',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    CreateDoctorChatModalComponent,
    CreateGroupChatModalComponent
  ],
  templateUrl: './patient-right-column.component.html'
})
export class PatientRightColumnComponent implements OnChanges, OnDestroy {

  @Input() patientId!: string;
  @Input() openChatId: string | null = null;
  @Input() openChatType: ChatType | null = null;

  @Output() chatOpen = new EventEmitter<void>();
  @Output() chatClose = new EventEmitter<void>();

  @ViewChild('scrollContainer')
  private scrollContainer!: ElementRef;

  directChats: ChatDto[] = [];
  groupChats: GroupChatDto[] = [];

  selectedChatType: ChatType | null = null;
  selectedDirectChat: ChatDto | null = null;
  selectedGroupChat: GroupChatDto | null = null;

  messages: UnifiedMessage[] = [];
  newMessage = '';
  loading = false;

  showCreateDoctorModal = false;
  showCreateGroupModal = false;

  showGroupMembers = false;

  private messagePollSub?: Subscription;
  private directMetaPollSub?: Subscription;

  constructor(
    private chatService: ChatRequestService,
    private messageService: MessageService,
    private groupChatService: GroupChatService
  ) {}

  ngOnDestroy(): void {
    this.stopPolling();
  }

  private listPollSub?: Subscription;

  private stopPolling(): void {
    this.messagePollSub?.unsubscribe();
    this.messagePollSub = undefined;

    this.directMetaPollSub?.unsubscribe();
    this.directMetaPollSub = undefined;

    this.listPollSub?.unsubscribe();
  this.listPollSub = undefined;
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.stopPolling();

    if (changes['patientId']) {
      if (!this.patientId) return;
      this.resetSelection();
      this.loadAllChats();
      return;
    }

    if (changes['openChatId'] || changes['openChatType']) {
      this.tryOpenChatById();
    }
  }

  private resetSelection(): void {
    this.selectedChatType = null;
    this.selectedDirectChat = null;
    this.selectedGroupChat = null;
    this.messages = [];
    this.newMessage = '';
    this.showGroupMembers = false;
  }

  toggleGroupMembers(): void {
    this.showGroupMembers = !this.showGroupMembers;
  }

  getDirectStatusText(chat: ChatDto): string {
    if (chat.accepted) return 'Akzeptiert';
    return chat.createdByMe ? 'Wartet auf Annahme' : 'Anfrage offen';
  }

  canSendDirect(): boolean {
    return !!this.selectedDirectChat?.accepted;
  }

  openCreateDoctorModal(): void { 
    this.showCreateDoctorModal = true; 
  }
  closeCreateDoctorModal(): void { 
    this.showCreateDoctorModal = false; 
  }
  onChatCreated(chat: ChatDto): void {
    this.showCreateDoctorModal = false;
    this.directChats = [chat, ...this.directChats];
  }

  openCreateGroupModal(): void { 
    this.showCreateGroupModal = true;
  }
  closeCreateGroupModal(): void { 
    this.showCreateGroupModal = false; 
  }
  onGroupChatCreated(chat: GroupChatDto): void {
    this.showCreateGroupModal = false;
    this.groupChats = [chat, ...this.groupChats];
  }

  private loadAllChats(): void {
    this.loading = true;

    forkJoin({
      direct: this.chatService.getAllChats(this.patientId),
      group: this.groupChatService.getAllGroupChats(this.patientId)
    }).subscribe({
      next: ({ direct, group }) => {
        this.directChats = direct ?? [];
        this.groupChats = group ?? [];
        this.loading = false;

        this.startPollingDirectChatList();
        this.tryOpenChatById();
      },
      error: err => {
        console.error('LOAD CHATS ERROR', err);
        this.loading = false;
      }
    });
  }

  private tryOpenChatById(): void {
    if (!this.openChatId) return;
    const id = String(this.openChatId);

    if (this.openChatType === 'direct') {
      const c = this.directChats.find(x => String(x.id) === id);
      if (c) this.openDirectChat(c);
      return;
    }
    if (this.openChatType === 'group') {
      const g = this.groupChats.find(x => String(x.id) === id);
      if (g) this.openGroupChat(g);
      return;
    }

    const direct = this.directChats.find(x => String(x.id) === id);
    if (direct) { 
      this.openDirectChat(direct); return; 
    }

    const group = this.groupChats.find(x => String(x.id) === id);
    if (group) this.openGroupChat(group);
  }


  private startPollingDirectChatMessages(): void {
    this.messagePollSub?.unsubscribe();

    this.messagePollSub = interval(2500).pipe(
      startWith(0),
      switchMap(() => {
        if (!this.selectedDirectChat || this.selectedChatType !== 'direct') return EMPTY;
        return this.messageService.getAllMessages(this.patientId, this.selectedDirectChat.id);
      }),
      catchError(err => {
        console.error('POLL DIRECT MESSAGES ERROR', err);
        return EMPTY;
      })
    ).subscribe(all => {
      if (!this.selectedDirectChat || this.selectedChatType !== 'direct') return;

      this.messages = (all ?? []).map(m => ({
        id: m.id,
        content: m.content,
        createdAt: m.createdAt,
        sentByMe: m.sentByMe
      }));

      setTimeout(() => this.scrollToBottom(), 50);
    });
  }

  private startPollingDirectChatAcceptance(chatId: string): void {
    this.directMetaPollSub?.unsubscribe();

    this.directMetaPollSub = interval(2500).pipe(
      startWith(0),
      switchMap(() => this.chatService.getChat(this.patientId, chatId)),
      catchError(err => {
        console.error('POLL CHAT META ERROR', err);
        return EMPTY;
      })
    ).subscribe(updated => {
      // update selection
      if (this.selectedDirectChat && String(this.selectedDirectChat.id) === String(updated.id)) {
        this.selectedDirectChat.accepted = updated.accepted;
        this.selectedDirectChat.unreadMessages = updated.unreadMessages;
        this.selectedDirectChat.createdByMe = updated.createdByMe;
      }

      // update list item
      const idx = this.directChats.findIndex(c => String(c.id) === String(updated.id));
      if (idx >= 0) this.directChats[idx] = updated;

      // sobald akzeptiert: meta-poll stoppen, messages poll starten
      if (updated.accepted) {
        this.directMetaPollSub?.unsubscribe();
        this.directMetaPollSub = undefined;
        this.startPollingDirectChatMessages();
      }
    });
  }


  openDirectChat(chat: ChatDto): void {
    this.stopPolling();

    this.selectedChatType = 'direct';
    this.selectedDirectChat = chat;
    this.selectedGroupChat = null;
    this.showGroupMembers = false;
    this.chatOpen.emit();

    // UI: wenn nicht akzeptiert -> keine Messages pollen, sondern accepted-status pollen
    if (!chat.accepted) {
      this.messages = [];
      this.startPollingDirectChatAcceptance(chat.id);
      return;
    }

    // accepted -> messages laden + pollen
    this.messageService.getAllMessages(this.patientId, chat.id).subscribe(res => {
      this.messages = (res ?? []).map(m => ({
        id: m.id,
        content: m.content,
        createdAt: m.createdAt,
        sentByMe: m.sentByMe
      }));
      setTimeout(() => this.scrollToBottom(), 50);
      this.startPollingDirectChatMessages();
    });

    this.messageService.markMessagesAsRead(this.patientId, chat.id).subscribe();
  }

  openGroupChat(chat: GroupChatDto): void {
    this.stopPolling();

    this.selectedChatType = 'group';
    this.selectedGroupChat = chat;
    this.selectedDirectChat = null;
    this.showGroupMembers = false;
    this.chatOpen.emit();

    this.groupChatService.getAllMessages(this.patientId, chat.id).subscribe(res => {
      this.messages = (res ?? []).map((m: GroupChatMessageDto) => ({
        id: m.id,
        content: m.content,
        createdAt: m.createdAt,
        sentByMe: m.sentByMe,
        senderFirstName: m.senderFirstName,
        senderLastName: m.senderLastName
      }));
      setTimeout(() => this.scrollToBottom(), 50);

      this.startPollingGroupChatMessages();
    });

    this.groupChatService
      .markMessagesAsReadViaExistingChatEndpoint(this.patientId, chat.id)
      .subscribe({ error: () => {} });
  }


  closeChat(): void {
    this.stopPolling();
    this.resetSelection();
    this.chatClose.emit();
    this.loadAllChats();
  }

  sendMessage(): void {
    if (!this.newMessage.trim() || !this.selectedChatType) return;

    // direct: block sending if not accepted
    if (this.selectedChatType === 'direct') {
      if (!this.selectedDirectChat) return;
      if (!this.selectedDirectChat.accepted) return;

      const dto = new MessageCreationDto(this.newMessage);

      this.messageService.sendMessage(this.patientId, this.selectedDirectChat.id, dto).subscribe({
        next: msg => {
          // optional: du kannst hier auch einfach warten bis polling updated
          this.messages.push({
            id: msg.id,
            content: msg.content,
            createdAt: msg.createdAt,
            sentByMe: msg.sentByMe
          });
          this.newMessage = '';
          setTimeout(() => this.scrollToBottom(), 50);
        },
        error: err => console.error('SEND DIRECT MESSAGE ERROR', err)
      });
      return;
    }

    // group
    if (this.selectedChatType === 'group') {
      if (!this.selectedGroupChat) return;

      const dto = new MessageCreationDto(this.newMessage);

      this.groupChatService.sendMessageViaExistingChatEndpoint(this.patientId, this.selectedGroupChat.id, dto).subscribe({
        next: msg => {
          this.messages.push({
            id: msg.id,
            content: msg.content,
            createdAt: msg.createdAt,
            sentByMe: true
          });
          this.newMessage = '';
          setTimeout(() => this.scrollToBottom(), 50);
        },
        error: err => console.error('SEND GROUP MESSAGE ERROR', err)
      });
    }
  }


  private startPollingDirectChatList(): void {
    this.listPollSub?.unsubscribe();

    this.listPollSub = interval(2500).pipe(
      startWith(0),
      switchMap(() => {
        if (!this.patientId) return EMPTY;
        return this.chatService.getAllChats(this.patientId);
      }),
      catchError(err => {
        console.error('POLL DIRECT CHAT LIST ERROR', err);
        return EMPTY;
      })
    ).subscribe(chats => {
      this.directChats = chats ?? [];

      if (this.selectedChatType === 'direct' && this.selectedDirectChat) {
        const updated = this.directChats.find(c => String(c.id) === String(this.selectedDirectChat!.id));
        if (updated) this.selectedDirectChat = updated;
      }
    });
  }

  private startPollingGroupChatMessages(): void {
    this.messagePollSub?.unsubscribe();

    this.messagePollSub = interval(2500).pipe(
      startWith(0),
      switchMap(() => {
        if (!this.selectedGroupChat || this.selectedChatType !== 'group') return EMPTY;
        return this.groupChatService.getAllMessages(this.patientId, this.selectedGroupChat.id);
      }),
      catchError(err => {
        console.error('POLL GROUP MESSAGES ERROR', err);
        return EMPTY;
      })
    ).subscribe(res => {
      if (!this.selectedGroupChat || this.selectedChatType !== 'group') return;

      this.messages = (res ?? []).map((m: GroupChatMessageDto) => ({
        id: m.id,
        content: m.content,
        createdAt: m.createdAt,
        sentByMe: m.sentByMe,
        senderFirstName: m.senderFirstName,
        senderLastName: m.senderLastName
      }));

      setTimeout(() => this.scrollToBottom(), 50);
    });
  }



  private scrollToBottom(): void {
    try {
      const el = this.scrollContainer.nativeElement;
      el.scrollTop = el.scrollHeight;
    } catch {}
  }

  
}
