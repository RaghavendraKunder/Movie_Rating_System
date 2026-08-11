import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Movie } from '../models/movies';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private baseUrl = 'http://localhost:9091/api/movies';

  constructor(private http: HttpClient) {}

  addMovie(movie: any): Observable<Movie> {
    return this.http.post<Movie>(
      this.baseUrl,
      movie,
      {
        withCredentials: true
      }
    );
  }

  getAllMovies(): Observable<Movie[]> {

  return this.http.get<Movie[]>(
    'http://localhost:9091/api/movies'
  );

  
}
getMovieById(id: number): Observable<Movie> {

    return this.http.get<Movie>(
        `${this.baseUrl}/${id}`
    );

}
}