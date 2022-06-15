import { Component, OnInit, OnDestroy } from '@angular/core';
import { CrossEventService } from '../../services/cross-event.service';
import { AuthService } from '../../services/auth.service';
import { User, USER_ROLES } from '../../models/user';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit, OnDestroy {
  authSubscription?: Subscription;
  toggleSidenavSubscription?: Subscription;

  sidenavToggled: boolean = true;
  user?: User | null;
  USER_ROLES = USER_ROLES;

  constructor(
    private crossEventService: CrossEventService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.authService.user$.subscribe(
      (user) => (this.user = user)
    );
    this.authService.isAuthenticated();

    this.toggleSidenavSubscription = this.crossEventService.toggleSidenav.subscribe(
      (status) => (this.sidenavToggled = status)
    );
  }

  ngOnDestroy() {
    this.authSubscription?.unsubscribe();
    this.toggleSidenavSubscription?.unsubscribe();
  }

  toggleSidenav() {
    this.crossEventService.toggleSidenav.emit(!this.sidenavToggled);
  }

  onLogout() {
    this.authService.logout();
  }
}
