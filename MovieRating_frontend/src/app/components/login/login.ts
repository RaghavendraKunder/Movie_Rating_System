import { Component } from '@angular/core';
import { Auth} from '../../services/auth';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})

export class Login {
  hide = true;
  loginForm!:FormGroup;
  constructor(
    private fb: FormBuilder,
    private authService: Auth,
    private router: Router,
    private snackBar: MatSnackBar
  ){ 
    this.loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  });}


  login() {
    if (this.loginForm.invalid) {
      return;
    }
    this.authService.login(this.loginForm.value)
      .subscribe({
        next: (response) => {
          this.snackBar.open(
            response.message,
            'Close',
            {
              duration: 3000
            });
          this.router.navigate(['/dashboard']);
        },
        error: (error) => {
          this.snackBar.open(
            error.error.message,
            'Close',
            {
              duration: 3000
            });
        }
      });
  }
}
