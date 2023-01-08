import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { CoursesList } from '../../models/coursesList';
import { User } from '../../models/user';
import { AlertService } from '../../services/alert.service';
import { AuthService } from '../../services/auth.service';
import { CoursesListService } from '../../services/courses-list.service';

@Component({
  selector: 'app-course-list-card',
  templateUrl: './course-list-card.component.html',
  styleUrls: ['./course-list-card.component.scss'],
})
export class CourseListCardComponent implements OnInit, OnDestroy {
  @Input() list!: CoursesList;
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
  }

  ngOnDestroy() {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
  }

  deleteList(coursesListId: number) {
    this.coursesListService
      .deleteCourseList(this.currentUser!.id!, coursesListId!)
      .subscribe({
        next: () =>
          this.alertService.success('Course list deleted successfully'),
        error: (err) => this.alertService.error(err.error.error),
      });
  }
}
