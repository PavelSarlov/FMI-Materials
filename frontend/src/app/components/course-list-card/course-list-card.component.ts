import { Component, Input, OnInit, Renderer2, ElementRef, ViewChild } from '@angular/core';
import { CoursesListService } from '../../services/courses-list.service';
import { CoursesList } from '../../models/coursesList';

@Component({
  selector: 'app-course-list-card',
  templateUrl: './course-list-card.component.html',
  styleUrls: ['./course-list-card.component.scss']
})
export class CourseListCardComponent implements OnInit {

  @Input() list!: CoursesList;
  @ViewChild('listButton') listButton!: ElementRef;
  isEditClicked: boolean = false;
  inputValue: string = '';
  
  constructor(private coursesListService: CoursesListService, private renderer: Renderer2) {
    
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
