import { isPlatformBrowser } from '@angular/common';
import {
  Component,
  Inject,
  OnInit,
  PLATFORM_ID,
  ChangeDetectorRef
} from '@angular/core';

import {
  FormBuilder,
  FormGroup,
  Validators
} from '@angular/forms';

import { Genre } from '../../../models/genre';
import { GenreService } from '../../../services/genre';
import { MovieRequestService } from '../../../services/movie-request';

@Component({
  selector: 'app-add-request',
  standalone: false,
  templateUrl: './add-request.html',
  styleUrl: './add-request.css',
})
export class AddRequest implements OnInit {

  movieForm!: FormGroup;

  isSubmitting = false;

  genres: Genre[] = [];

  constructor(
    private fb: FormBuilder,
    private genreService: GenreService,
    private cdr: ChangeDetectorRef,
    private movieRequestService: MovieRequestService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {

    this.movieForm = this.fb.group({

      title: [
        '',
        [
          Validators.required,
          Validators.maxLength(150)
        ]
      ],

      description: [
        '',
        [
          Validators.required,
          Validators.maxLength(3000)
        ]
      ],

      releaseDate: [
        '',
        Validators.required
      ],

      duration: [
        '',
        [
          Validators.required,
          Validators.min(1),
          Validators.max(500)
        ]
      ],

      language: [
        '',
        Validators.required
      ],

      genreIds: [
        [] as number[],
        Validators.required
      ],

      director: [
        '',
        Validators.required
      ],

      cast: [
        '',
        Validators.required
      ],

      posterUrl: [
        '',
        Validators.required
      ],

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

 sendRequest(): void {

    if (this.movieForm.invalid) {

      this.movieForm.markAllAsTouched();

      console.log(
        'Form is invalid'
      );

      return;
    }

    this.isSubmitting = true;

    const formValue = this.movieForm.value;

    /*
     * Angular Material datepicker returns a JavaScript Date.
     *
     * Spring Boot LocalDate expects:
     * yyyy-MM-dd
     */

    let releaseDate = formValue.releaseDate;

    if (releaseDate instanceof Date) {

      releaseDate =
        releaseDate.toISOString().split('T')[0];

    }

    const requestData = {

      title: formValue.title,

      description: formValue.description,

      releaseDate: releaseDate,

      duration: formValue.duration,

      language: formValue.language,

      genreIds: formValue.genreIds,

      director: formValue.director,

      cast: formValue.cast,

      posterUrl: formValue.posterUrl,

      trailerUrl: formValue.trailerUrl

    };

    console.log(
      'Movie request being sent:',
      requestData
    );

    this.movieRequestService
      .createRequest(requestData)
      .subscribe({

        next: (response) => {

          console.log(
            'Movie request submitted successfully:',
            response
          );

          alert(
            'Movie request sent successfully!'
          );

          this.movieForm.reset();

          this.movieForm.patchValue({
            genreIds: []
          });

          this.isSubmitting = false;

        },

        error: (err) => {

          console.error(
            'Error submitting movie request:',
            err
          );

          if (err.status === 400) {

            alert(
              'Invalid movie request. Please check the form.'
            );

          } else if (err.status === 401) {

            alert(
              'You are not logged in.'
            );

          } else if (err.status === 403) {

            alert(
              'You do not have permission to submit a request.'
            );

          } else {

            alert(
              'Failed to send movie request.'
            );

          }

          this.isSubmitting = false;

        }

      });

  }

}