import { Component, OnInit } from '@angular/core';
import { CrossEventService } from '../../services/cross-event.service';
import { AuthService } from '../../services/auth.service';
import { User, USER_ROLES } from '../../models/user';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  sidenavToggled: boolean = true;
  user?: User | null;
  USER_ROLES = USER_ROLES

  constructor(
    private crossEventService: CrossEventService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.authService.user$.subscribe((user) => {
      this.user = user;
    });
    this.authService.isAuthenticated();

    this.crossEventService.toggleSidenav.subscribe(
      (status) => (this.sidenavToggled = status)
    );
  }

  toggleSidenav() {
    this.crossEventService.toggleSidenav.emit(!this.sidenavToggled);
  }

  onLogout() {
    this.authService.logout();
  }
}
