import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {AuthService} from 'src/app/services/auth.service';
import {CoursesList} from '../../models/coursesList';
import {User} from '../../models/user';
import {CoursesListService} from '../../services/courses-list.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit, OnDestroy {

  coursesLists: CoursesList[] = [];
  currentUser?: User | null;
  authSubscription?: Subscription;
  courseListSubscription?: Subscription;

  constructor(private coursesListService: CoursesListService,
    private authService: AuthService,
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
}
