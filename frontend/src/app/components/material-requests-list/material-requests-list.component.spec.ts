import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaterialRequestsListComponent } from './material-requests-list.component';

describe('MaterialRequestsListComponent', () => {
  let component: MaterialRequestsListComponent;
  let fixture: ComponentFixture<MaterialRequestsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MaterialRequestsListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MaterialRequestsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
