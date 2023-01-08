import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription, takeUntil, Subject, Observable } from 'rxjs';
import { StompService, TopicSub } from 'src/app/services/stomp.service';
import { Course } from '../../models/course';
import { User, USER_ROLES } from '../../models/user';
import { AuthService } from '../../services/auth.service';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss'],
})
export class CoursesComponent implements OnInit, OnDestroy {
  user?: User;
  USER_ROLES = USER_ROLES;

  courses?: Course[];
  totalItems?: number;
  totalPages?: number;
  itemsPerPage?: number;
  currentPage?: number;
  filter?: string = 'name';
  filterValue?: string;
  sortBy?: string = this.filter;
  desc?: boolean;

  filters: any = {
    name: 'Name',
    description: 'Description',
    createdBy: 'Creator',
  };
  filterKeys = Object.keys(this.filters);

  topic: TopicSub | null = null;
  topic$?: Observable<TopicSub | null>;

  private unsubscribe$ = new Subject();

  constructor(
    private courseService: CourseService,
    private authService: AuthService,
    private stompService: StompService
  ) {}

  ngOnInit(): void {
    this.authService.user$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((user) => {
        this.user = user;
      });

    this.courseService.pagination$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((pagination) => {
        this.courses = pagination?.items;
        this.itemsPerPage = pagination?.itemsPerPage;
        this.totalPages = pagination?.totalPages;
        this.totalItems = pagination?.totalItems;
        this.currentPage = pagination?.currentPage;
      });
    this.courseService.getCourses();

    this.topic$ = this.stompService.subscribe('/course', (): void => {
      this.fetchCourses();
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

  handlePageEvent(event: any) {
    this.currentPage = event.pageIndex;
    this.itemsPerPage = event.pageSize;
    this.fetchCourses();
  }

  onSubmitSearch() {
    this.fetchCourses();
  }

  fetchCourses() {
    this.courseService.getCourses(
      this.filter,
      this.filterValue,
      this.currentPage,
      this.itemsPerPage,
      this.sortBy,
      this.desc
    );
  }
}
