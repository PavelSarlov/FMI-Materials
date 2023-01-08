import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription, takeUntil, Subject } from 'rxjs';
import { Course } from '../../models/course';
import { CourseGroup, COURSE_GROUPS } from '../../models/course-group';
import { FacultyDepartment } from '../../models/faculty-department';
import { User } from '../../models/user';
import { AlertService } from '../../services/alert.service';
import { AuthService } from '../../services/auth.service';
import { CourseService } from '../../services/course.service';
import { FacultyDepartmentService } from '../../services/faculty-department.service';

@Component({
  selector: 'app-course-create-form',
  templateUrl: './course-create-form.component.html',
  styleUrls: ['./course-create-form.component.scss'],
})
export class CourseCreateFormComponent implements OnInit, OnDestroy {
  authSubscription?: Subscription;

  user?: User;

  createCourseForm!: FormGroup;

  facultyDepartments: FacultyDepartment[] = [];

  COURSE_GROUPS = COURSE_GROUPS;

  courseId?: number = undefined;

  private unsubscribe$ = new Subject();

  constructor(
    private facultyDepartmentService: FacultyDepartmentService,
    private courseService: CourseService,
    private alertService: AlertService,
    private authService: AuthService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.authService.user$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((user) => {
        this.user = user;
      });

    this.facultyDepartmentService
      .getAllFacultyDepartments()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (resp) => (this.facultyDepartments = resp),
        error: (resp) => this.alertService.error(resp.error.error),
      });

    this.createCourseForm = this.fb.group({
      name: [
        '',
        [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(50),
        ],
      ],
      facultyDepartment: [''],
      courseGroup: [''],
      description: ['', [Validators.maxLength(255)]],
    });
  }

  ngOnDestroy() {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
  }

  get courseData() {
    return this.createCourseForm.controls;
  }

  createCourse() {
    if (this.createCourseForm.valid) {
      let course = new Course();
      course.name = this.createCourseForm.get('name')?.value;
      course.facultyDepartmentDto = new FacultyDepartment();
      course.facultyDepartmentDto.id =
        this.createCourseForm.get('facultyDepartment')?.value;
      course.courseGroup = new CourseGroup();
      course.courseGroup.id = this.createCourseForm.get('courseGroup')?.value;
      course.courseGroup.name = (<any>COURSE_GROUPS)[course.courseGroup.id!];
      course.createdBy = this.user?.name;
      course.description = this.createCourseForm.get('description')?.value;

      this.createCourseForm.reset();

      this.courseService.createCourse(course).subscribe({
        next: (resp) => {
          this.alertService.success('Course created successfully!');
          this.courseId = resp.id;
        },
      });
    }
  }
}
