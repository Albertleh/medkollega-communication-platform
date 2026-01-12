import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-avatar',
  templateUrl: './avatar.component.html',
  styleUrls: ['./avatar.component.scss'],
  standalone: true
})
export class AvatarComponent {

  @Input() first!: string;
  @Input() last!: string;

  get initials(): string {
    return (this.first[0] + this.last[0]).toUpperCase();
  }

  get background(): string {
    const seed = this.first + this.last;
    const colors = [
      "#BEE7DA","#B5D4F5","#CDB9F3",
      "#F3B7C3","#BFD3E8","#D1E8A8"
    ];
    const hash = seed.split('').reduce((acc, c) => acc + c.charCodeAt(0), 0);
    return colors[hash % colors.length];
  }
}
