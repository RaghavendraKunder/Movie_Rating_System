import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { MovieService } from '../../../services/movie';
import { RatingService } from '../../../services/rating';
import { ReviewService } from '../../../services/review';

@Component({
  selector: 'app-movie-details',
  standalone: false,
  templateUrl: './movie-details.html',
  styleUrls: ['./movie-details.css']
})

export class MovieDetails implements OnInit {

  movie: any = null;
  isLoading = true;
  selectedRating = 0;
  reviewText = '';
  reviews: any[] = [];
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private movieService: MovieService,
    private ratingService: RatingService,
    private reviewService: ReviewService,
    private router: Router,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      console.log('Movie ID:', id);
      if (!id) {
        this.isLoading = false;
        this.errorMessage = 'Invalid movie ID';
        return;
      }
      this.loadMovie(id);
    });
  }

  private loadMovie(id: number): void {

  console.log('Loading movie:', id);

  this.movie = null;
  this.errorMessage = '';
  this.isLoading = true;

  this.movieService.getMovieById(id).subscribe({

    next: (response) => {

      console.log('Movie API response:', response);

      this.movie = response;
      this.isLoading = false;

      console.log('Movie loaded successfully');

      this.loadReviews(id);

    },

    error: (error) => {
        console.error('Error loading movie:', error);
        Promise.resolve().then(() => {
          this.movie = null;
          this.isLoading = false;
          this.errorMessage = 'Unable to load movie details.';
          this.cd.markForCheck();
        });
      }
    });
  }

  openTrailer(): void {
    if (this.movie?.trailerUrl) {
      window.open(this.movie.trailerUrl, '_blank');
    }
  }

  selectRating(star: number): void {
    this.selectedRating = star;
  }

  submitRating(): void {

  if (!this.movie) {
    return;
  }

  if (this.selectedRating === 0) {
    alert('Please select a rating.');
    return;
  }

  const review = this.reviewText.trim();

  // Minimum 20 characters
  if (review.length < 20) {
    alert(`Review must be at least 20 characters. Current length: ${review.length}`);
    return;
  }

  // Maximum 3000 characters
  if (review.length > 3000) {
    alert('Review cannot exceed 3000 characters.');
    return;
  }

  const movieId = this.movie.id;

  const ratingRequest = {
    movieId: movieId,
    rating: this.selectedRating
  };

  const reviewRequest = {
    movieId: movieId,
    title: 'Movie Review',
    review: review,
    containsSpoiler: false
  };

  console.log('Submitting rating:', ratingRequest);
  console.log('Submitting review:', reviewRequest);

  this.ratingService.rateMovie(ratingRequest).subscribe({

    next: (ratingResponse) => {

      console.log('Rating saved successfully:', ratingResponse);

      this.reviewService.addReview(reviewRequest).subscribe({

        next: (reviewResponse) => {

          console.log('Review saved successfully:', reviewResponse);

          alert('Rating and review submitted successfully!');

          this.selectedRating = 0;
          this.reviewText = '';

          this.loadMovie(movieId);
          this.loadReviews(movieId);
        },

        error: (error) => {

          console.error('Error saving review:', error);

          alert('Rating was saved, but review could not be saved.');
        }

      });
    },

    error: (error) => {

      console.error('Error saving rating:', error);

      alert('Failed to save rating.');
    }

  });
}
private loadReviews(movieId: number): void {

  this.reviewService.getMovieReviews(movieId).subscribe({
    next: (response) => {
      console.log('Movie reviews:', response);
      this.reviews = response;
      this.cd.markForCheck();
    },
    error: (error) => {
      console.error('Error loading reviews:', error);
      this.reviews = [];
    }
  });
}

  goBack(): void {
    this.router.navigate(['/all-movies']);
  }
}