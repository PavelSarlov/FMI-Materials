import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {CoursesList} from '../../models/coursesList';
import {User} from '../../models/user';
import {AlertService} from '../../services/alert.service';
import {AuthService} from '../../services/auth.service';
import {CoursesListService} from '../../services/courses-list.service';

@Component({
  selector: 'app-course-list-card',
  templateUrl: './course-list-card.component.html',
  styleUrls: ['./course-list-card.component.scss']
})
export class CourseListCardComponent implements OnInit, OnDestroy {

  @Input() list!: CoursesList;
  currentUser?: User | null;
  authSubscription?: Subscription;

  constructor(private coursesListService: CoursesListService,
    private authService: AuthService,
    private alertService: AlertService) {

  }

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

  deleteList(coursesListId: number) {
    this.coursesListService.deleteCourseList(this.currentUser!.id!, coursesListId!).subscribe({
      next: () => this.alertService.success('Course list deleted successfully'),
      error: (err) => this.alertService.error(err.error.error)
    });
  }
}
