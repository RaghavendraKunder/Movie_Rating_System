import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {RatingService} from '../../../services/rating';
import {RatingResponse} from '../../../models/rating';

@Component({
  selector: 'app-user-my-ratings',
  standalone: false,
  templateUrl: './user-my-ratings.html',
  styleUrl: './user-my-ratings.css',
})
export class UserMyRatings implements OnInit {

  ratings: RatingResponse[] = [];
  filteredRatings: RatingResponse[] = [];
  paginatedRatings: RatingResponse[] = [];
  searchTerm: string = '';
  loading: boolean = false;
  currentPage: number = 1;
  itemsPerPage: number = 5;
  totalPages: number = 1;

  constructor(
    private ratingService: RatingService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadRatings();
  }


  loadRatings(): void {
    this.loading = true;
    this.ratingService
      .getUserRatings()
      .subscribe({
        next: (data: RatingResponse[]) => {
          console.log('User ratings loaded:',data);
          this.ratings = [...data].sort(
              (a, b) => new Date(b.ratedAt)
                        .getTime() - new Date(a.ratedAt).getTime()
            );
          this.filteredRatings = [...this.ratings];
          this.currentPage = 1;
          this.updatePagination();
          this.loading = false;
        },

        error: (error) => {
          console.error('Error loading ratings:',error);
          this.ratings = [];
          this.filteredRatings = [];
          this.paginatedRatings = [];
          this.loading = false;
        }
      });
  }

  // SEARCH

  filterRatings(): void {
    const search = this.searchTerm
        .toLowerCase()
        .trim();

    this.filteredRatings = this.ratings.filter(
        rating => {
          const movieTitle = rating.movieTitle ?.toLowerCase() || '';
          const genres = rating.genres ?.join(' ').toLowerCase() || '';
          return (!search || movieTitle.includes(search) || genres.includes(search));
        }
      );
    this.currentPage = 1;
    this.updatePagination();
  }

  // PAGINATION

  updatePagination(): void {
    this.totalPages = Math.max(1,
        Math.ceil(
          this.filteredRatings.length /
          this.itemsPerPage
        )
    );
    if (this.currentPage > this.totalPages) {
      this.currentPage = this.totalPages;
    }
    const startIndex =
      (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.paginatedRatings = this.filteredRatings.slice(startIndex, endIndex);
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updatePagination();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updatePagination();
    }
  }

  goToPage(page: number): void {
    this.currentPage = page;
    this.updatePagination();
  }

  getPages(): number[] {
    return Array.from(
      {
        length:
          this.totalPages
      },
      (
        _,
        index
      ) =>
        index + 1
    );
  }

  // RATING TIER
  getRatingTier(rating: number): string {
    if (rating >= 9) {
      return 'ELITE';
    }
    if (rating >= 8) {
      return 'TOP TIER';
    }
    if (rating >= 7) {
      return 'GREAT';
    }
    if (rating >= 6) {
      return 'GOOD';
    }
    if (rating >= 5) {
      return 'AVERAGE';
    }
    return 'LOW';
  }

  // TIER CSS CLASS
  getTierClass(rating: number): string {
    if (rating >= 4) {
      return 'top-tier';
    }
    if (rating >= 3) {
      return 'average';
    }
    return 'low';
  }

  // OPEN MOVIE
  openMovie(movieId: number): void {
    this.router.navigate(['/user-movie-details',movieId]);
  }

  // FORMAT DATE
  formatDate(date: string): string {
    if (!date) {
      return '';
    }
    return new Date(date).toLocaleDateString('en-US',{
        month: 'short',
        day: '2-digit',
        year: 'numeric'
      }
    );
  }
}
