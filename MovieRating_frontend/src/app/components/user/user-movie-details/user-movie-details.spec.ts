import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserMovieDetails } from './user-movie-details';

describe('UserMovieDetails', () => {
  let component: UserMovieDetails;
  let fixture: ComponentFixture<UserMovieDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserMovieDetails],
    }).compileComponents();

    fixture = TestBed.createComponent(UserMovieDetails);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
