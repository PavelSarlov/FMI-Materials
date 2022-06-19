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
export class FavouriteCoursesService {

  courses$: BehaviorSubject<Course[]> = new BehaviorSubject<Course[]>([]);

  constructor(private http: HttpClient) { }

  public getFavouriteCourses(userId: number) {
    this.http
      .get<Course[]>(
        `${environment.usersApi}/${userId}/favourite-courses`
      )
      .subscribe(
        (resp) => {
          this.courses$.next(resp);
        }
      );
  }

  public deleteCourseFromFavourites(userId: number, courseId: number) {
    this.http
      .delete(`${environment.usersApi}/${userId}/favourite-courses/${courseId}`)
      .subscribe(resp => {
          console.log(resp);
          this.getFavouriteCourses(userId);
        });
  }
}
