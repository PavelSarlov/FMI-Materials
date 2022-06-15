import { HttpClient } from '@angular/common/http';
import { Injectable, EventEmitter } from '@angular/core';
import { tap, BehaviorSubject, Subject, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Course } from '../models/course';
import { Section } from '../models/section';
import { Material } from '../models/material';
import { Pagination } from '../models/pagination';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  pagination$: Subject<Pagination<Course> | null> =
    new BehaviorSubject<Pagination<Course> | null>(null);

  constructor(private http: HttpClient) {}

  getCourses(
    filter?: string,
    filterValue?: any,
    page?: number,
    size?: number,
    sortBy?: string,
    desc?: boolean
  ) {
    this.http
      .get<Pagination<Course>>(
        `${environment.coursesApi}?filter=${filter ?? ''}&filterValue=${
          filterValue ?? ''
        }&page=${page ?? ''}&size=${size ?? ''}&sortBy=${sortBy ?? ''}&desc=${
          desc ?? ''
        }`
      )
      .subscribe({
        next: (resp) => this.pagination$.next(resp),
        error: (err) => console.log(err),
      });
  }

  createCourse(course: Course) {
    return this.http
      .post<Course>(`${environment.coursesApi}`, course)
      .pipe(tap({ error: (resp) => console.log(resp) }));
  }

  updateCourse(course: Course): Observable<Course> {
    return this.http.put<Course>(`${environment.coursesApi}`, course).pipe(
      tap({
        error: (resp) => {
          console.log(resp);
        },
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

  deleteCourseById(courseId: number) {
    return this.http.delete(`${environment.coursesApi}/${courseId}`).pipe(tap({
      error: (resp) => console.log(resp)
    }));
  }

  getCourseSections(courseId: number): Observable<Section[]> {
    return this.http
      .get<Section[]>(`${environment.coursesApi}/${courseId}/sections`)
      .pipe(
        tap({
          error: (err) => console.log(err),
        })
      );
  }

  getSectionById(sectionId: number): Observable<Section> {
    return this.http
      .get(`${environment.coursesApi}/sections/${sectionId}`)
      .pipe(
        tap({
          error: (err) => console.log(err),
        })
      );
  }

  patchSection(section: Section) {
    return this.http.patch<Course>(`${environment.coursesApi}/sections`, section).pipe(
      tap({
        error: (resp) => {
          console.log(resp);
        },
      })
    );
  }

  deleteSectionById(sectionId: number) {
    return this.http.delete(`${environment.coursesApi}/sections/${sectionId}`).pipe(tap({
      error: (resp) => console.log(resp)
    }));
  }

  getMaterialByName(sectionId: number, materialName: string): any {
    return this.http
      .get(
        `${environment.coursesApi}/sections/${sectionId}/materials/${materialName}`,
        { responseType: 'blob' }
      )
      .pipe(
        tap({
          error: (err) => console.log(err),
        })
      );
  }

  getMaterialById(materialId: number): any {
    return this.http
      .get(`${environment.coursesApi}/sections/materials/${materialId}`, {
        responseType: 'blob',
      })
      .pipe(
        tap({
          error: (err) => console.log(err),
        })
      );
  }

  createMaterial(formData: FormData, sectionId: number): any {
    return this.http
      .post<Material>(
        `${environment.coursesApi}/sections/${sectionId}/materials`,
        formData
      )
      .pipe(tap({ error: (err) => console.log(err) }));
  }

  deleteMaterialById(materialId: number) {
    return this.http
      .delete<Material>(
        `${environment.coursesApi}/sections/materials/${materialId}`
      )
      .pipe(tap({ error: (err) => console.log(err) }));
  }
}
