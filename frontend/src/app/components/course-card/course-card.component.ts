import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { User } from '../../models/user';
import { Course } from '../../models/course';
import { CoursesListService } from '../../services/courses-list.service';
import { AuthService } from 'src/app/services/auth.service';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router'

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
    private authService: AuthService,
    private activatedRoute: ActivatedRoute) { }

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
    this.coursesListService.deleteCourseFromCoursesList(this.currentUser!.id!, coursesListId, this.course.id!)
  }

  deleteCourseFromFavourites() {
    this.coursesListService.deleteCourseFromFavourites(this.currentUser!.id!, this.course.id!)
  }
}
