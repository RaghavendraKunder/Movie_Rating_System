import { Component, EventEmitter, Output } from '@angular/core';
import { Sidebar} from '../../../services/sidebar';

@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css']
})
export class Navbar {

  @Output() menuToggle = new EventEmitter<void>();
  constructor(private sidebarService: Sidebar) {}
  
  toggleSidebar(): void {
    this.sidebarService.toggleSidebar();
  }

  toggleMenu() {
    this.menuToggle.emit();
  }
}