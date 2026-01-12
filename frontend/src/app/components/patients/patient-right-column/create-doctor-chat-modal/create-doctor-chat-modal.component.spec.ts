import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateDoctorChatModalComponent } from './create-doctor-chat-modal.component';

describe('CreateDoctorChatModalComponent', () => {
  let component: CreateDoctorChatModalComponent;
  let fixture: ComponentFixture<CreateDoctorChatModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateDoctorChatModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateDoctorChatModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
