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
  { path: 'user-all-movies', component: UserAllMovies}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
