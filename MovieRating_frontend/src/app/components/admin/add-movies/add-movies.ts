import { isPlatformBrowser } from '@angular/common';
import { Component, Inject, OnInit, PLATFORM_ID,ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Genre } from '../../../models/genre';
import { GenreService } from '../../../services/genre';
import { MovieService } from '../../../services/movie';

@Component({
  selector: 'app-add-movies',
  standalone: false,
  templateUrl: './add-movies.html',
  styleUrl: './add-movies.css'
})

export class AddMovies implements OnInit {
  movieForm!: FormGroup;
  isSubmitting = false;
  genres: Genre[] = [];
  constructor(
    private fb: FormBuilder,
    private genreService: GenreService,
     private cdr: ChangeDetectorRef,
     private movieService: MovieService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}
  ngOnInit(): void {
    this.movieForm = this.fb.group({
      title: ['',[Validators.required, Validators.maxLength(150)]],

      description: ['',[Validators.required, Validators.maxLength(3000)]],

      releaseDate: ['', Validators.required],

      duration: ['',[Validators.required, Validators.min(1), Validators.max(500)]],

      language: ['', Validators.required],

      genreIds: [[] as number[], Validators.required],

      director: ['', Validators.required],

      cast: ['', Validators.required],

      posterUrl: ['', Validators.required],

      trailerUrl: ['']
    });
    if (isPlatformBrowser(this.platformId)) {
      this.loadGenres();
    }
  }
  loadGenres(): void {
    this.genreService.getGenres().subscribe({
      next: (data: Genre[]) => {
        this.genres = data;
        console.log('Genres loaded:', this.genres);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading genres:', err);
      }
    });
  }
  
  addMovie(): void {
    if (this.movieForm.invalid) {
      this.movieForm.markAllAsTouched();
      console.log('Form is invalid');
      return;
    }
    this.isSubmitting = true;
    const movieData = this.movieForm.value;
    console.log('Movie data being sent:', movieData);
    this.movieService.addMovie(movieData).subscribe({
      next: (response) => {
        console.log('Movie added successfully:', response);
        alert('Movie added successfully!');
        this.movieForm.reset();
        this.movieForm.patchValue({
          genreIds: []
        });
        this.isSubmitting = false;
      },
      error: (err) => {
        console.error('Error adding movie:', err);
        if (err.status === 400) {
          alert('Invalid movie data. Please check the form.');
        } else if (err.status === 401) {
          alert('You are not logged in.');
        } else if (err.status === 403) {
          alert('Only administrators can add movies.');
        } else {
          alert('Failed to add movie.');
        }
        this.isSubmitting = false;
      }

    });

  }

}