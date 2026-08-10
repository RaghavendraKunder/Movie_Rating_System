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

  this.authService.login(this.loginForm.value).subscribe({
  next: (response: any) => {

  localStorage.setItem('token', response.token);
  localStorage.setItem('role', response.role);
  localStorage.setItem('userId', response.userId);
  localStorage.setItem('email', response.email);
  localStorage.setItem('fullName', response.fullName);

  this.snackBar.open(
    'Login Successful',
    'Close',
    {
      duration: 3000
    }
  );

  if(response.role === 'ADMIN'){
    this.router.navigate(['/admin-dashboard']);
  }else{
    this.router.navigate(['/user-dashboard']);
  }

},

    error: (error) => {
      this.snackBar.open(error.error.message, 'Close', {
        duration: 3000
      });
    }
  });
}
}