export interface ReviewRequest {
  movieId: number;
  title: string;
  review: string;
  containsSpoiler: boolean;
}

export interface ReviewResponse {
  reviewId: number;
  movieId: number;
  movieTitle: string;
  userId: number;
  userName: string;
  title: string;
  review: string;
  containsSpoiler: boolean;
  likes: number;
  dislikes: number;
  userRating: number | null;
  createdAt: string;
  updatedAt: string | null;
  editable: boolean;
}