import { Component, OnInit, OnDestroy } from '@angular/core';
import { CoursesListService } from '../../services/courses-list.service';
import { FavouriteCoursesService } from '../../services/favourite-courses.service';
import { CoursesListWithCourses } from '../../models/coursesListWithCourses';
import { Course } from '../../models/course';
import { User } from '../../models/user';
import { AuthService } from 'src/app/services/auth.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router'

@Component({
  selector: 'app-save-course-form',
  templateUrl: './save-course-form.component.html',
  styleUrls: ['./save-course-form.component.scss']
})
export class SaveCourseFormComponent implements OnInit, OnDestroy {

  createClicked: boolean = false;
  coursesLists: CoursesListWithCourses[] = [];
  favouriteCourses: Course[] = [];
  isCoursePresentInList: boolean[] = [];
  isCoursePresentInFavourites: boolean = false;
  courseId: number = -1;
  inputValue: string = '';
  currentUser?: User | null;
  authSubscription?: Subscription;
  courseListSubscription?: Subscription;
  favouritesSubscription?: Subscription;

  constructor(private coursesListService: CoursesListService,
    private favouriteCoursesService: FavouriteCoursesService, 
    private authService: AuthService,
    private router: Router) { }

  ngOnInit(): void {
    this.authSubscription = this.authService.user$.subscribe(
      (resp) => {
        this.currentUser = resp;
      }
    );

    this.courseId = parseInt(window.location.href.split('/').pop()!);

    if (this.authService.isAuthenticated()){
      this.coursesListService.getCoursesListsWithCourses(this.currentUser!.id!);
      this.courseListSubscription = this.coursesListService.coursesListsWithCourses$.subscribe(
        (resp) => {
          this.coursesLists = resp;
          console.log(resp);

          this.isCoursePresentInList = new Array(this.coursesLists.length);
          for (let i = 0; i < this.coursesLists.length; i++) {
            let isPresent = false;
            for (let j = 0; j < this.coursesLists[i]!.courses!.length; j++) {
              if (this.coursesLists[i]!.courses![j].id === this.courseId) {
                isPresent = true;
                break;
              }
            }
            this.isCoursePresentInList[i] = isPresent;
          }
        }
      );

      this.favouriteCoursesService.getFavouriteCourses(this.currentUser!.id!);
      this.favouritesSubscription = this.favouriteCoursesService.courses$.subscribe(
        (resp) => {
          this.favouriteCourses = resp;

          this.isCoursePresentInFavourites = false;
          for (let i = 0; i < this.favouriteCourses.length; i++) {
            if (this.favouriteCourses[i].id === this.courseId) {
              this.isCoursePresentInFavourites = true;
              break;
            }
          }
        }
      );
    }
    else {
      this.router.navigateByUrl('/auth/login');
    }
  }

  ngOnDestroy() {
    this.authSubscription?.unsubscribe();
    this.courseListSubscription?.unsubscribe();
    this.favouritesSubscription?.unsubscribe();
  }

  addList() {
    this.coursesListService.addCourseListWithCourse(this.currentUser!.id!, this.inputValue, this.courseId);
    this.inputValue = '';
  }

  processCheckList(indexCheckBox: number) {
    if (this.isCoursePresentInList[indexCheckBox] == false) {
      this.coursesListService.addCourseToCoursesList(this.currentUser!.id!, this.coursesLists[indexCheckBox].id!, this.courseId);
    }
    else {
      this.coursesListService.deleteCourseFromCoursesList(this.currentUser!.id!, this.coursesLists[indexCheckBox].id!, this.courseId);
    }
  }

  processCheckFavourites() {
    if (this.isCoursePresentInFavourites == false) {
      this.favouriteCoursesService.addCourseToFavourites(this.currentUser!.id!, this.courseId);
    }
    else {
      this.favouriteCoursesService.deleteCourseFromFavourites(this.currentUser!.id!, this.courseId);
    }
  }

  showContent() {
    if (this.createClicked == false) {
      this.createClicked = true;
      let createSection = document.getElementsByClassName('createSection')[0] as HTMLElement | null;
      createSection!.style.display = 'flex';
    }
    else {
      this.createClicked = false;
      let createSection = document.getElementsByClassName('createSection')[0] as HTMLElement | null;
      createSection!.style.display = 'none';
    }
  }

}
