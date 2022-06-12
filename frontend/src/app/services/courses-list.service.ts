import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap, BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { CoursesList } from '../models/coursesList';

@Injectable({
  providedIn: 'root'
})
export class CoursesListService {
  coursesLists$: BehaviorSubject<CoursesList[]> = new BehaviorSubject<CoursesList[]>([]);

  constructor(private http: HttpClient) { }

  public getCoursesLists(userId: number): Observable<CoursesList[]> {
    return this.http
      .get<CoursesList[]>(
        `${environment.usersApi}/${userId}/lists`
      )
      .pipe(
        tap({
          next: (resp) => this.coursesLists$.next(resp),
          error: (err) => console.log(err),
        })
      );
  }
}
