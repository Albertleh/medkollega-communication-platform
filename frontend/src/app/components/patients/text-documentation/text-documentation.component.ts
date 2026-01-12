import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DocumentationTextService } from '../../../services/documentation-text.service';
import { TextEntryDto } from 'src/app/dtos/text-entry';
import { TextEntryCreationDto } from 'src/app/dtos/text-entry-creation';

@Component({
  selector: 'app-text-documentation',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './text-documentation.component.html',
  styleUrls: ['./text-documentation.component.scss']
})
export class TextDocumentationComponent implements OnInit, OnChanges {

  @Input() patientId!: string;
  @Input() search: string = '';

  entries: TextEntryDto[] = [];
  filteredEntries: TextEntryDto[] = [];
  loading = true;

  editId: string | null = null;
  editText: string = '';
  newEntryText: string = '';

  constructor(private textService: DocumentationTextService) {}

  ngOnInit(): void {
    this.loadEntries();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['patientId'] && !changes['patientId'].firstChange) {
      this.loadEntries();
    }

    if (changes['search']) {
      this.applyFilter();
    }
  }

  loadEntries(): void {
    this.loading = true;

    this.textService.getAll(this.patientId).subscribe({
      next: data => {
        this.entries = data;
        this.applyFilter();
        this.loading = false;
      },
      error: () => {
        this.entries = [];
        this.filteredEntries = [];
        this.loading = false;
      }
    });
  }

  applyFilter(): void {
    const term = this.search?.toLowerCase() || '';
    this.filteredEntries = this.entries.filter(e =>
      e.text.toLowerCase().includes(term)
    );
  }

  createEntry(text: string): void {
    const dto = new TextEntryCreationDto(text);
    this.textService.create(this.patientId, dto).subscribe({
      next: () => this.loadEntries()
    });
  }

  updateEntry(entryId: string, text: string): void {
    const dto = new TextEntryCreationDto(text);
    this.textService.update(this.patientId, entryId, dto).subscribe({
      next: () => this.loadEntries()
    });
  }

  deleteEntry(entryId: string): void {
    this.textService.delete(this.patientId, entryId).subscribe({
      next: () => this.loadEntries()
    });
  }

  startEdit(entry: TextEntryDto): void {
    this.editId = entry.id;
    this.editText = entry.text;
  }

  cancelEdit(): void {
    this.editId = null;
    this.editText = '';
  }

  saveEdit(entryId: string): void {
    this.updateEntry(entryId, this.editText);
    this.cancelEdit();
  }

  createNewEntry(): void {
    if (!this.newEntryText.trim()) return;
    this.createEntry(this.newEntryText);
    this.newEntryText = '';
  }
}
