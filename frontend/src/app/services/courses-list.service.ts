import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from '../../environments/environment';
import { CoursesList } from '../models/coursesList';
import { Course } from '../models/course';
import { CoursesListWithCourses } from 'src/app/models/coursesListWithCourses';

@Injectable({
  providedIn: 'root'
})
export class CoursesListService {
  coursesLists$: BehaviorSubject<CoursesList[]> = new BehaviorSubject<CoursesList[]>([]);
  coursesListsWithCourses$: BehaviorSubject<CoursesListWithCourses[]> = new BehaviorSubject<CoursesListWithCourses[]>([]);
  coursesList$: BehaviorSubject<CoursesListWithCourses> = new BehaviorSubject<CoursesListWithCourses>({});

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

  public getCoursesListsWithCourses(userId: number) {
    this.http
      .get<CoursesListWithCourses[]>(
        `${environment.usersApi}/${userId}/lists`
      )
      .subscribe(
        (resp) => {
          this.coursesListsWithCourses$.next(resp);
        }
      );
  }

  public getCoursesListById(userId: number, coursesListId: number) {
    this.http
      .get<CoursesListWithCourses>(
        `${environment.usersApi}/${userId}/lists/${coursesListId}`
      )
      .subscribe(
        (resp) => {
          this.coursesList$.next(resp);
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

  public deleteCourseFromCoursesList(userId: number, coursesListId: number, courseId: number) {
    this.http
      .delete(`${environment.usersApi}/${userId}/lists/${coursesListId}/${courseId}`)
      .subscribe(resp => {
          console.log(resp);
          this.getCoursesListById(userId, coursesListId);
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

  public addCourseToCoursesList(userId: number, coursesListId: number, courseId: number) {
    this.http
    .post(`${environment.usersApi}/${userId}/lists/${coursesListId}/${courseId}`, null)
    .subscribe(resp => {
      console.log(resp);
    });
  }

  public changeCoursesListName(userId: number, coursesListId: number, listName: string) {
    this.http
    .put(`${environment.usersApi}/${userId}/lists/${coursesListId}?listName=${listName}`, undefined)
    .subscribe(resp => {
      console.log(resp);
      this.getCoursesLists(userId);
    });
  }
}
