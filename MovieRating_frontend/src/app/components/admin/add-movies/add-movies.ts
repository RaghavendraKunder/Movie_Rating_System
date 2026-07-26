import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-add-movies',
  standalone: false,
  templateUrl: './add-movies.html',
  styleUrl: './add-movies.css',
})
export class AddMovies {
  movieForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {

    this.movieForm = this.fb.group({

      title: [''],
      description: [''],
      releaseDate: [''],
      duration: [''],
      language: [''],
      genres: [[]],
      director: [''],
      cast: [''],
      imdbRating: [''],
      userRating: [''],
      posterUrl: [''],
      bannerUrl: [''],
      trailerUrl: ['']

    });

  }
}
