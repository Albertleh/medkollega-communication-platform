import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MedicationDto } from '../dtos/medication';
import { MedicationCreationDto } from '../dtos/medication-creation';

@Injectable({ providedIn: 'root' })
export class MedicationService {

  private baseUrl = 'http://localhost:8080/api/v1/patient';


  constructor(private http: HttpClient) {}

  getAll(patientId: string): Observable<MedicationDto[]> {
    return this.http.get<MedicationDto[]>(`${this.baseUrl}/${patientId}/medication`);
  }

  create(patientId: string, dto: MedicationCreationDto): Observable<MedicationDto> {
    return this.http.post<MedicationDto>(`${this.baseUrl}/${patientId}/medication`, dto);
  }

  delete(patientId: string, medicationId: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${patientId}/medication/${medicationId}`);
  }
}
