import { TestBed } from '@angular/core/testing';

import { FavouriteCoursesService } from './favourite-courses.service';

describe('FavouriteCoursesService', () => {
  let service: FavouriteCoursesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FavouriteCoursesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
