import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { CoursesComponent } from './components/courses/courses.component';
import { CourseComponent } from './components/course/course.component';
import { AuthComponent } from './components/auth/auth.component';
import { CourseCreateFormComponent } from './components/course-create-form/course-create-form.component';
import { ListOfCoursesComponent } from './components/list-of-courses/list-of-courses.component';
import { CourseListComponent } from './components/course-list/course-list.component';
import { ProfileComponent } from './components/profile/profile.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'courses' },
  {
    path: 'auth',
    component: AuthComponent,
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
    ],
  },
  {
    path: 'courses',
    component: CoursesComponent,
  },
  { path: 'courses/:courseId', component: CourseComponent },
  { path: 'create-course', component: CourseCreateFormComponent },
  {
    path: 'user/course-lists',
    component: ListOfCoursesComponent
  },
  {
    path: 'user/course-lists/:coursesListId',
    component: CourseListComponent
  },
  {
    path: 'user',
    component: ProfileComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
