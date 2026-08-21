import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { AdminUser } from '../models/admin-user';

@Injectable({
  providedIn: 'root',
})
export class Admin {

  private baseUrl = 'http://localhost:9091/api/admin';

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<AdminUser[]> {
    return this.http.get<AdminUser[]>(
      `${this.baseUrl}/users`
    );
  }
}