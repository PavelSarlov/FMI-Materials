import { TestBed } from '@angular/core/testing';

import { CrossEventService } from './cross-event.service';

describe('CrossEventService', () => {
  let service: CrossEventService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CrossEventService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
