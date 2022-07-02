import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';
import {Course} from '../../models/course';
import {User} from '../../models/user';
import {AlertService} from '../../services/alert.service';
import {AuthService} from '../../services/auth.service';
import {CoursesListService} from '../../services/courses-list.service';
import {FavouriteCoursesService} from '../../services/favourite-courses.service';

@Component({
  selector: 'app-course-card',
  templateUrl: './course-card.component.html',
  styleUrls: ['./course-card.component.scss']
})
export class CourseCardComponent implements OnInit, OnDestroy {

  @Input() course!: Course;
  authSubscription?: Subscription;
  currentUser?: User | null;
  url: string = window.location.pathname;

  constructor(private coursesListService: CoursesListService,
    private favouriteCoursesService: FavouriteCoursesService,
    private authService: AuthService,
    private alertService: AlertService,
    private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.authSubscription = this.authService.user$.subscribe(
      (resp) => {
        this.currentUser = resp;
      }
    );
  }

  ngOnDestroy() {
    this.authSubscription?.unsubscribe();
  }

  deleteCourseFromList() {
    let coursesListId = parseInt(this.activatedRoute.snapshot.paramMap.get('coursesListId')!);

    this.coursesListService.deleteCourseFromCoursesList(this.currentUser!.id!, coursesListId, this.course.id!).subscribe({
      next: () => this.alertService.success('Course removed from list successfully'),
      error: (err) => this.alertService.error(err.error.error)
    });
  }

  deleteCourseFromFavourites() {
    this.favouriteCoursesService.deleteCourseFromFavourites(this.currentUser!.id!, this.course.id!).subscribe({
      next: () => this.alertService.success('Course removed from favourites successfully'),
      error: (err) => this.alertService.error(err.error.error)
    });
  }
}
