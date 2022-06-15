import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { CoursesListService } from '../../services/courses-list.service';
import { CoursesList } from '../../models/coursesList';
import { User } from '../../models/user';
import { AuthService } from 'src/app/services/auth.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router'

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
    private router: Router) {
    
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
    this.coursesListService.deleteCourseList(this.currentUser!.id!, coursesListId!);
  }
}
