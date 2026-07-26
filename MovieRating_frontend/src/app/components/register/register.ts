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
    fullName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    mobile: ['', [
      Validators.required,
      Validators.pattern('^[6-9]\\d{9}$')
    ]],
    password: ['', [
      Validators.required,
      Validators.minLength(6)
    ]],
    role: ['USER', Validators.required]
  });

  register() {
    if (this.registerForm.invalid) {
      return;
    }
    this.authService.register(this.registerForm.value)
      .subscribe({
        next: (res: any) => {
          this.snackBar.open(res.message, 'Close', {
            duration: 3000
          });
          this.router.navigate(['/login']);
        },
        error: (err) => {
          this.snackBar.open(err.error.message, 'Close', {
            duration: 3000
          });
        }
      });
  }
}
