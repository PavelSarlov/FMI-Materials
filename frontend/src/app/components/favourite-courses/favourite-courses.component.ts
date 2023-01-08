import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { Course } from 'src/app/models/course';
import { AuthService } from 'src/app/services/auth.service';
import { User } from '../../models/user';
import { FavouriteCoursesService } from '../../services/favourite-courses.service';

@Component({
  selector: 'app-favourite-courses',
  templateUrl: './favourite-courses.component.html',
  styleUrls: ['./favourite-courses.component.scss'],
})
export class FavouriteCoursesComponent implements OnInit, OnDestroy {
  courses: Course[] = [];
  currentUser?: User;

  private unsubscribe$ = new Subject();

  constructor(
    private favouriteCoursesService: FavouriteCoursesService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.authService.user$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((user) => {
        this.currentUser = user;
      });

    this.favouriteCoursesService.getFavouriteCourses(this.currentUser!.id!);
    this.favouriteCoursesService.courses$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((resp) => {
        this.courses = resp;
      });
  }

  ngOnDestroy() {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
  }
}
