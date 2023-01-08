import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from '../services/local-storage.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private userStorageService: UserStorageService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const auth = this.userStorageService.getAuth();

    if (auth) {
      request = request.clone({
        headers: request.headers.set(
          'Authorization',
          `Basic ${btoa(auth.email + ':' + auth.password)}`
        ),
      });
    }

    return next.handle(request);
  }
}
