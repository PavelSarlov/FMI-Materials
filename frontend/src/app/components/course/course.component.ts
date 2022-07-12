import {Component, OnDestroy, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {Course} from '../../models/course';
import {COURSE_GROUPS} from '../../models/course-group';
import {FacultyDepartment} from '../../models/faculty-department';
import {Section} from '../../models/section';
import {User, USER_ROLES} from '../../models/user';
import {AlertService} from '../../services/alert.service';
import {AuthService} from '../../services/auth.service';
import {CourseService} from '../../services/course.service';
import {CrossEventService} from '../../services/cross-event.service';
import {FacultyDepartmentService} from '../../services/faculty-department.service';
import {FILE_FORMATS} from '../../vo/file-formats';
import {SaveCourseFormComponent} from '../save-course-form/save-course-form.component';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss'],
})
export class CourseComponent implements OnInit, OnDestroy {
  authSubscription?: Subscription;
  sectionEventSubscription?: Subscription;

  user?: User | null;
  USER_ROLES = USER_ROLES;

  course?: Course;
  sectionBackup?: Section[] = [];

  facultyDepartments: FacultyDepartment[] = [];

  COURSE_GROUPS = COURSE_GROUPS;

  createSectionName?: string;

  FILE_FORMATS = FILE_FORMATS;
  materialSearchName: string = '';
  materialSearchFileFormat: string = '';
  materialSearchSectionName: string = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private courseService: CourseService,
    private alertService: AlertService,
    private authService: AuthService,
    private crossEventService: CrossEventService,
    private facultyDepartmentService: FacultyDepartmentService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.authService.user$.subscribe((user) => {
      this.user = user;
    });
    this.authService.isAuthenticated();

    this.fetchCourse(
      parseInt(this.activatedRoute.snapshot.paramMap.get('courseId')!)
    );

    if (this.user?.roles?.includes(USER_ROLES.ADMIN)) {
      this.facultyDepartmentService.getAllFacultyDepartments().subscribe({
        next: (resp) => (this.facultyDepartments = resp),
        error: (resp) => this.alertService.error(resp.error.error),
      });
    }

    this.sectionEventSubscription =
      this.crossEventService.sectionEvent.subscribe((courseId) => {
        if (courseId === this.course?.id) {
          this.fetchCourseSections(this.course?.id!);
        }
      });
  }

  ngOnDestroy() {
    this.authSubscription?.unsubscribe();
    this.sectionEventSubscription?.unsubscribe();
  }

  openDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    this.dialog.open(SaveCourseFormComponent, {
      width: '25%',
      enterAnimationDuration,
      exitAnimationDuration,
    });
  }

  fetchCourse(courseId: number) {
    this.courseService.getCourseById(courseId).subscribe({
      next: (course) => {
        this.course = course;
        this.sectionBackup = course.sectionDtos;
      },
      error: (resp) => {
        this.alertService.error(resp.error.error);
        this.router.navigateByUrl('/courses');
      },
    });
  }

  fetchCourseSections(courseId: number) {
    this.courseService.getCourseSections(courseId).subscribe({
      next: (resp) => {
        this.course!.sectionDtos = resp;
        this.sectionBackup = resp;
      },
      error: (resp) => {
        this.alertService.error(resp.error.error);
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
      next: () => this.alertService.success('Course updated successfully!'),
      error: (resp) => this.alertService.error(resp.error.error),
    });
  }

  deleteCourse() {
    if (window.confirm('Are you sure about that?')) {
      this.courseService.deleteCourseById(this.course!.id!).subscribe({
        next: () => {
          this.alertService.success('Course deleted successfully!', {
            keepAfterRouteChange: true,
          });
          this.router.navigateByUrl('/courses');
        },
        error: (resp) => this.alertService.error(resp.error.error),
      });
    }
  }

  createSection(form: any) {
    if (form.valid) {
      let section = new Section();
      section.name = this.createSectionName;

      this.courseService.createSection(section, this.course!.id!).subscribe({
        next: () => {
          this.alertService.success('Section created successfully!');
          this.fetchCourse(this.course!.id!);
        },
        error: (resp) => this.alertService.error(resp.error.error),
      });
    }
  }

  onMaterialSearch() {
    this.course!.sectionDtos = JSON.parse(JSON.stringify(this.sectionBackup));

    this.course!.sectionDtos = this.course!.sectionDtos!.filter((s) => {
      if (
        !this.materialSearchSectionName ||
        s.name?.match(new RegExp(this.materialSearchSectionName, 'gi'))
      ) {
        s.materialDtos = s.materialDtos?.filter((m) => {
          if (
            (!this.materialSearchFileFormat ||
              m.fileFormat?.match(
                new RegExp(this.materialSearchFileFormat, 'gi')
              )) &&
            (!this.materialSearchName ||
              m.fileName?.match(new RegExp(this.materialSearchName, 'gi')))
          ) {
            return true;
          }
          return false;
        });

        s.materialRequestDtos = s.materialRequestDtos?.filter((m) => {
          if (
            (!this.materialSearchFileFormat ||
              m.fileFormat?.match(
                new RegExp(this.materialSearchFileFormat, 'gi')
              )) &&
            (!this.materialSearchName ||
              m.fileName?.match(new RegExp(this.materialSearchName, 'gi')))
          ) {
            return true;
          }
          return false;
        });

        return true;
      }
      return false;
    });
  }
}
