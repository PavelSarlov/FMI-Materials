import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FacultyDepartment } from '../models/faculty-department';
import { environment } from '../../environments/environment';
import { Observable, tap, take } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FacultyDepartmentService {
  constructor(private http: HttpClient) {}

  getAllFacultyDepartments(): Observable<FacultyDepartment[]> {
    return this.http
      .get<FacultyDepartment[]>(`${environment.departmentsApi}`)
      .pipe(
        tap({
          error: (resp) => console.log(resp),
        }),
        take(1)
      );
  }
}
