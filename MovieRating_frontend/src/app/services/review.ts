import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ReviewRequest, ReviewResponse } from '../models/review';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private baseUrl = 'http://localhost:9091/api/reviews';

  constructor(private http: HttpClient) {}

  addReview(request: ReviewRequest): Observable<ReviewResponse> {

    return this.http.post<ReviewResponse>(
      this.baseUrl,
      request,
      {
        withCredentials: true
      }
    );

  }

  updateReview(
    reviewId: number,
    request: ReviewRequest
  ): Observable<ReviewResponse> {

    return this.http.put<ReviewResponse>(
      `${this.baseUrl}/${reviewId}`,
      request,
      {
        withCredentials: true
      }
    );

  }

  getMovieReviews(movieId: number): Observable<ReviewResponse[]> {

    return this.http.get<ReviewResponse[]>(
      `${this.baseUrl}/movie/${movieId}`
    );

  }

  getUserReviews(): Observable<ReviewResponse[]> {

    return this.http.get<ReviewResponse[]>(
      `${this.baseUrl}/user`,
      {
        withCredentials: true
      }
    );

  }

}