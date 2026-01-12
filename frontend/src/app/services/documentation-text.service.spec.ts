import { TestBed } from '@angular/core/testing';

import { DocumentationTextService } from './documentation-text.service';

describe('DocumentationTextService', () => {
  let service: DocumentationTextService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocumentationTextService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
