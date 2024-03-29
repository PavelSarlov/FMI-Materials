import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, takeUntil, Observable } from 'rxjs';
import { Subscription } from 'src/app/models/subscription';
import { StompService, TopicSub } from 'src/app/services/stomp.service';
import { UserService } from 'src/app/services/user.service';
import { Course } from '../../models/course';
import { COURSE_GROUPS } from '../../models/course-group';
import { FacultyDepartment } from '../../models/faculty-department';
import { Section } from '../../models/section';
import { User, USER_ROLES } from '../../models/user';
import { AlertService } from '../../services/alert.service';
import { AuthService } from '../../services/auth.service';
import { CourseService } from '../../services/course.service';
import { CrossEventService } from '../../services/cross-event.service';
import { FacultyDepartmentService } from '../../services/faculty-department.service';
import { FILE_FORMATS } from '../../vo/file-formats';
import { SaveCourseFormComponent } from '../save-course-form/save-course-form.component';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss'],
})
export class CourseComponent implements OnInit, OnDestroy {
  user?: User;
  USER_ROLES = USER_ROLES;

  course?: Course;
  sectionBackup?: Section[] = [];

  courseId: number = NaN;

  facultyDepartments: FacultyDepartment[] = [];

  COURSE_GROUPS = COURSE_GROUPS;

  createSectionName?: string;

  FILE_FORMATS = FILE_FORMATS;
  materialSearchName: string = '';
  materialSearchFileFormat: string = '';
  materialSearchSectionName: string = '';

  requestsSubscription: Subscription & { checked: boolean } = {
    checked: false,
  };

  topic: TopicSub | null = null;
  topic$?: Observable<TopicSub | null>;

  private unsubscribe$ = new Subject();

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private courseService: CourseService,
    private alertService: AlertService,
    private authService: AuthService,
    private crossEventService: CrossEventService,
    private facultyDepartmentService: FacultyDepartmentService,
    private stompService: StompService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.authService.user$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((user) => {
        this.user = user;

        if (this.user?.roles?.includes(USER_ROLES.ADMIN)) {
          this.facultyDepartmentService
            .getAllFacultyDepartments()
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe({
              next: (resp) => (this.facultyDepartments = resp),
              error: (resp) => this.alertService.error(resp.error.error),
            });
        }

        if (this.user) {
          this.userService.subscriptions$
            .pipe(takeUntil(this.unsubscribe$))
            .subscribe((value) => {
              const result = value.find(
                (sub) => sub.type === 'materialRequests'
              );
              this.requestsSubscription = { ...result, checked: !!result };
            });
          this.userService.findSubscriptionsByUserId(this.user!.id!);
        }
      });

    this.courseId = parseInt(
      this.activatedRoute.snapshot.paramMap.get('courseId')!
    );

    if (isNaN(this.courseId)) {
      this.router.navigateByUrl('/');
    }

    this.fetchCourse(this.courseId);

    this.crossEventService.sectionEvent
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((courseId) => {
        if (courseId === this.courseId) {
          this.fetchCourseSections(this.courseId);
        }
      });

    this.topic$ = this.stompService.subscribe('/topic/section', (): void => {
      this.fetchCourseSections(this.courseId);
    });
    if (this.topic$) {
      this.topic$
        .pipe(takeUntil(this.unsubscribe$))
        .subscribe((topic) => (this.topic = topic));
    }
  }

  ngOnDestroy() {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
    this.stompService.unsubscribe(this.topic);
  }

  openDialog(
    enterAnimationDuration: string,
    exitAnimationDuration: string
  ): void {
    this.dialog.open(SaveCourseFormComponent, {
      width: '25%',
      enterAnimationDuration,
      exitAnimationDuration,
      data: { courseId: this.course?.id },
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

  onUpdateRequestsSubscription() {
    if (this.requestsSubscription.checked) {
      this.userService.createSubscription(this.user!.id!, {
        targetId: this.course!.id!,
        type: 'materialRequests',
      });
    } else {
      this.userService.deleteSubscriptionById(
        this.user!.id!,
        this.requestsSubscription
      );
    }
  }
}
