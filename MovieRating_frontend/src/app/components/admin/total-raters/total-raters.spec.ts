import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TotalRaters } from './total-raters';

describe('TotalRaters', () => {
  let component: TotalRaters;
  let fixture: ComponentFixture<TotalRaters>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TotalRaters],
    }).compileComponents();

    fixture = TestBed.createComponent(TotalRaters);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
