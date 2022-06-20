import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveCourseFormComponent } from './save-course-form.component';

describe('SaveCourseFormComponent', () => {
  let component: SaveCourseFormComponent;
  let fixture: ComponentFixture<SaveCourseFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SaveCourseFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SaveCourseFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
