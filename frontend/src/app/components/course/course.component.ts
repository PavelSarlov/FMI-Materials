import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Course } from '../../models/course';
import { User, USER_ROLES } from '../../models/user';
import { CourseService } from '../../services/course.service';
import { AlertService } from '../../services/alert.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss'],
})
export class CourseComponent implements OnInit {
  user?: User | null;
  USER_ROLES = USER_ROLES;

  course?: Course;

  constructor(
    private activatedRoute: ActivatedRoute,
    private courseService: CourseService,
    private alertService: AlertService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.authService.user$.subscribe((user) => {
      this.user = user;
    });

    this.activatedRoute.paramMap.subscribe((params) => {
      this.courseService
        .getCourseById(parseInt(params.get('courseId') ?? ''))
        .subscribe({
          next: (course) => {
            this.course = course;
          },
          error: (resp) => {
            this.alertService.error(resp.error.error);
          },
        });
    });
  }

  createSection() {}
}
