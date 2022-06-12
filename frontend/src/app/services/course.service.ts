import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap, BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Course } from '../models/course';
import { Section } from '../models/section';
import { Material } from '../models/material';
import { Pagination } from '../models/pagination';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  pagination$: BehaviorSubject<Pagination<Course>> = new BehaviorSubject<
    Pagination<Course>
  >({});

  constructor(private http: HttpClient) {}

  getCourses(
    filter?: string,
    filterValue?: any,
    page?: number,
    size?: number,
    sortBy?: string,
    desc?: boolean
  ): Observable<Pagination<Course>> {
    return this.http
      .get<Pagination<Course>>(
        `${environment.coursesApi}?filter=${filter ?? ''}&filterValue=${
          filterValue ?? ''
        }&page=${page ?? ''}&size=${size ?? ''}&sortBy=${sortBy ?? ''}&desc=${
          desc ?? ''
        }`
      )
      .pipe(
        tap({
          next: (resp) => this.pagination$.next(resp),
          error: (err) => console.log(err),
        })
      );
  }

  getCourseById(courseId?: number): Observable<Course> {
    return this.http.get<Course>(`${environment.coursesApi}/${courseId}`).pipe(
      tap({
        error: (err) => console.log(err),
      })
    );
  }

  getCourseSections(courseId: number): Observable<Section[]> {
    return this.http
      .get<Section[]>(`${environment.coursesApi}/${courseId}/sections`)
      .pipe(
        tap({
          next: (resp) => console.log(resp),
          error: (err) => console.log(err),
        })
      );
  }

  getMaterialByName(
    sectionId: number,
    materialName: string
  ): Observable<Material> {
    return this.http
      .get<Material>(
        `${environment.coursesApi}/courses/sections/${sectionId}/materials/${materialName}`
      )
      .pipe(
        tap({
          error: (err) => console.log(err),
        })
      );
  }
}
