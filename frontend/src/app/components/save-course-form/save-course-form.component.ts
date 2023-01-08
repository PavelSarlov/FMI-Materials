import { Component, Input, OnDestroy, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subject, takeUntil } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { Course } from '../../models/course';
import { CoursesListWithCourses } from '../../models/coursesListWithCourses';
import { User } from '../../models/user';
import { AlertService } from '../../services/alert.service';
import { CoursesListService } from '../../services/courses-list.service';
import { FavouriteCoursesService } from '../../services/favourite-courses.service';

@Component({
  selector: 'app-save-course-form',
  templateUrl: './save-course-form.component.html',
  styleUrls: ['./save-course-form.component.scss'],
})
export class SaveCourseFormComponent implements OnInit, OnDestroy {
  createClicked: boolean = false;
  coursesLists: CoursesListWithCourses[] = [];
  favouriteCourses: Course[] = [];
  isCoursePresentInList: boolean[] = [];
  isCoursePresentInFavourites: boolean = false;
  @Input() courseId: number = -1;
  inputValue: string = '';
  currentUser?: User;

  private unsubscribe$ = new Subject();

  constructor(
    private coursesListService: CoursesListService,
    private favouriteCoursesService: FavouriteCoursesService,
    private authService: AuthService,
    private alertService: AlertService,
    @Inject(MAT_DIALOG_DATA) data: any
  ) {
    this.courseId = data.courseId;
  }

  ngOnInit(): void {
    this.authService.user$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((user) => {
        this.currentUser = user;
      });

    this.coursesListService.getCoursesListsWithCourses(this.currentUser!.id!);
    this.coursesListService.coursesListsWithCourses$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((resp) => {
        this.coursesLists = resp;

        this.isCoursePresentInList = new Array(this.coursesLists.length);
        for (let i = 0; i < this.coursesLists.length; i++) {
          let isPresent = false;
          for (let j = 0; j < this.coursesLists[i]!.courses!.length; j++) {
            if (this.coursesLists[i]!.courses![j].id === this.courseId) {
              isPresent = true;
              break;
            }
          }
          this.isCoursePresentInList[i] = isPresent;
        }
      });

    this.favouriteCoursesService.getFavouriteCourses(this.currentUser!.id!);
    this.favouriteCoursesService.courses$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((resp) => {
        this.favouriteCourses = resp;

        this.isCoursePresentInFavourites = false;
        for (let i = 0; i < this.favouriteCourses.length; i++) {
          if (this.favouriteCourses[i].id === this.courseId) {
            this.isCoursePresentInFavourites = true;
            break;
          }
        }
      });
  }

  ngOnDestroy() {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
  }

  addList() {
    this.coursesListService
      .addCourseListWithCourse(
        this.currentUser!.id!,
        this.inputValue,
        this.courseId
      )
      .subscribe({
        next: () => this.alertService.success('Course list added successfully'),
        error: (err) => this.alertService.error(err.error.error),
      });
    this.inputValue = '';
  }

  processCheckList(indexCheckBox: number) {
    if (this.isCoursePresentInList[indexCheckBox] == false) {
      this.coursesListService
        .addCourseToCoursesList(
          this.currentUser!.id!,
          this.coursesLists[indexCheckBox].id!,
          this.courseId
        )
        .subscribe({
          next: () =>
            this.alertService.success('Course added to list successfully'),
          error: (err) => this.alertService.error(err.error.error),
        });
    } else {
      this.coursesListService
        .deleteCourseFromCoursesList(
          this.currentUser!.id!,
          this.coursesLists[indexCheckBox].id!,
          this.courseId
        )
        .subscribe({
          next: () =>
            this.alertService.success('Course removed from list successfully'),
          error: (err) => this.alertService.error(err.error.error),
        });
    }
  }

  processCheckFavourites() {
    if (this.isCoursePresentInFavourites == false) {
      this.favouriteCoursesService
        .addCourseToFavourites(this.currentUser!.id!, this.courseId)
        .subscribe({
          next: () =>
            this.alertService.success(
              'Course added to favourites successfully'
            ),
          error: (err) => this.alertService.error(err.error.error),
        });
    } else {
      this.favouriteCoursesService
        .deleteCourseFromFavourites(this.currentUser!.id!, this.courseId)
        .subscribe({
          next: () =>
            this.alertService.success(
              'Course removed from favourites successfully'
            ),
          error: (err) => this.alertService.error(err.error.error),
        });
    }
  }

  showContent() {
    if (this.createClicked == false) {
      this.createClicked = true;
      let createSection = document.getElementsByClassName(
        'createSection'
      )[0] as HTMLElement | null;
      createSection!.style.display = 'flex';
    } else {
      this.createClicked = false;
      let createSection = document.getElementsByClassName(
        'createSection'
      )[0] as HTMLElement | null;
      createSection!.style.display = 'none';
    }
  }
}
