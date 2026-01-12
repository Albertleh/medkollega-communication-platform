import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ContactDto } from '../dtos/contact';
import { ContactUpdateDto } from '../dtos/contact-update';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/api/v1/user';

  constructor(private http: HttpClient) {}

  getPersonalSettings(): Observable<ContactDto> {
    return this.http.get<ContactDto>(`${this.baseUrl}/contact`);
  }

  updatePersonalSettings(dto: ContactUpdateDto): Observable<ContactDto> {
    return this.http.put<ContactDto>(`${this.baseUrl}/contact`, dto);
  }

  getAllUsers(search: string | null, patientId: string): Observable<ContactDto[]> {
    let params = new HttpParams().set('patientId', patientId);

    if (search) {
      params = params.set('search', search);
    }

    return this.http.get<ContactDto[]>(this.baseUrl, { params });
  }
}
