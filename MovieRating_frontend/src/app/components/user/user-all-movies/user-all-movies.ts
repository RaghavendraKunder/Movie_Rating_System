import {Component, OnInit, ChangeDetectorRef} from '@angular/core';
import {Router} from '@angular/router';

import {Movie} from '../../../models/movies';
import {MovieService} from '../../../services/movie';
import {Watchlist} from '../../../services/watchlist';
import { WatchlistMovie } from '../../../models/watchlist';


@Component({
  selector: 'app-user-all-movies',
  standalone: false,
  templateUrl: './user-all-movies.html',
  styleUrl: './user-all-movies.css',
})

export class UserAllMovies implements OnInit {

  movies: Movie[] = [];
  filteredMovies: Movie[] = [];
  searchTerm: string = '';
  genres: string[] = [];
  selectedGenre: string = '';
  bookmarkedMovies: Set<number> = new Set<number>();

  constructor(
    private movieService:MovieService,
    private watchlistService:Watchlist,
    private cdr:ChangeDetectorRef,
    private router:Router
  ) {}


  // INITIALIZE
  ngOnInit(): void {
    this.loadMovies();
    this.loadWatchlist();
  }

  // LOAD MOVIES
  loadMovies(): void {
    this.movieService
      .getAllMovies()
      .subscribe({
        next: (data: Movie[]) => {
          console.log('Movies loaded:',data);
          this.movies = [...data].sort(
              (a, b) =>new Date(b.releaseDate).getTime()
                  -new Date(a.releaseDate).getTime());
          this.filteredMovies = [...this.movies];
          // Extract genres
          const genreSet = new Set<string>();
          this.movies.forEach(movie => {
              if (movie.genres) {
                movie.genres.forEach(genre => {
                    genreSet.add(genre);
                }
                );
              }
            }
          );
          this.genres = Array.from(genreSet);
          console.log('Genres:',this.genres);
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error('Error loading movies:',err);
        }
      });
  }

  // LOAD USER WATCHLIST

  loadWatchlist(): void {
    this.watchlistService
      .getWatchlist()
      .subscribe({
        next: (data: WatchlistMovie[]) => {
          console.log('Watchlist loaded:',data);
          this.bookmarkedMovies.clear();
          data.forEach(movie => {
              if (movie.movieId) {
                this.bookmarkedMovies.add(movie.movieId);
              }
            }
          );
          this.cdr.detectChanges();
        },
        error: (error) => {
          console.error('Error loading watchlist:',error);
        }
      });
  }
  // FILTER MOVIES
  filterMovies(): void {
    const search = this.searchTerm
        .toLowerCase()
        .trim();
    this.filteredMovies = this.movies.filter(movie => {
          // Search
          const matchesSearch = !search || movie.title
                                .toLowerCase()
                                .includes(search);
          // Genre
          const matchesGenre = !this.selectedGenre || movie.genres.includes(this.selectedGenre);
          return (matchesSearch && matchesGenre);
        }
      );
  }

  // OPEN MOVIE DETAILS
  openMovie(movieId: number): void {
    console.log('Opening movie ID:',movieId);
    this.router.navigate(['/user-movie-details',movieId]);
  }

  // CHECK BOOKMARK
  isBookmarked(movieId: number): boolean {
    return this.bookmarkedMovies.has(movieId);
  }

  // ADD / REMOVE WATCHLIST
  toggleBookmark(movieId: number, event: MouseEvent): void {
    event.stopPropagation();
    // REMOVE
    if (this.bookmarkedMovies.has(movieId)) {
      this.watchlistService
        .removeFromWatchlist(movieId)
        .subscribe({next: () => {
            this.bookmarkedMovies.delete(movieId);
            console.log('Movie removed from watchlist:',movieId);
            this.cdr.detectChanges();
          },
          error: (error) => {
            console.error('Error removing movie from watchlist:',error);
          }
        });
    }
    // ADD
    else {
      this.watchlistService
        .addToWatchlist(movieId)
        .subscribe({next: (response) => {
            this.bookmarkedMovies.add(movieId);
            console.log('Movie added to watchlist:',response);
            this.cdr.detectChanges();
          },
          error: (error) => {
            console.error('Error adding movie to watchlist:',error);
          }
        });
    }
  }
}