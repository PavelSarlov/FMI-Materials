import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject, tap} from 'rxjs';
import {environment} from '../../environments/environment';
import {Course} from '../models/course';

@Injectable({
  providedIn: 'root'
})
export class FavouriteCoursesService {

  courses$: BehaviorSubject<Course[]> = new BehaviorSubject<Course[]>([]);

  constructor(private http: HttpClient) {}

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

  public addCourseToFavourites(userId: number, courseId: number) {
    return this.http
      .post(`${environment.usersApi}/${userId}/favourite-courses/${courseId}`, null)
      .pipe(tap({
        next: resp => {
          console.log(resp);
          this.getFavouriteCourses(userId);
        },
        error: err => console.log(err)
      }));
  }


  public deleteCourseFromFavourites(userId: number, courseId: number) {
    return this.http
      .delete(`${environment.usersApi}/${userId}/favourite-courses/${courseId}`)
      .pipe(tap({
        next: resp => {
          console.log(resp);
          this.getFavouriteCourses(userId);
        },
        error: err => console.log(err)
      }));
  }
}
