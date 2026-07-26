import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserMyRatings } from './user-my-ratings';

describe('UserMyRatings', () => {
  let component: UserMyRatings;
  let fixture: ComponentFixture<UserMyRatings>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserMyRatings],
    }).compileComponents();

    fixture = TestBed.createComponent(UserMyRatings);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
