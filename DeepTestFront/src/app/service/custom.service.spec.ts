import { TestBed, inject } from '@angular/core/testing';

import { CustomService } from './custom.service';

describe('CustomService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CustomService]
    });
  });

  it('should be created', inject([CustomService], (service: CustomService) => {
    expect(service).toBeTruthy();
  }));
});
