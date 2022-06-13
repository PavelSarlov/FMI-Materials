import { Component, OnInit } from '@angular/core';
import { CoursesListService } from '../../services/courses-list.service';
import { CoursesList } from '../../models/coursesList';

@Component({
  selector: 'app-list-of-courses',
  templateUrl: './list-of-courses.component.html',
  styleUrls: ['./list-of-courses.component.scss']
})
export class ListOfCoursesComponent implements OnInit {

  coursesLists: CoursesList[] = [];
  value: string = '';
  constructor(private coursesListService: CoursesListService) { }

  ngOnInit(): void {
    let currentUser = JSON.parse(localStorage.getItem('user') ?? '');
    if(currentUser){
      this.coursesListService.getCoursesLists(currentUser.id);
      this.coursesListService.coursesLists$.subscribe(
        (resp) => {
          this.coursesLists = resp;
        }
      );
    }
    else {
      console.log("user undefined")
    }
  }

  deleteList(coursesListId: number) {
    let currentUser = JSON.parse(localStorage.getItem('user') ?? '');
    if(currentUser){
      this.coursesListService.deleteCourseList(currentUser.id, coursesListId!);
    }
    else {
      console.log("user undefined")
    }
  }

  addList() {
    let currentUser = JSON.parse(localStorage.getItem('user') ?? '');
    if(currentUser && this.value != ''){
      this.coursesListService.addCourseList(currentUser.id, this.value);
      this.value = '';
    }
    else {
      console.log("user undefined")
    }
  }
}
