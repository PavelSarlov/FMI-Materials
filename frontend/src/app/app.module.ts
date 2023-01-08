import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { AuthInterceptor } from './interceptors/auth-interceptor';
import { AppComponent } from './app.component';
import { CourseComponent } from './components/course/course.component';
import { CoursesComponent } from './components/courses/courses.component';
import { SidenavComponent } from './components/sidenav/sidenav.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthComponent } from './components/auth/auth.component';
import { MaterialComponent } from './components/material/material.component';
import { SectionComponent } from './components/section/section.component';
import { AlertComponent } from './components/alert/alert.component';
import { CourseCreateFormComponent } from './components/course-create-form/course-create-form.component';
import { ListOfCoursesComponent } from './components/list-of-courses/list-of-courses.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CourseListCardComponent } from './components/course-list-card/course-list-card.component';
import { CourseListComponent } from './components/course-list/course-list.component';
import { CourseCardComponent } from './components/course-card/course-card.component';
import { FavouriteCoursesComponent } from './components/favourite-courses/favourite-courses.component';
import { MaterialRequestComponent } from './components/material-request/material-request.component';
import { BlobErrorInterceptor } from './interceptors/blob-error-interceptor';
import { MaterialRequestsListComponent } from './components/material-requests-list/material-requests-list.component';
import { SaveCourseFormComponent } from './components/save-course-form/save-course-form.component';

@NgModule({
  declarations: [
    AppComponent,
    CourseComponent,
    CoursesComponent,
    SidenavComponent,
    HeaderComponent,
    LoginComponent,
    RegisterComponent,
    AuthComponent,
    MaterialComponent,
    SectionComponent,
    AlertComponent,
    CourseCreateFormComponent,
    ListOfCoursesComponent,
    ProfileComponent,
    CourseListCardComponent,
    CourseListComponent,
    CourseCardComponent,
    FavouriteCoursesComponent,
    MaterialRequestComponent,
    MaterialRequestsListComponent,
    SaveCourseFormComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: BlobErrorInterceptor,
      multi: true,
    },
    CookieService
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
