import { Component, OnInit, OnDestroy } from '@angular/core';
import { FavouriteCoursesService } from '../../services/favourite-courses.service';
import { Course } from 'src/app/models/course';
import { User } from '../../models/user';
import { AuthService } from 'src/app/services/auth.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router'


@Component({
  selector: 'app-favourite-courses',
  templateUrl: './favourite-courses.component.html',
  styleUrls: ['./favourite-courses.component.scss']
})
export class FavouriteCoursesComponent implements OnInit, OnDestroy {

  courses: Course[] = [];
  currentUser?: User | null;
  authSubscription?: Subscription;
  courseListSubscription?: Subscription;

  constructor(private favouriteCoursesService: FavouriteCoursesService, 
    private authService: AuthService,
    private router: Router) { }

  ngOnInit(): void {
    this.authSubscription = this.authService.user$.subscribe(
      (resp) => {
        this.currentUser = resp;
      }
    );

    if (this.authService.isAuthenticated()){
      this.favouriteCoursesService.getFavouriteCourses(this.currentUser!.id!);
      this.courseListSubscription = this.favouriteCoursesService.courses$.subscribe(
        (resp) => {
          this.courses = resp;
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