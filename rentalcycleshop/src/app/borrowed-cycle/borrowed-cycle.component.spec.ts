import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowedCycleComponent } from './borrowed-cycle.component';

describe('BorrowedCycleComponent', () => {
  let component: BorrowedCycleComponent;
  let fixture: ComponentFixture<BorrowedCycleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BorrowedCycleComponent]
    });
    fixture = TestBed.createComponent(BorrowedCycleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
