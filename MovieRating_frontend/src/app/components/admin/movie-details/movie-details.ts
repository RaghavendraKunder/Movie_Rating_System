import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Movie } from '../../../models/movies';
import { MovieService } from '../../../services/movie';

@Component({
  selector: 'app-movie-details',
  standalone: false,
  templateUrl: './movie-details.html',
  styleUrl: './movie-details.css'
})
export class MovieDetails implements OnInit {

  movie!: Movie;

  movieId!: number;

  selectedRating: number = 0;

  reviewText: string = '';

  reviews: any[] = [];

  loading: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private movieService: MovieService
  ) {}

  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {

      const id = params.get('id');

      if (id) {

        this.movieId = Number(id);

        this.loadMovie();

        this.loadReviews();

      }

    });

  }

  loadMovie(): void {

    this.movieService
      .getMovieById(this.movieId)
      .subscribe({

        next: (data: Movie) => {

          this.movie = data;

          this.loading = false;

          console.log('Movie details:', this.movie);

        },

        error: (err) => {

          console.error(
            'Error loading movie:',
            err
          );

          this.loading = false;

        }

      });

  }

  loadReviews(): void {

    // We will connect this to ReviewService
    // in the next step.

  }

  goBack(): void {

    this.router.navigate(['/all-movies']);

  }

  selectRating(rating: number): void {

    this.selectedRating = rating;

  }

  submitRating(): void {

    if (this.selectedRating === 0) {

      alert('Please select a rating.');

      return;

    }

    console.log(
      'Rating:',
      this.selectedRating
    );

    // Rating API will be connected here.
  }

  submitReview(): void {

    if (!this.reviewText.trim()) {

      alert('Please write a review.');

      return;

    }

    console.log(
      'Review:',
      this.reviewText
    );

    // Review API will be connected here.
  }

  openTrailer(): void {

  if (this.movie?.trailerUrl) {

    window.open(
      this.movie.trailerUrl,
      '_blank'
    );

  }

}

}