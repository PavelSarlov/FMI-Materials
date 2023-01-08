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
import { FavouriteCoursesComponent } from './components/favourite-courses/favourite-courses.component';
import { MaterialRequestsListComponent } from './components/material-requests-list/material-requests-list.component';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  {
    path: '',
    children: [
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
        data: { noLogin: true },
      },
      {
        path: 'courses/:courseId',
        component: CourseComponent,
        data: { noLogin: true },
      },
      {
        path: 'create-course',
        component: CourseCreateFormComponent,
        data: { role: 'ADMIN' },
      },
      {
        path: 'user/course-lists',
        component: ListOfCoursesComponent,
      },
      {
        path: 'user/course-lists/:coursesListId',
        component: CourseListComponent,
      },
      {
        path: 'user/favourite-courses',
        component: FavouriteCoursesComponent,
      },
      {
        path: 'user',
        component: ProfileComponent,
      },
      {
        path: 'material-requests',
        component: MaterialRequestsListComponent,
        data: { role: 'ADMIN' },
      },
      { path: '**', pathMatch: 'full', redirectTo: 'courses' },
    ],
    canActivateChild: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
