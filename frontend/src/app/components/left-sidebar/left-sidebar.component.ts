import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PatientService } from '../../services/patient.service';
import { PatientDto } from '../../dtos/patient';

import { AvatarComponent } from '../avatar/avatar.component';
import { PatientCreateComponent } from '../patient-create/patient-create.component';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-left-sidebar',
  standalone: true,
  imports: [
    CommonModule,
    AvatarComponent,
    PatientCreateComponent
  ],
  templateUrl: './left-sidebar.component.html',
  styleUrls: ['./left-sidebar.component.scss']
})
export class LeftSidebarComponent implements OnInit {

  @Input() isOpen = true;

  patients: PatientDto[] = [];
  filteredPatients: PatientDto[] = [];

  @Output() patientSelected = new EventEmitter<string>();
  selectedPatientId: string | null = null;

  createMode = false;

  constructor(
    public authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private patientService: PatientService
  ) {}

  private destroy$ = new Subject<void>();

  ngOnInit(): void {
    this.reloadPatients();

    this.patientService.refreshRequested$
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => this.reloadPatients());

    this.route.firstChild?.paramMap.subscribe(params => {
      this.selectedPatientId = params.get('id');
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  reloadPatients(): void {
    this.patientService.getpatients().subscribe({
      next: data => {
        this.patients = data;
        this.filteredPatients = data;
      },
      error: err => console.error('Sidebar patient load failed', err)
    });
  }


  onSearch(term: string) {
    const value = term.toLowerCase().trim();
    this.filteredPatients = this.patients.filter(p =>
      (p.firstName + ' ' + p.lastName).toLowerCase().includes(value)
    );
  }


  onSelectPatient(patient: PatientDto) {
    this.selectedPatientId = patient.id;
    this.patientSelected.emit(patient.id);


    this.router.navigate(['/patients', patient.id]);
  }

  openCreatePatient(): void {
    this.createMode = true;
  }

  closeCreatePatient(): void {
    this.createMode = false;
  }

  onPatientCreated(newPatient: any) {
    this.patients.unshift(newPatient);
    this.filteredPatients = [...this.patients];
    this.createMode = false;
  }
}
