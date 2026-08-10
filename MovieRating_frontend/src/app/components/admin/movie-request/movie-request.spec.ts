import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieRequest } from './movie-request';

describe('MovieRequest', () => {
  let component: MovieRequest;
  let fixture: ComponentFixture<MovieRequest>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MovieRequest],
    }).compileComponents();

    fixture = TestBed.createComponent(MovieRequest);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
