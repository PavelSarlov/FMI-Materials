import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Course } from '../../models/course';
import { User } from '../../models/user';
import { CourseService } from '../../services/course.service';
import { AlertService } from '../../services/alert.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss'],
})
export class CourseComponent implements OnInit {
  user?: User | null = JSON.parse(localStorage.getItem('user')!);

  course?: Course;

  constructor(
    private activatedRoute: ActivatedRoute,
    private courseService: CourseService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
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
}
