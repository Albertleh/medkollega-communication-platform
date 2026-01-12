import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PatientService } from '../../services/patient.service';
import { PatientDetailDto } from '../../dtos/patient-detail';
import { CommonModule } from '@angular/common';
import { AvatarComponent } from '../avatar/avatar.component';
import { TextDocumentationComponent } from './text-documentation/text-documentation.component';
import { MedicationComponent } from './medication/medication.component';
import { DiagnosisComponent } from './diagnosis/diagnosis.component';
import { FormsModule } from '@angular/forms';
import { PatientRightColumnComponent } from './patient-right-column/patient-right-column.component';
import { PatientModificationDto } from 'src/app/dtos/patient-modification';
import { PatientEditComponent } from './patient-edit/patient-edit.component';
import { FindingsComponent } from './findings/findings.component';

@Component({
  selector: 'app-patients',
  standalone: true,
  imports: [
    CommonModule,
    AvatarComponent,
    FormsModule,
    PatientEditComponent,
    FindingsComponent,

    // NEW COMPONENTS
    TextDocumentationComponent,
    MedicationComponent,
    DiagnosisComponent,
    PatientRightColumnComponent

  ],
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.scss']
})
export class PatientViewComponent implements OnInit {

  patient!: PatientDetailDto;
  hasRouteId = false;
  isChatOpen = false;
  openChatId: string | null = null;
  isRightSidebarOpen = true;

  searchTerm = '';
  activeTab: 'text' | 'medikation' | 'diagnose' | 'befunde' = 'text';


  editMode = false;
  editablePatient!: PatientDetailDto;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private patientService: PatientService
  ) {}

  @Output() patientUpdated = new EventEmitter<void>();

  onPatientUpdated(updated: PatientDetailDto): void {
    this.patient = updated;
    this.editMode = false;
    this.patientUpdated.emit();
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      this.hasRouteId = !!id;

      if (!id) {
        this.patient = undefined!;
        this.openChatId = null;
        return;
      }

      this.loadPatient(id);
    });


    this.route.queryParamMap.subscribe(qm => {
      this.openChatId = qm.get('chatId');
      if (this.openChatId) {
        this.isRightSidebarOpen = true;
      }
    });
    
  }

  toggleRightSidebar(): void {
  this.isRightSidebarOpen = !this.isRightSidebarOpen;

  // optional: wenn du beim Einklappen auch Chat schließen willst:
   if (!this.isRightSidebarOpen) this.isChatOpen = false;
}

  onChatClose(): void {
      this.isChatOpen = false;

      //  wichtig: sofort lokal löschen, sonst auto-open in child
      this.openChatId = null;

      //  URL bereinigen (chatId entfernen)
      this.router.navigate([], {
        relativeTo: this.route,
        queryParams: { chatId: null },     // (optional: chatType: null)
        queryParamsHandling: 'merge',
        replaceUrl: true
      });
    }

  loadPatient(id: string): void {
    this.patientService.getPatient(id).subscribe({
      next: (data) => {
        this.patient = data;
      },
      error: (err) => {
        console.error('Failed to load patient', err);
      }
    });
  }

  openEditPatient(): void {
    if (!this.patient?.id) return;

    this.patientService.getPatient(this.patient.id).subscribe({
      next: (data) => {
        this.editablePatient = { ...data };
        this.editMode = true;
      },
      error: (err) => {
        console.error('Failed to load patient for edit', err);
      }
    });
  }

  savePatient(): void {
    if (!this.patient?.id || !this.editablePatient) return;

    const dto = new PatientModificationDto(
      this.editablePatient.insuranceNumber,
      this.editablePatient.dateOfBirth,
      this.editablePatient.firstName,
      this.editablePatient.lastName,
      this.editablePatient.zipCode,
      this.editablePatient.city,
      this.editablePatient.streetName,
      this.editablePatient.streetNumber,
      this.editablePatient.description
    );

    this.patientService
      .updatePatient(this.patient.id, dto)
      .subscribe({
        next: (updated) => {
          this.patient = updated;   
          this.editMode = false;
        },
        error: (err) => {
          console.error('Failed to update patient', err);
        }
      });
  }

  cancelEdit(): void {
    this.editMode = false;
  }
}