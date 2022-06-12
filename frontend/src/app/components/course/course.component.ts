import { Component, OnInit, Input } from '@angular/core';
import { Course } from '../../models/course';
import { User } from '../../models/user';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss'],
})
export class CourseComponent implements OnInit {
  user?: User | null = JSON.parse(localStorage.getItem('user')!);

  @Input()
  course!: Course;

  constructor() {}

  ngOnInit(): void {}
}
