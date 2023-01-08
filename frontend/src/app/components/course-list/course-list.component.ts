import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { CoursesListWithCourses } from 'src/app/models/coursesListWithCourses';
import { AuthService } from 'src/app/services/auth.service';
import { User } from '../../models/user';
import { AlertService } from '../../services/alert.service';
import { CoursesListService } from '../../services/courses-list.service';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.scss'],
})
export class CourseListComponent implements OnInit, OnDestroy {
  coursesList: CoursesListWithCourses = new CoursesListWithCourses();
  isEditClicked: boolean = false;
  inputValue: string = '';
  currentUser?: User;

  private unsubscribe$ = new Subject();

  constructor(
    private coursesListService: CoursesListService,
    private authService: AuthService,
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.authService.user$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((user) => {
        this.currentUser = user;
      });

    let courseListId = parseInt(
      this.activatedRoute.snapshot.paramMap.get('coursesListId')!
    );

    this.coursesListService.getCoursesListById(
      this.currentUser!.id!,
      courseListId
    );
    this.coursesListService.coursesList$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((resp) => {
        this.coursesList = resp;
      });
  }

  ngOnDestroy() {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
  }

  showInput() {
    if (this.isEditClicked == false) {
      this.isEditClicked = true;
    }
  }

  closeInputDeny() {
    if (this.isEditClicked == true) {
      this.inputValue = '';
      this.isEditClicked = false;
    }
  }

  closeInputSubmit() {
    if (this.isEditClicked == true) {
      this.changeListName();
      this.inputValue = '';
      this.isEditClicked = false;
    }
  }

  changeListName() {
    this.coursesListService
      .changeCoursesListName(
        this.currentUser!.id!,
        this.coursesList.id!,
        this.inputValue
      )
      .subscribe({
        next: () =>
          this.alertService.success('Course list renamed successfully'),
        error: (err) => this.alertService.error(err.error.error),
      });
  }
}
