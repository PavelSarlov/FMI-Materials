import { Component, OnInit } from '@angular/core';
import { Course } from '../../models/course';
import { Pagination } from '../../models/pagination';
import { Observable } from 'rxjs';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit {

  // @ts-ignore
  pagination$: Observable<Pagination<Course>>;

  constructor(private courseService: CourseService) {}

  ngOnInit(): void {
    this.pagination$ = this.courseService.getCourses();
  }

}
