import {
  Component,
  Inject,
  OnInit,
  PLATFORM_ID,
  afterNextRender
} from '@angular/core';

import { MovieRequestService } from '../../../services/movie-request';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-movie-request',
  standalone: false,
  templateUrl: './movie-request.html',
  styleUrl: './movie-request.css',
})
export class MovieRequest implements OnInit {

  requests: any[] = [];
  filteredRequests: any[] = [];
  selectedRequest: any = null;
  searchText: string = '';
  selectedStatus: string = 'ALL';
  loading: boolean = false;

  constructor(
    private movieRequestService: MovieRequestService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {

    // LOAD ONLY AFTER ANGULAR INITIAL RENDER / HYDRATION
  if (isPlatformBrowser(this.platformId)) {
      afterNextRender(() => {this.loadRequests();});
    }
  }

  ngOnInit(): void { }


  // LOAD REQUESTS
  loadRequests(): void {
    this.loading = true;
    console.log('Loading movie requests...');
    this.movieRequestService
      .getAllRequests()
      .subscribe({
        next: (data) => {
          console.log(
            'Movie requests loaded:', data
          );
          this.requests = data || [];
          if (this.requests.length > 0) {
            this.selectedRequest = this.requests[0];
          } else {
            this.selectedRequest = null;
          }
          this.applyFilters();
          this.loading = false;
        },
        error: (error) => {
          console.error(
            'Error loading movie requests:',error
          );
          this.requests = [];
          this.filteredRequests = [];
          this.selectedRequest = null;
          this.loading = false;
        }
      });
  }

  // APPLY FILTERS
  applyFilters(): void {
    const search = this.searchText
        .toLowerCase()
        .trim();
    this.filteredRequests =
      this.requests.filter(request => {
        const matchesSearch =
          !search || request.title?.toLowerCase()
            .includes(search) || request.requestedBy?.toLowerCase()
            .includes(search);
        const matchesStatus =
          this.selectedStatus === 'ALL' || request.status === this.selectedStatus;
        return matchesSearch && matchesStatus;
      });
    console.log(
      'Filtered requests:', this.filteredRequests
    );
  }

  // SELECT REQUEST
  selectRequest(request: any): void {
    this.selectedRequest = request;
  }

  // APPROVE
  approveRequest(): void {
    if (!this.selectedRequest) {
      console.error('No movie request selected');
      return;
    }
    const requestId = this.selectedRequest.id;
    this.movieRequestService
      .approveRequest(requestId)
      .subscribe({
        next: (updatedRequest) => {
          console.log(
            'Movie request approved successfully:',
            updatedRequest
          );
          this.selectedRequest = updatedRequest;
          this.loadRequests();
        },
        error: (error) => {
          console.error(
            'Error approving movie request:',
            error
          );
        alert(
            error.error?.message ||
            'Failed to approve movie request.'
        );
      }
    });
}

  // REJECT
  rejectRequest(): void {
    if (!this.selectedRequest) {
      return;
    }
    const id = this.selectedRequest.id;
    if (!confirm(`Reject "${this.selectedRequest.title}"?`)) {
      return;
    }
    this.movieRequestService
      .rejectRequest(id)
      .subscribe({
        next: (updatedRequest) => {
          console.log(
            'Movie request rejected:',
            updatedRequest
          );
          this.updateRequest(updatedRequest);
        },
        error: (error) => {
          console.error(
            'Error rejecting request:',
            error
          );
        }
      });
  }

  // UPDATE REQUEST
  private updateRequest(updatedRequest: any): void {
    const index =this.requests.findIndex(request => request.id === updatedRequest.id);
    if (index !== -1) {
      this.requests[index] = updatedRequest;
      this.selectedRequest = updatedRequest;
      this.applyFilters();
    }
  }

  // STATUS CLASS
  getStatusClass(status: string): string {
    switch (status) {
      case 'APPROVED': return 'approved';
      case 'REJECTED': return 'rejected';
      default: return 'pending';
    }
  }

  // FORMAT DATE
  formatDate(date: string): string {
    if (!date) {
      return '';
    }
    return new Date(date).toLocaleDateString();
  }
}