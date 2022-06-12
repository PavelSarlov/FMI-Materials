import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { User } from '../models/user';
import { tap, Subject, ReplaySubject } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  userSubject: Subject<User | null> = new ReplaySubject<User | null>(1);
  user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient, private cookies: CookieService) {}

  isAuthenticated(): boolean {
    if (localStorage.getItem('user')) {
      this.userSubject.next(JSON.parse(localStorage.getItem('user')!));
      return true;
    }
    if (this.cookies.get('auth')) {
      let user = window.atob(this.cookies.get('auth')).split(':', 2);
      let status = false;

      this.authenticate(user[0], user[1]).subscribe({
        next: (resp) => {
          status = true;
        },
        error: (err) => console.log(err.error),
      });

      return status;
    }

    return false;
  }

  authenticate(email: string, password: string) {
    return this.http
      .post<User>(`${environment.authApi}/authenticate`, {
        email: email,
        password: password,
      })
      .pipe(
        tap((resp) => {
          let auth = window.btoa(`${email}:${password}`);
          localStorage.setItem('auth', auth);
          localStorage.setItem('user', JSON.stringify(resp));
          this.cookies.set('auth', auth);
          this.userSubject.next(resp);
        })
      );
  }

  register(
    email: string,
    name: string,
    password: string,
    repeatedPassword: string
  ) {
    return this.http.post<User>(`${environment.authApi}/register`, {
      email: email,
      name: name,
      password: password,
      repeatedPassword: repeatedPassword,
    });
  }

  logout() {
    this.cookies.deleteAll();
    localStorage.clear();
    this.userSubject.next(null);
  }
}
