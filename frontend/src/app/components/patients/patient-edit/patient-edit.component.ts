import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { PatientDetailDto } from 'src/app/dtos/patient-detail';
import { PatientModificationDto } from 'src/app/dtos/patient-modification';
import { PatientService } from 'src/app/services/patient.service';

@Component({
  selector: 'app-patient-edit',
  standalone: true,             
  imports: [
    CommonModule,                
    FormsModule                  
  ],
  templateUrl: './patient-edit.component.html',
  styleUrls: ['./patient-edit.component.scss']
})
export class PatientEditComponent implements OnInit {

  @Input() patient!: PatientDetailDto;

  @Output() close = new EventEmitter<void>();

  @Output() updated = new EventEmitter<PatientDetailDto>();

  editablePatient!: PatientDetailDto;

  constructor(private patientService: PatientService) {}

  ngOnInit(): void {
    this.editablePatient = { ...this.patient };
  }

  save(): void {
    const dto = new PatientModificationDto(
      this.editablePatient.insuranceNumber,
      this.editablePatient.dateOfBirth,   // yyyy-MM-dd
      this.editablePatient.firstName,
      this.editablePatient.lastName,
      this.editablePatient.zipCode,
      this.editablePatient.city,
      this.editablePatient.streetName,
      this.editablePatient.streetNumber,
      this.editablePatient.description
    );

    this.patientService.updatePatient(this.patient.id, dto).subscribe({
      next: (updatedPatient) => {
        this.updated.emit(updatedPatient);  
      },
      error: (err) => {
        console.error('Failed to update patient', err);
      }
    });
  }
}
