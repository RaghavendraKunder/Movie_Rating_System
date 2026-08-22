export interface WatchlistMovie {

  watchlistId: number;

  movieId: number;

  movieTitle: string;

  posterUrl: string;

  language: string;

  genres: string[];

  certificate?: string;

  averageRating?: number;

  duration?: number;

  releaseYear?: number;

  addedAt: string;

}