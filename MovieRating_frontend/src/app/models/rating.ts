export interface RatingRequest {
  movieId: number;
  rating: number;
}

export interface RatingResponse {
   ratingId: number;

  movieId: number;

  movieTitle: string;

  posterUrl: string;

  releaseYear?: number;

  genres?: string[];

  userId: number;

  userName: string;

  rating: number;

  averageRating: number;

  totalRatings: number;

  ratedAt: string;

}