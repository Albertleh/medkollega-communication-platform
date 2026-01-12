import {
  Component,
  OnInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from 'src/app/services/user.service.service';
import { ContactDto } from 'src/app/dtos/contact';
import { ContactUpdateDto } from 'src/app/dtos/contact-update';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-account-overview',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './account-overview.component.html',
  styleUrls: ['./account-overview.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AccountOverviewComponent implements OnInit {

  contact: ContactDto | null = null;

  loading = true;
  saving = false;
  error = false;
  success = false;

  constructor(
    private userService: UserService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.error = false;

    this.userService.getPersonalSettings()
      .pipe(finalize(() => {
        this.loading = false;
        this.cdr.markForCheck();
      }))
      .subscribe({
        next: data => {
          this.contact = data;
          this.cdr.markForCheck();
        },
        error: () => {
          this.error = true;
          this.contact = null;
          this.cdr.markForCheck();
        }
      });
  }

  private successTimer?: any;

  save(): void {
    if (!this.contact) return;

    // falls gerade noch ein alter Timer läuft
    if (this.successTimer) {
      clearTimeout(this.successTimer);
      this.successTimer = undefined;
    }

    this.saving = true;
    this.success = false;
    this.error = false;
    this.cdr.markForCheck();

    const payload: ContactUpdateDto = {
      firstName: this.contact.firstName,
      lastName: this.contact.lastName,
      zipCode: this.contact.zipCode,
      city: this.contact.city,
      placesAvailable: this.contact.placesAvailable
    };

    this.userService.updatePersonalSettings(payload)
      .pipe(finalize(() => {
        this.saving = false;
        this.cdr.markForCheck();
      }))
      .subscribe({
        next: updated => {
          this.contact = updated;
          this.success = true;
          this.cdr.markForCheck();

          this.successTimer = setTimeout(() => {
            this.success = false;
            this.cdr.markForCheck();
            this.successTimer = undefined;
          }, 3000);
        },
        error: () => {
          this.error = true;
          this.cdr.markForCheck();
        }
      });
  }
}
