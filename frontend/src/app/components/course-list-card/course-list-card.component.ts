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
    this.renderer.listen('window', 'click',(e:Event)=>{
      if (e.target !== this.listButton.nativeElement) {
        if (this.inputValue != '') {
         this.changeListName();
        }
 
        this.isEditClicked=false;
      }
     });
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

  showInput() {
    if (this.isEditClicked == false) {
      this.inputValue = this.list.listName!;
      this.isEditClicked = true;
    }
  }

  changeListName() {
    let currentUser = JSON.parse(localStorage.getItem('user') ?? '');

    if (currentUser && this.inputValue != '' && this.inputValue != this.list.listName) {
      this.coursesListService.changeCoursesListName(currentUser.id, this.list.id!, this.inputValue);
    }
    else {
      console.log("user undefined")
    }
  }
}
