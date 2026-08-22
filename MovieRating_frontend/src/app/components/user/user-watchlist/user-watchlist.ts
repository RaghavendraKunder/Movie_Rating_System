import {
  Component,
  OnInit
} from '@angular/core';

import { Router } from '@angular/router';

import {
  Watchlist
} from '../../../services/watchlist';

import {
  WatchlistMovie
} from '../../../models/watchlist';


@Component({
  selector: 'app-user-watchlist',
  standalone: false,
  templateUrl: './user-watchlist.html',
  styleUrl: './user-watchlist.css',
})
export class UserWatchlist implements OnInit {


  // ==========================
  // WATCHLIST DATA
  // ==========================

  watchlistMovies: WatchlistMovie[] = [];

  filteredMovies: WatchlistMovie[] = [];


  // ==========================
  // SEARCH + GENRE FILTER
  // ==========================

  searchTerm: string = '';

  selectedGenre: string = '';

  genres: string[] = [];


  // ==========================
  // LOADING
  // ==========================

  loading: boolean = false;


  constructor(
    private watchlistService: Watchlist,
    private router: Router
  ) {}


  ngOnInit(): void {

    this.loadWatchlist();

  }


  /**
   * Load logged-in user's watchlist
   */
  loadWatchlist(): void {

    this.loading = true;

    this.watchlistService
      .getWatchlist()
      .subscribe({

        next: (data: WatchlistMovie[]) => {

          console.log(
            'Watchlist loaded:',
            data
          );

          this.watchlistMovies =
            data || [];


          // Extract unique genres

          this.buildGenreList();


          // Initially show all movies

          this.filterMovies();


          this.loading = false;

        },


        error: (error) => {

          console.error(
            'Error loading watchlist:',
            error
          );

          this.watchlistMovies = [];

          this.filteredMovies = [];

          this.genres = [];

          this.loading = false;

        }

      });

  }


  /**
   * Build genre dropdown
   */
  buildGenreList(): void {

    const genreSet =
      new Set<string>();


    this.watchlistMovies.forEach(movie => {

      movie.genres.forEach(genre => {

        genreSet.add(genre);

      });

    });


    this.genres =
      Array.from(genreSet)
        .sort();

  }


  /**
   * Search and filter movies
   */
  filterMovies(): void {

    const search =
      this.searchTerm
        .toLowerCase()
        .trim();


    this.filteredMovies =
      this.watchlistMovies.filter(movie => {


        // Search by title

        const matchesSearch =

          !search ||

          movie.movieTitle
            .toLowerCase()
            .includes(search);


        // Filter by selected genre

        const matchesGenre =

          !this.selectedGenre ||

          movie.genres.includes(
            this.selectedGenre
          );


        return (

          matchesSearch &&

          matchesGenre

        );

      });

  }


  /**
   * Remove movie from watchlist
   */
  removeFromWatchlist(
    movie: WatchlistMovie,
    event: MouseEvent
  ): void {

    event.stopPropagation();


    this.watchlistService
      .removeFromWatchlist(
        movie.movieId
      )
      .subscribe({

        next: () => {


          // Remove movie locally

          this.watchlistMovies =
            this.watchlistMovies.filter(

              item =>
                item.movieId !== movie.movieId

            );


          // Rebuild genre dropdown

          this.buildGenreList();


          // Reset selected genre if it no longer exists

          if (
            this.selectedGenre &&
            !this.genres.includes(
              this.selectedGenre
            )
          ) {

            this.selectedGenre = '';

          }


          // Reapply search/filter

          this.filterMovies();

        },


        error: (error) => {

          console.error(
            'Error removing movie from watchlist:',
            error
          );

        }

      });

  }


  /**
   * Format movie duration
   */
  formatDuration(
    minutes: number
  ): string {

    if (!minutes) {

      return '';

    }


    const hours =
      Math.floor(
        minutes / 60
      );


    const remainingMinutes =
      minutes % 60;


    if (hours === 0) {

      return `${remainingMinutes}m`;

    }


    if (remainingMinutes === 0) {

      return `${hours}h`;

    }


    return `${hours}h ${remainingMinutes}m`;

  }


  /**
   * Open movie details
   */
  openMovie(
    movieId: number
  ): void {

    this.router.navigate([

      '/user-movie-details',

      movieId

    ]);

  }

}