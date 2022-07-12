import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {CoursesList} from '../../models/coursesList';
import {User} from '../../models/user';
import {AlertService} from '../../services/alert.service';
import {AuthService} from '../../services/auth.service';
import {CoursesListService} from '../../services/courses-list.service';

@Component({
  selector: 'app-list-of-courses',
  templateUrl: './list-of-courses.component.html',
  styleUrls: ['./list-of-courses.component.scss']
})
export class ListOfCoursesComponent implements OnInit, OnDestroy {

  coursesLists: CoursesList[] = [];
  value: string = '';
  currentUser?: User | null;
  authSubscription?: Subscription;
  courseListSubscription?: Subscription;

  constructor(private coursesListService: CoursesListService,
    private authService: AuthService,
    private alertService: AlertService,
    private router: Router) {}

  ngOnInit(): void {
    this.authSubscription = this.authService.user$.subscribe(
      (resp) => {
        this.currentUser = resp;
      }
    );
    if (this.authService.isAuthenticated()) {
      this.coursesListService.getCoursesLists(this.currentUser!.id!);
      this.courseListSubscription = this.coursesListService.coursesLists$.subscribe(
        (resp) => {
          this.coursesLists = resp;
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

  addList() {
    this.coursesListService.addCourseList(this.currentUser!.id!, this.value).subscribe({
      next: () => this.alertService.success('Course list created successfully'),
      error: (err) => this.alertService.error(err.error.error)
    });
    this.value = '';
  }
}
