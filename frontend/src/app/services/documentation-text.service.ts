import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TextEntryDto } from '../dtos/text-entry';
import { TextEntryCreationDto } from '../dtos/text-entry-creation';

@Injectable({ providedIn: 'root' })
export class DocumentationTextService {

  private baseUrl = 'http://localhost:8080/api/v1/patient';

  constructor(private http: HttpClient) {}

  getAll(patientId: string): Observable<TextEntryDto[]> {
    return this.http.get<TextEntryDto[]>(`${this.baseUrl}/${patientId}/textEntry`);
  }

  create(patientId: string, dto: TextEntryCreationDto): Observable<TextEntryDto> {
    return this.http.post<TextEntryDto>(`${this.baseUrl}/${patientId}/textEntry`, dto);
  }

  update(patientId: string, entryId: string, dto: TextEntryCreationDto): Observable<TextEntryDto> {
    return this.http.put<TextEntryDto>(`${this.baseUrl}/${patientId}/textEntry/${entryId}`, dto);
  }

  delete(patientId: string, entryId: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${patientId}/textEntry/${entryId}`);
  }
}
