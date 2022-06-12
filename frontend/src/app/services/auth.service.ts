import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { User } from '../models/user';
import { tap } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient, private cookies: CookieService) {}

  isAuthenticated(): boolean {
    if (localStorage.getItem('user')) {
      return true;
    }
    if (this.cookies.get('auth')) {
      let user = window.atob(this.cookies.get('auth')).split(':', 2);
      let success = false;

      this.authenticate(user[0], user[1]).subscribe({
        next: (resp) => {
          success = true;
        },
        error: (err) => console.log(err.error),
      });
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
        })
      );
  }

  logout() {
    this.cookies.deleteAll();
    localStorage.clear();
  }
}
