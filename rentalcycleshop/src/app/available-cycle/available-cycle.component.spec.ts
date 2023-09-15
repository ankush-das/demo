import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvailableCycleComponent } from './available-cycle.component';

describe('AvailableCycleComponent', () => {
  let component: AvailableCycleComponent;
  let fixture: ComponentFixture<AvailableCycleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AvailableCycleComponent]
    });
    fixture = TestBed.createComponent(AvailableCycleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
