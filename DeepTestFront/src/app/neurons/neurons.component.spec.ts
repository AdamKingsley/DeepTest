import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NeuronsComponent } from './neurons.component';

describe('NeuronsComponent', () => {
  let component: NeuronsComponent;
  let fixture: ComponentFixture<NeuronsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NeuronsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NeuronsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
