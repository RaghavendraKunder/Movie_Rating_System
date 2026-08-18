import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieRequestService {

  private baseUrl = 'http://localhost:9091/api/movie-requests';

  constructor(private http: HttpClient) {}

  createRequest(request: any): Observable<any> {

    return this.http.post<any>(
      this.baseUrl,
      request,
      {
        withCredentials: true
      }
    );

  }
}