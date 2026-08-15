import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Sidebar {
  private collapsedSubject = new BehaviorSubject<boolean>(false);

  collapsed$ = this.collapsedSubject.asObservable();

  toggleSidebar(): void {
    this.collapsedSubject.next(!this.collapsedSubject.value);
  }
  toggle(): void {
    this.collapsedSubject.next(
      !this.collapsedSubject.value
    );
  }
}
