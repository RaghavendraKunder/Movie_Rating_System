import { TestBed } from '@angular/core/testing';

import { MovieRequest } from './movie-request';

describe('MovieRequest', () => {
  let service: MovieRequest;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MovieRequest);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
