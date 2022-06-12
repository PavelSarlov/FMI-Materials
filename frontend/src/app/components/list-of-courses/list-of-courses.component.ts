import { Component, OnInit } from '@angular/core';
import { CoursesListService } from '../../services/courses-list.service';
import { CoursesList } from '../../models/coursesList';

@Component({
  selector: 'app-list-of-courses',
  templateUrl: './list-of-courses.component.html',
  styleUrls: ['./list-of-courses.component.scss']
})
export class ListOfCoursesComponent implements OnInit {
  coursesLists?: CoursesList[];
  constructor(private coursesListService: CoursesListService) { }

  ngOnInit(): void {
    this.coursesListService.getCoursesLists(userId: number)
  }

}
