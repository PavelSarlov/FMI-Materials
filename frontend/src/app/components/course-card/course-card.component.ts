import { Component, Input, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { Course } from '../../models/course';

@Component({
  selector: 'app-course-card',
  templateUrl: './course-card.component.html',
  styleUrls: ['./course-card.component.scss']
})
export class CourseCardComponent implements OnInit {

  @Input() course!: Course;
  user?: User | null = JSON.parse(localStorage.getItem('user')!);
  url: string = window.location.href;

  constructor() { }

  ngOnInit(): void {
  }

  deleteCourse() {
    
  }
}
