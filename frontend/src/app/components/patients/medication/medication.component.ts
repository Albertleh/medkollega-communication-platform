import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MedicationService } from '../../../services/medication.service';
import { MedicationDto } from 'src/app/dtos/medication';
import { MedicationCreationDto } from 'src/app/dtos/medication-creation';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-medication',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './medication.component.html',
  styleUrls: ['./medication.component.scss']
})
export class MedicationComponent implements OnInit, OnChanges {

  @Input() patientId!: string;
  @Input() search: string = '';

  medications: MedicationDto[] = [];
  filteredMedications: MedicationDto[] = [];
  loading = true;

  newMedName = '';
  newMedMorning = 0;
  newMedNoon = 0;
  newMedEvening = 0;
  newMedNight = 0;
  newMedNote = '';

  constructor(private medicationService: MedicationService) {}

  ngOnInit(): void {
    this.loadMedications();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['patientId'] && !changes['patientId'].firstChange) {
      this.loadMedications();
    }

    if (changes['search']) {
      this.applyFilter();
    }
  }

  loadMedications(): void {
    this.loading = true;

    this.medicationService.getAll(this.patientId).subscribe({
      next: data => {
        this.medications = data;
        this.applyFilter();
        this.loading = false;
      },
      error: () => {
        this.medications = [];
        this.filteredMedications = [];
        this.loading = false;
      }
    });
  }

  applyFilter(): void {
    const term = this.search?.toLowerCase() || '';
    this.filteredMedications = this.medications.filter(m =>
      m.medication.toLowerCase().includes(term) ||
      (m.note && m.note.toLowerCase().includes(term))
    );
  }

  createMedicationEntry(): void {
    const dto = new MedicationCreationDto(
      this.newMedName,
      this.newMedMorning,
      this.newMedNoon,
      this.newMedEvening,
      this.newMedNight,
      this.newMedNote
    );

    this.medicationService.create(this.patientId, dto).subscribe({
      next: () => this.loadMedications()
    });

    this.newMedName = '';
    this.newMedMorning = 0;
    this.newMedNoon = 0;
    this.newMedEvening = 0;
    this.newMedNight = 0;
    this.newMedNote = '';
  }

  deleteMedication(id: string): void {
    this.medicationService.delete(this.patientId, id).subscribe({
      next: () => this.loadMedications()
    });
  }

  displayDose(value: number | null | undefined): string {
    if (!value || value <= 0) return '—';
    return value.toString();
  }
}
