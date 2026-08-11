import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule, provideClientHydration, withEventReplay } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { Navbar } from './components/admin/navbar/navbar';
import { UserDashboard } from './components/user/user-dashboard/user-dashboard';
import { AdminDashboard } from './components/admin/admin-dashboard/admin-dashboard';
import { SideNav } from './components/admin/side-nav/side-nav';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatMenuModule } from '@angular/material/menu';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { AllMovies } from './components/admin/all-movies/all-movies';
import { AddMovies } from './components/admin/add-movies/add-movies';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { UseSideNav } from './components/user/use-side-nav/use-side-nav';
import { UserAllMovies } from './components/user/user-all-movies/user-all-movies';
import { UserWatchlist } from './components/user/user-watchlist/user-watchlist';
import { UserMyRatings } from './components/user/user-my-ratings/user-my-ratings';
import { UserNavbar } from './components/user/user-navbar/user-navbar';
import { MovieRequest } from './components/admin/movie-request/movie-request';
import { MatSidenavModule } from '@angular/material/sidenav';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';

import { jwtInterceptor } from './interceptors/jwt-interceptor';
import { MovieDetails } from './components/admin/movie-details/movie-details';
import {UserMovieDetails} from './components/user/user-movie-details/user-movie-details';

@NgModule({
  declarations: [
    App,
    Login,
    Register,
    Navbar,
    UserDashboard,
    AdminDashboard,
    SideNav,
    AllMovies,
    AddMovies,
    UseSideNav,
    UserAllMovies,
    UserWatchlist,
    UserMyRatings,
    UserNavbar,
    MovieRequest,
    MovieDetails,
    UserMovieDetails
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    MatToolbarModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatSelectModule,
    MatSnackBarModule,
    MatMenuModule,
    MatListModule,
    RouterModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSidenavModule,
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideClientHydration(withEventReplay()),
    provideHttpClient(withFetch(), withInterceptors([jwtInterceptor])),
  ],
  bootstrap: [App],
})
export class AppModule {}
