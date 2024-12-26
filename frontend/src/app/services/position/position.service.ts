import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PositionResponseTRC } from '../../types/PositionResponseTRC';

@Injectable({
  providedIn: 'root',
})
export class PositionService {
  private apiUrl = 'http://localhost:8080/api/v1/positions/client'; // Replace with your actual API URL

  constructor(private http: HttpClient) {}

  // Fetch position data from the backend using clientId
  getPositionsByClientId(clientId: string): Observable<PositionResponseTRC[]> {
    return this.http.get<PositionResponseTRC[]>(`${this.apiUrl}/${clientId}`);
  }
}
