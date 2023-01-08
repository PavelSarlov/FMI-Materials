import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject, tap, take } from 'rxjs';
import { environment } from '../../environments/environment';
import { Course } from '../models/course';
import { Material } from '../models/material';
import { Pagination } from '../models/pagination';
import { ResponseDto } from '../models/response';
import { Section } from '../models/section';

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
      .pipe(take(1))
      .subscribe({
        next: (resp) => this.pagination$.next(resp),
        error: (err) => console.log(err),
      });
  }

  createCourse(course: Course): Observable<Course> {
    return this.http
      .post<Course>(`${environment.coursesApi}`, course)
      .pipe(tap({ error: (resp) => console.log(resp) }), take(1));
  }

  updateCourse(course: Course): Observable<Course> {
    return this.http.put<Course>(`${environment.coursesApi}`, course).pipe(
      tap({
        error: (resp) => {
          console.log(resp);
        },
      }),
      take(1)
    );
  }

  getCourseById(courseId?: number): Observable<Course> {
    return this.http.get<Course>(`${environment.coursesApi}/${courseId}`).pipe(
      tap({
        error: (err) => console.log(err),
      }),
      take(1)
    );
  }

  deleteCourseById(courseId: number): Observable<ResponseDto> {
    return this.http
      .delete<ResponseDto>(`${environment.coursesApi}/${courseId}`)
      .pipe(
        tap({
          error: (resp) => console.log(resp),
        }),
        take(1)
      );
  }

  getCourseSections(courseId: number): Observable<Section[]> {
    return this.http
      .get<Section[]>(`${environment.coursesApi}/${courseId}/sections`)
      .pipe(
        tap({
          error: (err) => console.log(err),
        }),
        take(1)
      );
  }

  getSectionById(sectionId: number): Observable<Section> {
    return this.http
      .get(`${environment.coursesApi}/sections/${sectionId}`)
      .pipe(
        tap({
          error: (err) => console.log(err),
        }),
        take(1)
      );
  }

  createSection(section: Section, courseId: number): Observable<Section> {
    return this.http
      .post<Section>(`${environment.coursesApi}/${courseId}/sections`, section)
      .pipe(
        tap({
          error: (err) => console.log(err),
        }),
        take(1)
      );
  }

  patchSection(section: Section): Observable<Section> {
    return this.http
      .patch<Section>(`${environment.coursesApi}/sections`, section)
      .pipe(
        tap({
          error: (resp) => console.log(resp),
        }),
        take(1)
      );
  }

  deleteSectionById(sectionId: number): Observable<ResponseDto> {
    return this.http
      .delete<ResponseDto>(`${environment.coursesApi}/sections/${sectionId}`)
      .pipe(
        tap({
          error: (resp) => console.log(resp),
        }),
        take(1)
      );
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
        }),
        take(1)
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
        }),
        take(1)
      );
  }

  createMaterial(formData: FormData, sectionId: number): any {
    return this.http
      .post<Material>(
        `${environment.coursesApi}/sections/${sectionId}/materials`,
        formData
      )
      .pipe(
        tap({
          error: (err) => console.log(err),
        }),
        take(1)
      );
  }

  deleteMaterialById(materialId: number): Observable<Material> {
    return this.http
      .delete<Material>(
        `${environment.coursesApi}/sections/materials/${materialId}`
      )
      .pipe(
        tap({
          error: (err) => console.log(err),
        }),
        take(1)
      );
  }
}
