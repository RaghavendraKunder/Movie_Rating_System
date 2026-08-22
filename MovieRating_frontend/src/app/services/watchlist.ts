import { Injectable } from '@angular/core';

import {
  HttpClient
} from '@angular/common/http';

import {
  Observable
} from 'rxjs';

import {
  WatchlistMovie
} from '../models/watchlist';


@Injectable({
  providedIn: 'root'
})

export class Watchlist {


  private readonly baseUrl =
    'http://localhost:9091/api/watchlist';


  constructor(
    private http: HttpClient
  ) {}


  /**
   * Get logged-in user's watchlist
   */
  getWatchlist(): Observable<WatchlistMovie[]> {

    return this.http.get<WatchlistMovie[]>(
      this.baseUrl
    );

  }


  /**
   * Add movie to watchlist
   */
  addToWatchlist(
    movieId: number
  ): Observable<WatchlistMovie> {

    return this.http.post<WatchlistMovie>(
      `${this.baseUrl}/${movieId}`,
      {}
    );

  }


  /**
   * Remove movie from watchlist
   */
  removeFromWatchlist(
    movieId: number
  ): Observable<string> {

    return this.http.delete(
      `${this.baseUrl}/${movieId}`,
      {
        responseType: 'text'
      }
    );

  }


  /**
   * Check whether movie is in watchlist
   */
  isMovieInWatchlist(
    movieId: number
  ): Observable<boolean> {

    return this.http.get<boolean>(
      `${this.baseUrl}/check/${movieId}`
    );

  }


  /**
   * Clear complete watchlist
   */
  clearWatchlist(): Observable<string> {

    return this.http.delete(
      `${this.baseUrl}/clear`,
      {
        responseType: 'text'
      }
    );

  }

}