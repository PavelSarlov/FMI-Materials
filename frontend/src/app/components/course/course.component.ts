import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Course } from '../../models/course';
import { COURSE_GROUPS } from '../../models/course-group';
import { FacultyDepartment } from '../../models/faculty-department';
import { User, USER_ROLES } from '../../models/user';
import { AlertService } from '../../services/alert.service';
import { AuthService } from '../../services/auth.service';
import { CourseService } from '../../services/course.service';
import { CrossEventService } from '../../services/cross-event.service';
import { FacultyDepartmentService } from '../../services/faculty-department.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss'],
})
export class CourseComponent implements OnInit, OnDestroy {
  authSubscription?: Subscription;
  routerSubscription?: Subscription;
  sectionOnDeleteSubscription?: Subscription;

  user?: User | null;
  USER_ROLES = USER_ROLES;

  course?: Course;

  facultyDepartments: FacultyDepartment[] = [];

  COURSE_GROUPS = COURSE_GROUPS;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private courseService: CourseService,
    private alertService: AlertService,
    private authService: AuthService,
    private crossEventService: CrossEventService,
    private fb: FormBuilder,
    private facultyDepartmentService: FacultyDepartmentService
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.authService.user$.subscribe((user) => {
      this.user = user;
    });
    this.authService.isAuthenticated();

    this.routerSubscription = this.activatedRoute.paramMap.subscribe(
      (params) => {
        this.fetchCourse(parseInt(params.get('courseId') ?? ''));
      }
    );

    if (this.user?.roles?.includes(USER_ROLES.ADMIN)) {
      this.facultyDepartmentService.getAllFacultyDepartments().subscribe({
        next: (resp) => (this.facultyDepartments = resp),
        error: (resp) => this.alertService.error(resp.error.error),
      });
    }

    this.sectionOnDeleteSubscription = this.crossEventService.sectionOnDelete.subscribe(() =>
      this.fetchCourse(this.course?.id!)
    );
  }

  ngOnDestroy() {
    this.authSubscription?.unsubscribe();
    this.routerSubscription?.unsubscribe();
    this.sectionOnDeleteSubscription?.unsubscribe();
  }


  fetchCourse(courseId: number) {
    this.courseService.getCourseById(courseId).subscribe({
      next: (course) => {
        this.course = course;
      },
      error: (resp) => {
        this.alertService.error(resp.error.error);
        this.router.navigateByUrl('/courses');
      },
    });
  }

  updateCourse(form: any) {
    let course = this.course!;

    course.name = form.value.name;
    course.description = form.value.description;
    course.courseGroup!.id = form.value.courseGroup;
    course.facultyDepartmentDto!.id = form.value.facultyDepartment;

    this.courseService.updateCourse(course).subscribe({
      next: (resp) => this.alertService.success('Course updated successfully!'),
      error: (resp) => this.alertService.error(resp.error.error),
    });
  }

  deleteCourse() {
    if (window.confirm('Are you sure about that?')) {
      this.courseService.deleteCourseById(this.course!.id!).subscribe({
        next: (resp) => {
          this.alertService.success('Course deleted successfully!', {
            keepAfterRouteChange: true,
          });
          this.router.navigateByUrl('/courses');
        },
        error: (resp) => this.alertService.error(resp.error.error),
      });
    }
  }

  createSection() {}
}
