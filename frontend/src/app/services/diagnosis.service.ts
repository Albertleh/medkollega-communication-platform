import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DiagnosisDto } from '../dtos/diganosis';
import { DiagnosisCreationDto } from '../dtos/diagnosis-creation';
import { DiagnosisToPastDto } from '../dtos/diagnosis-to-past';

@Injectable({ providedIn: 'root' })
export class DiagnosisService {

  private baseUrl = 'http://localhost:8080/api/v1/patient';


  constructor(private http: HttpClient) {}

  getAll(patientId: string): Observable<DiagnosisDto[]> {
    return this.http.get<DiagnosisDto[]>(`${this.baseUrl}/${patientId}/diagnosis`);
  }

  getAllPast(patientId: string): Observable<DiagnosisDto[]> {
    return this.http.get<DiagnosisDto[]>(`${this.baseUrl}/${patientId}/diagnosis/past`);
  }

  create(patientId: string, dto: DiagnosisCreationDto): Observable<DiagnosisDto> {
    return this.http.post<DiagnosisDto>(`${this.baseUrl}/${patientId}/diagnosis`, dto);
  }

  setToPast(patientId: string, diagnosisId: string, dto: DiagnosisToPastDto): Observable<DiagnosisDto> {
    return this.http.post<DiagnosisDto>(
      `${this.baseUrl}/${patientId}/diagnosis/${diagnosisId}/past`,
      dto
    );
  }

  delete(patientId: string, diagnosisId: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${patientId}/diagnosis/${diagnosisId}`);
  }
}
