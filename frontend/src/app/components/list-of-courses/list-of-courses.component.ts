import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription, takeUntil, Subject } from 'rxjs';
import { CoursesList } from '../../models/coursesList';
import { User } from '../../models/user';
import { AlertService } from '../../services/alert.service';
import { AuthService } from '../../services/auth.service';
import { CoursesListService } from '../../services/courses-list.service';

@Component({
  selector: 'app-list-of-courses',
  templateUrl: './list-of-courses.component.html',
  styleUrls: ['./list-of-courses.component.scss'],
})
export class ListOfCoursesComponent implements OnInit, OnDestroy {
  coursesLists: CoursesList[] = [];
  value: string = '';
  currentUser?: User;

  private unsubscribe$ = new Subject();

  constructor(
    private coursesListService: CoursesListService,
    private authService: AuthService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.authService.user$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((user) => {
        this.currentUser = user;
      });

    this.coursesListService.getCoursesLists(this.currentUser!.id!);
    this.coursesListService.coursesLists$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((resp) => {
        this.coursesLists = resp;
      });
  }

  ngOnDestroy() {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
  }

  addList() {
    this.coursesListService
      .addCourseList(this.currentUser!.id!, this.value)
      .subscribe({
        next: () =>
          this.alertService.success('Course list created successfully'),
        error: (err) => this.alertService.error(err.error.error),
      });
    this.value = '';
  }
}
