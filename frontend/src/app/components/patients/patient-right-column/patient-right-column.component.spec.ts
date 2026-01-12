import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientRightColumnComponent } from './patient-right-column.component';

describe('PatientRightColumnComponent', () => {
  let component: PatientRightColumnComponent;
  let fixture: ComponentFixture<PatientRightColumnComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PatientRightColumnComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PatientRightColumnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
