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
  constructor(private coursesListService: CoursesListService) { }

  ngOnInit(): void {
    this.getCoursesLists();
  }

  getCoursesLists() {
    let currentUser = JSON.parse(localStorage.getItem('user') ?? '');
    if(currentUser){
      this.coursesListService.getCoursesLists(currentUser.id).subscribe(
        (resp) => {
          console.log(resp);
          this.coursesLists = resp;
        }
      );
    }
    else {
      console.log("user undefined")
    }
  }
}
