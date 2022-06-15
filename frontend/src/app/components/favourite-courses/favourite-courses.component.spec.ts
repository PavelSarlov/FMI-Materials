import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavouriteCoursesComponent } from './favourite-courses.component';

describe('FavouriteCoursesComponent', () => {
  let component: FavouriteCoursesComponent;
  let fixture: ComponentFixture<FavouriteCoursesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FavouriteCoursesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FavouriteCoursesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
