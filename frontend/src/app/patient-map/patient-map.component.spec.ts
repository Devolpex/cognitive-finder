import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientMapComponent } from './patient-map.component';

describe('PatientMapComponent', () => {
  let component: PatientMapComponent;
  let fixture: ComponentFixture<PatientMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PatientMapComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PatientMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
