import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMovies } from './add-movies';

describe('AddMovies', () => {
  let component: AddMovies;
  let fixture: ComponentFixture<AddMovies>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddMovies],
    }).compileComponents();

    fixture = TestBed.createComponent(AddMovies);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
