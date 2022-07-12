import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {CoursesListWithCourses} from 'src/app/models/coursesListWithCourses';
import {AuthService} from 'src/app/services/auth.service';
import {User} from '../../models/user';
import {AlertService} from '../../services/alert.service';
import {CoursesListService} from '../../services/courses-list.service';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.scss']
})
export class CourseListComponent implements OnInit, OnDestroy {

  coursesList: CoursesListWithCourses = new CoursesListWithCourses();
  isEditClicked: boolean = false;
  inputValue: string = '';
  currentUser?: User | null;
  authSubscription?: Subscription;
  courseListSubscription?: Subscription;

  constructor(private coursesListService: CoursesListService,
    private authService: AuthService,
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService,
    private router: Router) {}

  ngOnInit(): void {
    this.authSubscription = this.authService.user$.subscribe(
      (resp) => {
        this.currentUser = resp;
      }
    );

    let courseListId = parseInt(this.activatedRoute.snapshot.paramMap.get('coursesListId')!);

    if (this.authService.isAuthenticated()) {
      this.coursesListService.getCoursesListById(this.currentUser!.id!, courseListId);
      this.courseListSubscription = this.coursesListService.coursesList$.subscribe(
        (resp) => {
          this.coursesList = resp;
        }
      );
    }
    else {
      this.router.navigateByUrl('/auth/login');
    }
  }

  ngOnDestroy() {
    this.authSubscription?.unsubscribe();
    this.courseListSubscription?.unsubscribe();
  }

  showInput() {
    if (this.isEditClicked == false) {
      this.isEditClicked = true;
    }
  }

  closeInputDeny() {
    if (this.isEditClicked == true) {
      this.inputValue = ''
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
    this.coursesListService.changeCoursesListName(this.currentUser!.id!, this.coursesList.id!, this.inputValue).subscribe({
      next: () => this.alertService.success('Course list renamed successfully'),
      error: (err) => this.alertService.error(err.error.error)
    });
  }
}
