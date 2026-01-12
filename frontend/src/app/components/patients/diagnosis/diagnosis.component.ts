import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DiagnosisService } from '../../../services/diagnosis.service';
import { DiagnosisDto } from 'src/app/dtos/diganosis';
import { DiagnosisCreationDto } from 'src/app/dtos/diagnosis-creation';
import { DiagnosisToPastDto } from 'src/app/dtos/diagnosis-to-past';

@Component({
  selector: 'app-diagnosis',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './diagnosis.component.html',
  styleUrls: ['./diagnosis.component.scss']
})
export class DiagnosisComponent implements OnInit, OnChanges {

  @Input() patientId!: string;
  @Input() search: string = '';

  diagnoses: DiagnosisDto[] = [];
  pastDiagnoses: DiagnosisDto[] = [];

  filteredDiagnoses: DiagnosisDto[] = [];
  filteredPastDiagnoses: DiagnosisDto[] = [];

  loading = true;

  newDiagnosisText = '';
  newDiagnosisIcd = '';
  newDiagnosisValidUntil: string | null = null;

  constructor(private diagnosisService: DiagnosisService) {}

  ngOnInit(): void {
    if (this.patientId) {
      this.loadCurrent();
      this.loadPast();
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['patientId'] && this.patientId) {
      this.loadCurrent();
      this.loadPast();
    }

    if (changes['search']) {
      this.applyFilter();
    }
  }

  loadCurrent(): void {
    this.loading = true;

    this.diagnosisService.getAll(this.patientId).subscribe({
      next: data => {
        this.diagnoses = data;
        this.applyFilter();
        this.loading = false;
      },
      error: () => {
        this.diagnoses = [];
        this.filteredDiagnoses = [];
        this.loading = false;
      }
    });
  }

  loadPast(): void {
    this.diagnosisService.getAllPast(this.patientId).subscribe({
      next: data => {
        this.pastDiagnoses = data;
        this.applyFilter();
      }
    });
  }

  applyFilter(): void {
    const term = this.search?.toLowerCase() || '';

    this.filteredDiagnoses = this.diagnoses.filter(d =>
      d.diagnosisText.toLowerCase().includes(term) ||
      d.icdCode.toLowerCase().includes(term)
    );

    this.filteredPastDiagnoses = this.pastDiagnoses.filter(d =>
      d.diagnosisText.toLowerCase().includes(term) ||
      d.icdCode.toLowerCase().includes(term)
    );
  }

  create(): void {
    const dto = new DiagnosisCreationDto(
      this.newDiagnosisText,
      this.newDiagnosisIcd,
      this.newDiagnosisValidUntil
    );

    this.diagnosisService.create(this.patientId, dto).subscribe({
      next: () => {
        this.newDiagnosisText = '';
        this.newDiagnosisIcd = '';
        this.newDiagnosisValidUntil = null;
        this.loadCurrent();
        this.loadPast();
      }
    });
  }

  moveToPast(id: string): void {
    const today = new Date().toISOString().split("T")[0];
    const dto = new DiagnosisToPastDto(today);

    this.diagnosisService.setToPast(this.patientId, id, dto).subscribe({
      next: () => {
        this.loadCurrent();
        this.loadPast();
      }
    });
  }

  delete(id: string): void {
    this.diagnosisService.delete(this.patientId, id).subscribe({
      next: () => {
        this.loadCurrent();
        this.loadPast();
      }
    });
  }
}
