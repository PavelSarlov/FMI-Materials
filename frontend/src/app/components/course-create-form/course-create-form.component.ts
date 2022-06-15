import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { CourseGroup, COURSE_GROUPS } from '../../models/course-group';
import { FacultyDepartment } from '../../models/faculty-department';
import { User, USER_ROLES } from '../../models/user';
import { Course } from '../../models/course';
import { FacultyDepartmentService } from '../../services/faculty-department.service';
import { CourseService } from '../../services/course.service';
import { AlertService } from '../../services/alert.service';
import { AuthService } from '../../services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-course-create-form',
  templateUrl: './course-create-form.component.html',
  styleUrls: ['./course-create-form.component.scss'],
})
export class CourseCreateFormComponent implements OnInit, OnDestroy {
  authSubscription?: Subscription;

  user?: User | null;

  createCourseForm!: FormGroup;

  facultyDepartments: FacultyDepartment[] = [];

  COURSE_GROUPS = COURSE_GROUPS;

  courseId?: number = undefined;

  constructor(
    private facultyDepartmentService: FacultyDepartmentService,
    private courseService: CourseService,
    private alertService: AlertService,
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.authService.user$.subscribe(
      (user) => (this.user = user)
    );

    if (
      !this.authService.isAuthenticated() ||
      !this.user?.roles?.includes(USER_ROLES.ADMIN)
    ) {
      this.alertService.warn(
        'You do not have the necessary permission to do that',
        { keepAfterRouteChange: true }
      );
      this.router.navigateByUrl('courses');
    } else {
      this.facultyDepartmentService.getAllFacultyDepartments().subscribe({
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
  }

  ngOnDestroy() {
    this.authSubscription?.unsubscribe();
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
