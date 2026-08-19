import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieRequestService {

  private baseUrl = 'http://localhost:9091/api/movie-requests';

  constructor(private http: HttpClient) {}


  // USER
  createRequest(request: any): Observable<any> {

    return this.http.post<any>(
      this.baseUrl,
      request,
      {
        withCredentials: true
      }
    );

  }


  // ADMIN
  getAllRequests(): Observable<any[]> {

    return this.http.get<any[]>(
      this.baseUrl,
      {
        withCredentials: true
      }
    );

  }


  // ADMIN
  getRequestById(id: number): Observable<any> {

    return this.http.get<any>(
      `${this.baseUrl}/${id}`,
      {
        withCredentials: true
      }
    );

  }


  // ADMIN
  approveRequest(id: number): Observable<any> {

    return this.http.put<any>(
      `${this.baseUrl}/${id}/approve`,
      {},
      {
        withCredentials: true
      }
    );

  }


  // ADMIN
  rejectRequest(id: number): Observable<any> {

    return this.http.put<any>(
      `${this.baseUrl}/${id}/reject`,
      {},
      {
        withCredentials: true
      }
    );

  }

}