import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, tap, take } from 'rxjs';
import { CoursesListWithCourses } from 'src/app/models/coursesListWithCourses';
import { environment } from '../../environments/environment';
import { Course } from '../models/course';
import { CoursesList } from '../models/coursesList';

@Injectable({
  providedIn: 'root',
})
export class CoursesListService {
  coursesLists$: BehaviorSubject<CoursesList[]> = new BehaviorSubject<
    CoursesList[]
  >([]);
  coursesListsWithCourses$: BehaviorSubject<CoursesListWithCourses[]> =
    new BehaviorSubject<CoursesListWithCourses[]>([]);
  coursesList$: BehaviorSubject<CoursesListWithCourses> =
    new BehaviorSubject<CoursesListWithCourses>({});

  constructor(private http: HttpClient) {}

  public getCoursesLists(userId: number) {
    this.http
      .get<CoursesList[]>(`${environment.usersApi}/${userId}/lists`)
      .pipe(take(1))
      .subscribe((resp) => {
        this.coursesLists$.next(resp);
      });
  }

  public getCoursesListsWithCourses(userId: number) {
    this.http
      .get<CoursesListWithCourses[]>(`${environment.usersApi}/${userId}/lists`)
      .pipe(take(1))
      .subscribe((resp) => {
        this.coursesListsWithCourses$.next(resp);
      });
  }

  public getCoursesListById(userId: number, coursesListId: number) {
    this.http
      .get<CoursesListWithCourses>(
        `${environment.usersApi}/${userId}/lists/${coursesListId}`
      )
      .pipe(take(1))
      .subscribe((resp) => {
        this.coursesList$.next(resp);
      });
  }

  public deleteCourseList(userId: number, coursesListId: number) {
    return this.http
      .delete(`${environment.usersApi}/${userId}/lists/${coursesListId}`)
      .pipe(
        tap({
          next: (resp) => {
            this.getCoursesLists(userId);
          },
          error: (err) => console.log(err),
        }),
        take(1)
      );
  }

  public deleteCourseFromCoursesList(
    userId: number,
    coursesListId: number,
    courseId: number
  ) {
    return this.http
      .delete(
        `${environment.usersApi}/${userId}/lists/${coursesListId}/${courseId}`
      )
      .pipe(
        tap({
          next: (resp) => {
            this.getCoursesListById(userId, coursesListId);
            this.getCoursesListsWithCourses(userId);
          },
          error: (err) => console.log(err),
        }),
        take(1)
      );
  }

  public addCourseList(userId: number, listName: string) {
    let courseList = new CoursesList();
    courseList.listName = listName;

    return this.http
      .post(`${environment.usersApi}/${userId}/lists`, courseList)
      .pipe(
        tap({
          next: (resp) => {
            this.getCoursesLists(userId);
          },
          error: (err) => console.log(err),
        }),
        take(1)
      );
  }

  public addCourseListWithCourse(
    userId: number,
    listName: string,
    courseId: number
  ) {
    let course = new Course();
    course.id = courseId;
    let courseList = new CoursesListWithCourses();
    courseList.listName = listName;
    courseList.courses = new Array();
    courseList.courses?.push(course);

    return this.http
      .post(`${environment.usersApi}/${userId}/lists`, courseList)
      .pipe(
        tap({
          next: (resp) => {
            this.getCoursesListsWithCourses(userId);
          },
          error: (err) => console.log(err),
        }),
        take(1)
      );
  }

  public addCourseToCoursesList(
    userId: number,
    coursesListId: number,
    courseId: number
  ) {
    return this.http
      .post(
        `${environment.usersApi}/${userId}/lists/${coursesListId}/${courseId}`,
        null
      )
      .pipe(
        tap({
          next: (resp) => {
            this.getCoursesListsWithCourses(userId);
          },
          error: (err) => console.log(err),
        }),
        take(1)
      );
  }

  public changeCoursesListName(
    userId: number,
    coursesListId: number,
    listName: string
  ) {
    return this.http
      .put(
        `${environment.usersApi}/${userId}/lists/${coursesListId}?listName=${listName}`,
        undefined
      )
      .pipe(
        tap({
          next: (resp) => {
            this.getCoursesListById(userId, coursesListId);
          },
          error: (err) => console.log(err),
        }),
        take(1)
      );
  }
}
