import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { User, USER_ROLES } from '../../models/user';
import { AuthService } from '../../services/auth.service';
import { CrossEventService } from '../../services/cross-event.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit, OnDestroy {
  sidenavToggled: boolean = true;
  user?: User;
  USER_ROLES = USER_ROLES;

  private unsubscribe$ = new Subject();

  constructor(
    private crossEventService: CrossEventService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authService.user$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((user) => (this.user = user));

    this.crossEventService.toggleSidenav
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((status) => (this.sidenavToggled = status));
  }

  ngOnDestroy() {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
  }

  toggleSidenav() {
    this.crossEventService.toggleSidenav.emit(!this.sidenavToggled);
  }

  onLogout() {
    this.authService.logout();
    this.router.navigateByUrl('/auth/login');
  }
}
