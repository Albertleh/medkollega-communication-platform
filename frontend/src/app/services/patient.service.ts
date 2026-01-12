import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { PatientDto } from '../dtos/patient';
import { PatientDetailDto } from '../dtos/patient-detail';
import { PatientModificationDto } from '../dtos/patient-modification';
import { Globals } from '../global/globals';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private baseUrl = `${this.globals.backendUri}/patients`;

  constructor(
    private http: HttpClient,
    private globals: Globals
  ) {}

  getpatients(): Observable<PatientDto[]> {
    return this.http.get<PatientDto[]>(this.baseUrl);
  }

  getPatient(id: string): Observable<PatientDetailDto> {
    return this.http.get<PatientDetailDto>(`${this.baseUrl}/${id}`);
  }

  updatePatient(
    id: string,
    patient: PatientModificationDto
  ): Observable<PatientDetailDto> {
    return this.http.put<PatientDetailDto>(
      `${this.baseUrl}/${id}`,
      patient
    );
  }

  createPatient(
    patient: PatientModificationDto
  ): Observable<PatientDetailDto> {
    return this.http.post<PatientDetailDto>(
      this.baseUrl,
      patient
    );
  }

  private _refreshRequested = new Subject<void>();
  refreshRequested$ = this._refreshRequested.asObservable();

  requestRefresh(): void {
    this._refreshRequested.next();
  }

  getPatientBySozNr(sozNr: string): Observable<PatientDetailDto> {
    return this.http.get<PatientDetailDto>(`${this.baseUrl}/bySozNr`, {
      params: { sozNr }
    });
  }


}
