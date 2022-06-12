import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Course } from '../../models/course';
import { User } from '../../models/user';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss'],
})
export class CourseComponent implements OnInit {
  user?: User | null = JSON.parse(localStorage.getItem('user')!);

  course?: Course;

  fileFormats: any = {
    'text/plain': 'text_snippet',
    'text/html': 'html',
    'text/css': 'css',
    'application/javascript': 'javascript',
    'application/x-httpd-php': 'php',
    'image/png': 'image',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document': 'description',
    'application/pdf': 'picture_as_pdf',
    'default': 'text_snippet'
  }

  constructor(
    private activatedRoute: ActivatedRoute,
    private courseService: CourseService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.courseService.getCourseById(parseInt(params.get('courseId') ?? '')).subscribe(course => {
        this.course = course;
      });
    })
  }
}
