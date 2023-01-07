import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import { StompService } from 'src/app/services/stomp.service';
import {Course} from '../../models/course';
import {User, USER_ROLES} from '../../models/user';
import {AuthService} from '../../services/auth.service';
import {CourseService} from '../../services/course.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss'],
})
export class CoursesComponent implements OnInit, OnDestroy {
  authSubscription?: Subscription;
  paginationSubscription?: Subscription;

  user?: User | null;
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

  constructor(
    private courseService: CourseService,
    private authService: AuthService,
    private stompService: StompService,
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.authService.user$.subscribe(
      (user) => (this.user = user)
    );
    this.authService.isAuthenticated();

    this.paginationSubscription = this.courseService.pagination$.subscribe(
      (pagination) => {
        this.courses = pagination?.items;
        this.itemsPerPage = pagination?.itemsPerPage;
        this.totalPages = pagination?.totalPages;
        this.totalItems = pagination?.totalItems;
        this.currentPage = pagination?.currentPage;
      }
    );
    this.courseService.getCourses();

    this.stompService.subscribe('/topic/course', (): void => {
      this.fetchCourses();
    });
  }

  ngOnDestroy() {
    this.authSubscription?.unsubscribe();
    this.paginationSubscription?.unsubscribe();
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
