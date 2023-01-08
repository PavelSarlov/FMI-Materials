import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, take, tap, ReplaySubject } from 'rxjs';
import { environment } from '../../environments/environment';
import { User } from '../models/user';
import { UserStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  user$ = new BehaviorSubject<User | undefined>(undefined);

  constructor(
    private http: HttpClient,
    private userStorageService: UserStorageService
  ) {}

  isAuthenticated() {
    const authenticated$ = new ReplaySubject<boolean>(1);
    const auth = this.userStorageService.getAuth();
    const user = this.userStorageService.getUser();

    if (auth) {
      this.http
        .get(`${environment.authApi}/is-authenticated`)
        .pipe(take(1))
        .subscribe({
          next: (_) => authenticated$.next(true),
          error: (_) => authenticated$.next(false),
        });
      this.user$.next(user);
    } else {
      authenticated$.next(false);
    }

    return authenticated$.asObservable();
  }

  authenticate(email: string, password: string) {
    const payload = {
      email: email,
      password: password,
    };

    return this.http
      .post<User>(`${environment.authApi}/authenticate`, payload)
      .pipe(
        tap({
          next: (resp) => {
            const user = window.btoa(JSON.stringify(resp));
            const auth = window.btoa(JSON.stringify(payload));
            localStorage.setItem('user', user);
            localStorage.setItem('auth', auth);
            this.user$.next(resp);
          },
          error: (_) => {
            this.logout();
          },
        }),
        take(1)
      );
  }

  register(
    email: string,
    name: string,
    password: string,
    repeatedPassword: string
  ) {
    return this.http
      .post<User>(`${environment.authApi}/register`, {
        email: email,
        name: name,
        password: password,
        repeatedPassword: repeatedPassword,
      })
      .pipe(
        tap({
          error: (err) => console.log(err),
        }),
        take(1)
      );
  }

  logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('auth');
    this.user$.next(undefined);
  }

  isAuthorized(role: string) {
    const user = this.userStorageService.getUser();

    if (!user || !user.roles!.includes(role)) {
      return false;
    }

    return true;
  }
}
