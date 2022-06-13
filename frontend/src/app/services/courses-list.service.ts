import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from '../../environments/environment';
import { CoursesList } from '../models/coursesList';

@Injectable({
  providedIn: 'root'
})
export class CoursesListService {
  coursesLists$: BehaviorSubject<CoursesList[]> = new BehaviorSubject<CoursesList[]>([]);

  constructor(private http: HttpClient) { }

  public getCoursesLists(userId: number) {
    this.http
      .get<CoursesList[]>(
        `${environment.usersApi}/${userId}/lists`
      )
      .subscribe(
        (resp) => {
          this.coursesLists$.next(resp);
        }
      );
  }

  public deleteCourseList(userId: number, coursesListId: number) {
    this.http
      .delete(`${environment.usersApi}/${userId}/lists/${coursesListId}`)
      .subscribe(resp => {
          console.log(resp);
          this.getCoursesLists(userId);
        });
  }

  public addCourseList(userId: number, listName: string) {
    let courseList = new CoursesList();
    courseList.listName = listName;

    this.http
    .post(`${environment.usersApi}/${userId}/lists`, courseList)
    .subscribe(resp => {
      console.log(resp);
      this.getCoursesLists(userId);
    });
  }
}
