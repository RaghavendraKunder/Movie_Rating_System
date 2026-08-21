import { Component, OnInit } from '@angular/core';
import { Admin } from '../../../services/admin';
import { AdminUser } from '../../../models/admin-user';

@Component({
  selector: 'app-total-raters',
  standalone: false,
  templateUrl: './total-raters.html',
  styleUrl: './total-raters.css',
})

export class TotalRaters  implements OnInit {

  users: AdminUser[] = [];
  filteredUsers: AdminUser[] = [];
  selectedUser: AdminUser | null = null;
  searchTerm: string = '';
  sortOrder: string = 'NEWEST';
  loading: boolean = false;

  constructor(
    private adminService: Admin
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading = true;
    console.log('Loading users...');
    this.adminService
      .getAllUsers()
      .subscribe({
        next: (data: AdminUser[]) => {
          console.log('Users loaded:', data);
          this.users = data || [];
          this.applyFilters();
          this.selectedUser = this.filteredUsers.length > 0 ? this.filteredUsers[0] : null;
          this.loading = false;
          console.log(
            'Filtered users:',
            this.filteredUsers
          );
        },
        error: (error) => {
          console.error(
            'Error loading users:',
            error
          );
          this.users = [];
          this.filteredUsers = [];
          this.selectedUser = null;
          this.loading = false;
        }
      });
  }

  applyFilters(): void {
    let result = [...this.users];
    if (this.searchTerm.trim()) {
      const search = this.searchTerm.toLowerCase();
      result = result.filter(user =>
        user.fullName
          .toLowerCase()
          .includes(search)
        ||
        user.email
          .toLowerCase()
          .includes(search)
      );
    }
    result.sort((a, b) => {
      const dateA = new Date(a.joinedAt).getTime();
      const dateB = new Date(b.joinedAt).getTime();
      if (this.sortOrder === 'NEWEST') {
        return dateB - dateA;
      }
      return dateA - dateB;
    });
    this.filteredUsers = result;
  }

  onSearch(): void {
    this.applyFilters();
  }

  onSortChange(): void {
    this.applyFilters();
  }

  selectUser(user: AdminUser): void {
    this.selectedUser = user;
  }

  getInitials(user: AdminUser): string {
    if (!user.fullName) {
      return '?';
    }
    return user.fullName
      .split(' ')
      .map(name => name.charAt(0))
      .join('')
      .substring(0, 2)
      .toUpperCase();
  }

  get totalUsers(): number {
    return this.users.length;
  }

  get totalRatings(): number {
    return this.users.reduce(
      (total, user) =>
        total + (user.totalRatings || 0),
      0
    );
  }

  getProfileImage(user: AdminUser): string | null {
    if (
      user.profilePicture &&
      user.profilePicture.trim() !== ''
    ) {
      return user.profilePicture;
    }
    return null;
  }
}
