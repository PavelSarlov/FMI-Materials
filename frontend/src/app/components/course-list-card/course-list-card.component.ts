import { Component, Input, OnInit } from '@angular/core';
import { CoursesListService } from '../../services/courses-list.service';
import { CoursesList } from '../../models/coursesList';

@Component({
  selector: 'app-course-list-card',
  templateUrl: './course-list-card.component.html',
  styleUrls: ['./course-list-card.component.scss']
})
export class CourseListCardComponent implements OnInit {

  @Input() list!: CoursesList;
  
  constructor(private coursesListService: CoursesListService) {
    
  }

  ngOnInit(): void {  
  }

  deleteList(coursesListId: number) {
    let currentUser = JSON.parse(localStorage.getItem('user') ?? '');
    if (currentUser){
      this.coursesListService.deleteCourseList(currentUser.id, coursesListId!);
    }
    else {
      console.log("user undefined")
    }
  }
}
