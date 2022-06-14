import { Component, OnInit } from '@angular/core';
import { CoursesListService } from '../../services/courses-list.service';
import { CoursesList } from 'src/app/models/coursesList';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.scss']
})
export class CourseListComponent implements OnInit {

  coursesList: CoursesList = new CoursesList();
  isEditClicked: boolean = false;
  inputValue: string = '';

  constructor(private coursesListService: CoursesListService) { }

  ngOnInit(): void {
    let currentUser = JSON.parse(localStorage.getItem('user') ?? '');
    let url = window.location.href;
    let courseListId = parseInt(url.split('/').pop()!);
    if (currentUser){
      this.coursesListService.getCoursesListById(currentUser.id, courseListId);
      this.coursesListService.coursesList$.subscribe(
        (resp) => {
          this.coursesList = resp;
        }
      );
    }
    else {
      console.log("user undefined")
    }
  }

  showInput() {
    if (this.isEditClicked == false) {
      this.isEditClicked = true;
    }
  }

  closeInputDeny() {
    if (this.isEditClicked == true) {
      this.inputValue = ''
      this.isEditClicked = false;
    }
  }

  closeInputSubmit() {
    if (this.isEditClicked == true) {
      this.changeListName();
      this.inputValue = '';
      this.isEditClicked = false;
    }
  }

  changeListName() {
    let currentUser = JSON.parse(localStorage.getItem('user') ?? '');

    if (currentUser && this.inputValue != '' && this.inputValue != this.coursesList.listName) {
      this.coursesListService.changeCoursesListName(currentUser.id, this.coursesList.id!, this.inputValue);
      this.coursesListService.getCoursesListById(currentUser.id, this.coursesList.id!);
    }
    else {
      console.log("user undefined")
    }
  }
}
