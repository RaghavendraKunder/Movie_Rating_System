import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-dashboard',
  standalone: false,
  templateUrl: './admin-dashboard.html',
  styleUrls: ['./admin-dashboard.css'],
})
export class AdminDashboard {
  topRated = [
    {
      title: 'Oppenheimer',
      year: 2023,
      genre: 'Drama',
      rating: 8.6,
      votes: 1250,
      posterUrl: 'https://image.tmdb.org/t/p/original/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg'
    },
    {
      title: 'Interstellar',
      year: 2014,
      genre: 'Sci-Fi',
      rating: 8.7,
      votes: 2100,
      posterUrl: 'https://image.tmdb.org/t/p/original/iawqQdFKI7yTUoSkDNP8gyV3J3r.jpg'
    },
    {
      title: 'The Dark Knight',
      year: 2008,
      genre: 'Action',
      rating: 9.0,
      votes: 3500,
      posterUrl: 'https://image.tmdb.org/t/p/original/xQPgyZOBhaz1GdCQIPf5A5VeFzO.jpg'
    },
    {
      title: 'Inception',
      year: 2010,
      genre: 'Sci-Fi',
      rating: 8.8,
      votes: 2800,
      posterUrl: 'https://image.tmdb.org/t/p/original/xlaY2zyzMfkhk0HSC5VUwzoZPU1.jpg'
    }
  ];

 recentlyAdded = [
    {
      title: 'Oppenheimer',
      year: 2023,
      genre: 'Drama',
      rating: 8.6,
      votes: 1250,
      posterUrl: 'https://image.tmdb.org/t/p/original/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg'
    },
    {
      title: 'Interstellar',
      year: 2014,
      genre: 'Sci-Fi',
      rating: 8.7,
      votes: 2100,
      posterUrl: 'https://image.tmdb.org/t/p/original/iawqQdFKI7yTUoSkDNP8gyV3J3r.jpg'
    },
    {
      title: 'The Dark Knight',
      year: 2008,
      genre: 'Action',
      rating: 9.0,
      votes: 3500,
      posterUrl: 'https://image.tmdb.org/t/p/original/xQPgyZOBhaz1GdCQIPf5A5VeFzO.jpg'
    },
    {
      title: 'Inception',
      year: 2010,
      genre: 'Sci-Fi',
      rating: 8.8,
      votes: 2800,
      posterUrl: 'https://image.tmdb.org/t/p/original/xlaY2zyzMfkhk0HSC5VUwzoZPU1.jpg'
    }
  ];

  constructor() {
  }

}