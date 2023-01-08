import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { CoursesList } from '../../models/coursesList';
import { User } from '../../models/user';
import { CoursesListService } from '../../services/courses-list.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit, OnDestroy {
  coursesLists: CoursesList[] = [];
  currentUser?: User;

  private unsubscribe$ = new Subject();

  constructor(
    private coursesListService: CoursesListService,
    private authService: AuthService
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
}
