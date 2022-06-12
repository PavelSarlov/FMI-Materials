import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { AuthInterceptor } from './auth-interceptor';
import { AppComponent } from './app.component';
import { CourseComponent } from './components/course/course.component';
import { CoursesComponent } from './components/courses/courses.component';
import { SidenavComponent } from './components/sidenav/sidenav.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthComponent } from './components/auth/auth.component';
<<<<<<< HEAD
import { MaterialComponent } from './components/material/material.component';
import { SectionComponent } from './components/section/section.component';
import { AlertComponent } from './components/alert/alert.component';
import { CourseCreateFormComponent } from './components/course-create-form/course-create-form.component';
=======
import { ListOfCoursesComponent } from './components/list-of-courses/list-of-courses.component';
>>>>>>> bf6777f (resolved conflict and completed lists enlisting)

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
<<<<<<< HEAD
    MaterialComponent,
    SectionComponent,
    AlertComponent,
    CourseCreateFormComponent,
=======
    ListOfCoursesComponent
>>>>>>> bf6777f (resolved conflict and completed lists enlisting)
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
    CookieService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
