import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseListCardComponent } from './course-list-card.component';

describe('CourseListComponent', () => {
  let component: CourseListCardComponent;
  let fixture: ComponentFixture<CourseListCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CourseListCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CourseListCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
