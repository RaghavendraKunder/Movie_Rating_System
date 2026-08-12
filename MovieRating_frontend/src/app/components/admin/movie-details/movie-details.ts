import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MovieService } from '../../../services/movie';

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
        Promise.resolve().then(() => {
          this.movie = response;
          this.isLoading = false;
          this.cd.markForCheck();
        });
        console.log('Movie loaded successfully');
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
    if (this.selectedRating === 0) {
      return;
    }
    console.log('Rating:', this.selectedRating);
    console.log('Review:', this.reviewText);
  }

  goBack(): void {
    this.router.navigate(['/all-movies']);
  }
}