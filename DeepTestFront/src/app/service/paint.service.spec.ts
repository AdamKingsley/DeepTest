import { TestBed, inject } from '@angular/core/testing';

import { PaintService } from './paint.service';

describe('PaintService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PaintService]
    });
  });

  it('should be created', inject([PaintService], (service: PaintService) => {
    expect(service).toBeTruthy();
  }));
});
