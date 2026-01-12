import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PatientModificationDto } from 'src/app/dtos/patient-modification';
import { PatientDetailDto } from 'src/app/dtos/patient-detail';
import { PatientService } from 'src/app/services/patient.service';

@Component({
  selector: 'app-patient-create',
  standalone: true,                  // ✅ REQUIRED
  imports: [CommonModule, FormsModule], // ✅ REQUIRED FOR ngModel
  templateUrl: './patient-create.component.html',
  styleUrls: ['./patient-create.component.scss']
})
export class PatientCreateComponent {

  @Output() close = new EventEmitter<void>();
  @Output() created = new EventEmitter<PatientDetailDto>();

  newPatient = new PatientModificationDto(
    0,
    '2000-01-01',   
    '',
    '',
    '',
    '',
    '',
    '',
    ''
  );

  constructor(private patientService: PatientService) {}

  save(): void {
    this.patientService.createPatient(this.newPatient).subscribe({
      next: (created) => {
        this.created.emit(created);
        this.close.emit();
      },
      error: (err) => console.error('Create patient failed', err)
    });
  }
  
}
