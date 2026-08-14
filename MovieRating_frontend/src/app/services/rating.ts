import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { RatingRequest, RatingResponse } from '../models/rating';

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  private baseUrl = 'http://localhost:9091/api/ratings';

  constructor(private http: HttpClient) {}

  rateMovie(request: RatingRequest): Observable<RatingResponse> {

    return this.http.post<RatingResponse>(
      this.baseUrl,
      request,
      {
        withCredentials: true
      }
    );

  }

  updateRating(
    movieId: number,
    request: RatingRequest
  ): Observable<RatingResponse> {

    return this.http.put<RatingResponse>(
      `${this.baseUrl}/${movieId}`,
      request,
      {
        withCredentials: true
      }
    );

  }

  getMovieRatings(movieId: number): Observable<RatingResponse[]> {

    return this.http.get<RatingResponse[]>(
      `${this.baseUrl}/movie/${movieId}`
    );

  }

  getAverageRating(movieId: number): Observable<number> {

    return this.http.get<number>(
      `${this.baseUrl}/average/${movieId}`
    );

  }

}