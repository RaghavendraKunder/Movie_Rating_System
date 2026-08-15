import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-side-nav',
  standalone: false,
  templateUrl: './side-nav.html',
  styleUrls: ['./side-nav.css'],
})
export class SideNav {
   @Input() collapsed = false;
}
