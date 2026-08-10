import { Component, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Auth } from '../../services/auth';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.html',
  styleUrls: ['./register.css'],
})
export class Register {

  private fb = inject(FormBuilder);
  private authService = inject(Auth);
  private snackBar = inject(MatSnackBar);
  private router = inject(Router);

  hide = true;

  registerForm = this.fb.group({

    fullName: [
      '',
      [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(50)
      ]
    ],

    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],

    phone: [
      '',
      [
        Validators.required,
        Validators.pattern('^[6-9]\\d{9}$')
      ]
    ],

    password: [
      '',
      [
        Validators.required,
        Validators.minLength(8),
        Validators.pattern(
          '^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$'
        )
      ]
    ],

    role: [
      'USER',
      Validators.required
    ]

  });

  register() {

    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    console.log(this.registerForm.value);

    this.authService.register(this.registerForm.value)
      .subscribe({

        next: (res: any) => {

          this.snackBar.open(
            'Registration Successful',
            'Close',
            { duration: 3000 }
          );

          this.router.navigate(['/login']);
        },

        error: (err) => {

          this.snackBar.open(
            err.error?.message || 'Registration Failed',
            'Close',
            { duration: 3000 }
          );

        }

      });

  }

}