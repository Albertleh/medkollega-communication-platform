import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-findings',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './findings.component.html',
  styleUrls: ['./findings.component.scss']
})
export class FindingsComponent implements OnInit, OnChanges {

  @Input() patientId!: string;
  @Input() search: string = '';

  files: { name: string; blob: Blob }[] = [];
  filteredFiles: { name: string; blob: Blob }[] = [];

  ngOnInit(): void {
    this.applyFilter();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['search']) {
      this.applyFilter();
    }
  }

  upload(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return;

    const file = input.files[0];
    this.files.push({ name: file.name, blob: file });

    this.applyFilter();
  }

  applyFilter(): void {
    const term = this.search?.toLowerCase() || '';
    this.filteredFiles = this.files.filter(f =>
      f.name.toLowerCase().includes(term)
    );
  }

  open(file: { name: string; blob: Blob }): void {
    const url = URL.createObjectURL(file.blob);
    window.open(url, '_blank');
  }
}
