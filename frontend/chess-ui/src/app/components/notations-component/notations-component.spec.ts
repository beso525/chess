import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotationsComponent } from './notations-component';

describe('NotationsComponent', () => {
  let component: NotationsComponent;
  let fixture: ComponentFixture<NotationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NotationsComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NotationsComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
