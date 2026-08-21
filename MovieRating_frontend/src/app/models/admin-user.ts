export interface AdminUser {

  id: number;

  fullName: string;

  email: string;

  phone?: string;

  profilePicture?: string;

  role: 'USER' | 'ADMIN';

  enabled: boolean;

  totalRatings: number;

  totalReviews: number;

  watchlistCount: number;

  joinedAt: string;

}