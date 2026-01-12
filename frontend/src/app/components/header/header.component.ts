import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { ChatRequestService } from 'src/app/services/chat.service.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  standalone: false
})
export class HeaderComponent implements OnInit {

  isMenuOpen = false;
  showChatRequestPopup = false;

  // ✅ BADGE STATE
  openRequestCount = 0;

  constructor(
    public authService: AuthService,
    private router: Router,
    private chatRequestService: ChatRequestService
  ) {}

  ngOnInit() {
    this.loadOpenRequestCount();
  }

  hideHeader(): boolean {
    return this.router.url === '/login';
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
    this.showChatRequestPopup = false; // ✅ gegenseitig schließen
  }

  toggleChatRequestPopup(): void {
    this.showChatRequestPopup = !this.showChatRequestPopup;
    this.isMenuOpen = false; // ✅ gegenseitig schließen

    // ✅ Wenn geöffnet → Badge zurücksetzen
    if (this.showChatRequestPopup) {
      this.openRequestCount = 0;
    }
  }

  closeChatRequestPopup(): void {
    this.showChatRequestPopup = false;
  }

  logout() {
    this.authService.logoutUser();
    this.router.navigate(['/login']);
  }

  goTo(path: string): void {
    this.router.navigate([`/${path}`]);
  }

  // ✅ Lädt nur die ANZAHL der offenen Anfragen
  private loadOpenRequestCount(): void {
    this.chatRequestService.getAllOpenRequests().subscribe({
      next: (data: any) => {
        const requests = Array.isArray(data?.chatRequests)
          ? data.chatRequests
          : [];

        // ✅ nur UNGELESENE zählen
        this.openRequestCount = requests.filter((r: any) => !r.read).length;
      },
      error: err => console.error('BADGE LOAD ERROR', err)
    });
  }
}
