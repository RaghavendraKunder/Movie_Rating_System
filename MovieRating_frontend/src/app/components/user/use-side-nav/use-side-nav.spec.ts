import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UseSideNav } from './use-side-nav';

describe('UseSideNav', () => {
  let component: UseSideNav;
  let fixture: ComponentFixture<UseSideNav>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UseSideNav],
    }).compileComponents();

    fixture = TestBed.createComponent(UseSideNav);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
