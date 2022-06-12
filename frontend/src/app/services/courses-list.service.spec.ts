import { TestBed } from '@angular/core/testing';

import { CoursesListService } from './courses-list.service';

describe('CoursesListService', () => {
  let service: CoursesListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CoursesListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
