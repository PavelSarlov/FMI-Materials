import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';
import {BehaviorSubject, Subject, tap} from 'rxjs';
import {environment} from '../../environments/environment';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  user$: Subject<User | null> = new BehaviorSubject<User | null>(null);

  constructor(
    private http: HttpClient,
    private cookies: CookieService,
    private router: Router
  ) {}

  isAuthenticated(): boolean {
    if (localStorage.getItem('user')) {
      this.user$.next(JSON.parse(localStorage.getItem('user')!));
      return true;
    }
    if (this.cookies.get('auth')) {
      let user = window.atob(this.cookies.get('auth')).split(':', 2);
      let status = false;

      this.authenticate(user[0], user[1]).subscribe({
        next: (resp) => {
          status = true;
        },
        error: (err) => console.log(err.error.error),
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
          this.cookies.set('auth', auth, undefined, '/');
          this.user$.next(resp);
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
    }).pipe(tap({
      error: err => console.log(err)
    }));
  }

  logout() {
    localStorage.clear();
    this.cookies.deleteAll('../');
    this.user$.next(null);
    this.router.navigateByUrl('/auth/login');
  }
}
