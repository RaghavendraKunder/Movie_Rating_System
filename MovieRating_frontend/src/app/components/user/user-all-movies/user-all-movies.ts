import {Component, OnInit, ChangeDetectorRef} from '@angular/core';

import { Router } from '@angular/router';

import { Movie } from '../../../models/movies';
import { MovieService } from '../../../services/movie';

@Component({
  selector: 'app-user-all-movies',
  standalone: false,
  templateUrl: './user-all-movies.html',
  styleUrl: './user-all-movies.css',
})
export class UserAllMovies implements OnInit{

  movies: Movie[] = [];
  filteredMovies: Movie[] = [];
  searchTerm: string = '';
  genres: string[] = [];
  selectedGenres: string[] = [];

  constructor(
    private movieService: MovieService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadMovies();
  }

  loadMovies(): void {
    this.movieService.getAllMovies().subscribe({
      next: (data: Movie[]) => {
        console.log('Movies loaded:', data);
        this.movies = [...data].sort(
          (a, b) =>
            new Date(b.releaseDate).getTime() -
            new Date(a.releaseDate).getTime()
        );
        this.filteredMovies = [...this.movies];
        const genreSet = new Set<string>();
        this.movies.forEach(movie => {
          if (movie.genres) {
            movie.genres.forEach(genre => {
              genreSet.add(genre);
            });
          }
        });
        this.genres = Array.from(genreSet);
        console.log('Genres:', this.genres);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error(
          'Error loading movies:',
          err
        );
      }
    });
  }

  filterMovies(): void {
    const search = this.searchTerm
      .toLowerCase()
      .trim();
    this.filteredMovies = this.movies.filter(movie => {
      const matchesSearch =
        !search ||
        movie.title.toLowerCase().includes(search);
      const matchesGenre =
        this.selectedGenres.length === 0 ||
        this.selectedGenres.some(
          genre => movie.genres.includes(genre)
        );
      return matchesSearch && matchesGenre;
    });
  }

  onGenreChange(): void {
    this.filterMovies();
  }

  openMovie(movieId: number): void {
    console.log('Opening movie ID:', movieId);
    this.router.navigate(['/user-movie-details', movieId]);
  }
}
