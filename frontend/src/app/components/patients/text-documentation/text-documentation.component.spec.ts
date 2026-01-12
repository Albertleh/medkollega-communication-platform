import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TextDocumentationComponent } from './text-documentation.component';

describe('TextDocumentationComponent', () => {
  let component: TextDocumentationComponent;
  let fixture: ComponentFixture<TextDocumentationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TextDocumentationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TextDocumentationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
