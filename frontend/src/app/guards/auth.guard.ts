import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { map, Observable, take } from 'rxjs';
import { AlertService } from '../services/alert.service';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate, CanActivateChild {
  constructor(
    private authService: AuthService,
    private alertService: AlertService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    return this.authService.isAuthenticated().pipe(
      map((authenticated: boolean) => {
        if (authenticated && state.url.includes('auth')) {
          this.alertService.warn('You are already logged in!', {
            keepAfterRouteChange: true,
          });
          return this.router.parseUrl('/');
        }

        if (!authenticated) {
          this.authService.logout();
          if (state.url.includes('auth')) {
            return true;
          } else {
            return this.router.parseUrl('/auth/login');
          }
        }

        if (
          route.data['role'] &&
          !this.authService.isAuthorized(route.data['role'])
        ) {
          this.alertService.warn(
            'You do not have the necessary permission to do that',
            { keepAfterRouteChange: true }
          );
          return this.router.parseUrl('/');
        }

        return authenticated;
      }),
      take(1)
    );
  }

  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree> {
    if (childRoute.data['noLogin']) {
      return true;
    }

    return this.canActivate(childRoute, state);
  }
}
