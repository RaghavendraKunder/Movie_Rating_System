import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAllMovies } from './user-all-movies';

describe('UserAllMovies', () => {
  let component: UserAllMovies;
  let fixture: ComponentFixture<UserAllMovies>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserAllMovies],
    }).compileComponents();

    fixture = TestBed.createComponent(UserAllMovies);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
