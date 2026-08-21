import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { AdminDashboard } from './components/admin/admin-dashboard/admin-dashboard';
import {Navbar} from './components/admin/navbar/navbar';
import {SideNav} from './components/admin/side-nav/side-nav';
import {AllMovies} from './components/admin/all-movies/all-movies';
import {AddMovies} from './components/admin/add-movies/add-movies';
import { UserDashboard } from './components/user/user-dashboard/user-dashboard';
import { UseSideNav } from './components/user/use-side-nav/use-side-nav';
import { UserAllMovies } from './components/user/user-all-movies/user-all-movies';
import { UserWatchlist } from './components/user/user-watchlist/user-watchlist';
import { UserMyRatings } from './components/user/user-my-ratings/user-my-ratings';
import {MovieRequest} from './components/admin/movie-request/movie-request';
import { MovieDetails } from './components/admin/movie-details/movie-details';
import { AddRequest } from './components/user/add-request/add-request';
import { UserMovieDetails } from './components/user/user-movie-details/user-movie-details';
import { TotalRaters } from './components/admin/total-raters/total-raters';



const routes: Routes = [ 
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: Login },
  { path: 'register', component: Register},
  { path: 'admin-dashboard', component: AdminDashboard},
  { path: 'navbar', component: Navbar},
  { path: 'side-nav', component: SideNav},
  { path: 'all-movies', component: AllMovies},
  { path: 'add-movies', component: AddMovies},
  { path: 'user-dashboard', component: UserDashboard},
  { path: 'use-side-nav', component: UseSideNav},
  { path: 'add-request', component: AddRequest},
  { path: 'user-all-movies', component: UserAllMovies},
  { path: 'user-watchlist', component: UserWatchlist},
  { path: 'user-my-ratings', component: UserMyRatings},
  { path: 'movie-request', component: MovieRequest},
  { path: 'movie-details/:id', component: MovieDetails},
  { path: 'user-movie-details/:id', component:UserMovieDetails},
  { path: 'total-raters', component: TotalRaters}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
