import { User } from '../models/user';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserStorageService {
  getUser(): User | undefined {
    try {
      if (localStorage.getItem('user')) {
        return JSON.parse(window.atob(localStorage.getItem('user')!));
      }
    } catch (e) {}

    return undefined;
  }

  getAuth(): { email: string; password: string } | undefined {
    try {
      if (localStorage.getItem('auth')) {
        return JSON.parse(window.atob(localStorage.getItem('auth')!));
      }
    } catch (e) {}

    return undefined;
  }
}
