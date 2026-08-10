export interface Movie {
  id?: number;
  title: string;
  description: string;
  releaseDate: string;
  duration: number;
  language: string;
  genres: string[];
  director: string;
  cast: string;
  posterUrl: string;
  trailerUrl?: string;
  averageRating?: number;
  totalRatings?: number;
  totalReviews?: number;
  createdAt?: string;
  updatedAt?: string;
}