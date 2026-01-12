import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from 'src/app/services/user.service.service';
import { ChatRequestService } from 'src/app/services/chat.service.service';
import { ContactDto } from 'src/app/dtos/contact';
import { ChatDto } from 'src/app/dtos/chat';

@Component({
  selector: 'app-create-doctor-chat-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './create-doctor-chat-modal.component.html',
  styleUrls: ['./create-doctor-chat-modal.component.scss']
})
export class CreateDoctorChatModalComponent implements OnInit {

  @Input() patientId!: string;

  @Output() close = new EventEmitter<void>();
  @Output() created = new EventEmitter<ChatDto>(); // notify parent to reload chats

  users: ContactDto[] = [];
  filteredUsers: ContactDto[] = [];

  loading = false;
  searchTerm = '';

  constructor(
    private userService: UserService,
    private chatService: ChatRequestService
  ) {}

  ngOnInit(): void {
    if (!this.patientId) {
      console.error('❌ patientId is missing in CreateDoctorChatModal');
      return;
    }

    this.loadAvailableUsers();
  }

  loadAvailableUsers(): void {
    this.loading = true;

    this.userService.getAllUsers(null, this.patientId).subscribe({
      next: data => {
        this.users = data;
        this.filteredUsers = data;
        this.loading = false;
      },
      error: err => {
        console.error('LOAD USERS ERROR', err);
        this.loading = false;
      }
    });
  }

  onSearch(): void {
    const term = this.searchTerm.toLowerCase().trim();

    this.filteredUsers = this.users.filter(u =>
      `${u.firstName} ${u.lastName}`.toLowerCase().includes(term) ||
      u.professionalRoleUser?.toLowerCase().includes(term)
    );
  }


  selectUser(user: ContactDto): void {
    console.log('CREATE CHAT FOR:', {
      patientId: this.patientId,
      professionalId: user.userId
    });

    this.chatService
    .createChat(this.patientId, user.userId)
    .subscribe({
      next: chat => {
        this.created.emit(chat);   
        this.close.emit();
      },
      error: err => console.error('CREATE CHAT ERROR', err)
    });
    }
  }
