import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { UserService } from 'src/app/services/user.service.service';
import { GroupChatService } from 'src/app/services/group-chat.service';

import { ContactDto } from 'src/app/dtos/contact';
import { GroupChatCreationDto } from 'src/app/dtos/group-chat-creation';
import { GroupChatDto } from 'src/app/dtos/group-chat';

@Component({
  selector: 'app-create-group-chat-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './create-group-chat-modal.component.html'
})
export class CreateGroupChatModalComponent {
  @Input() patientId!: string;

  @Output() close = new EventEmitter<void>();
  @Output() created = new EventEmitter<GroupChatDto>();

  groupName = '';
  search = '';

  users: ContactDto[] = [];
  loading = false;
  submitting = false;
  error: string | null = null;

  // Multi-select
  selected = new Map<string, ContactDto>();

  constructor(
    private userService: UserService,
    private groupChatService: GroupChatService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  onClose(): void {
    this.close.emit();
  }

  loadUsers(): void {
    if (!this.patientId) return;

    this.loading = true;
    this.userService.getAllUsers(this.search?.trim() || null, this.patientId).subscribe({
      next: res => {
        this.users = res ?? [];
        this.loading = false;
      },
      error: err => {
        console.error('GET USERS ERROR', err);
        this.loading = false;
        this.error = 'User konnten nicht geladen werden.';
      }
    });
  }

  onSearchChange(): void {
    this.loadUsers();
  }

  isSelected(u: ContactDto): boolean {
    return this.selected.has(u.userId);
  }

  toggleUser(u: ContactDto): void {
    if (this.selected.has(u.userId)) {
      this.selected.delete(u.userId);
    } else {
      this.selected.set(u.userId, u);
    }
  }

  removeSelected(userId: string): void {
    this.selected.delete(userId);
  }

  create(): void {
    this.error = null;

    const name = this.groupName.trim();
    const ids = Array.from(this.selected.keys());

    if (!name) {
      this.error = 'Gruppenname fehlt.';
      return;
    }
    if (ids.length < 2) {
      this.error = 'Für einen Gruppenchat wähle mindestens 2 Personen aus.';
      return;
    }

    this.submitting = true;

    const dto = new GroupChatCreationDto(
      this.patientId,
      ids,
      name
    );

    this.groupChatService.createGroupChat(this.patientId, dto).subscribe({
      next: chat => {
        this.submitting = false;
        this.created.emit(chat);
      },
      error: err => {
        console.error('CREATE GROUP CHAT ERROR', err);
        this.submitting = false;
        this.error = 'Gruppenchat konnte nicht erstellt werden.';
      }
    });
  }
}
