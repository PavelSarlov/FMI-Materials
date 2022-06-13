import { Component, OnInit } from '@angular/core';
import { CrossEventService } from '../../services/cross-event.service';
import { AuthService } from '../../services/auth.service';
import { User, USER_ROLES } from '../../models/user';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss'],
})
export class SidenavComponent implements OnInit {
  sidenavToggled: boolean = true;
  user?: User | null;
  USER_ROLES = USER_ROLES;

  constructor(
    private crossEventService: CrossEventService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.authService.user$.subscribe((user) => {
      this.user = user;
    });
    this.authService.isAuthenticated();

    this.toggleSidenav();
    this.crossEventService.toggleSidenav.subscribe((status) => {
      this.sidenavToggled = status;
      this.toggleSidenav();
    });
  }

  toggleSidenav() {
    if (this.sidenavToggled) {
      document
        .getElementsByTagName('app-sidenav')[0]
        .setAttribute(
          'style',
          'width: var(--app-sidenav-width); pointer-events: all;'
        );
      document
        .getElementsByTagName('main')[0]
        .setAttribute('style', 'margin-left: var(--app-sidenav-width);');
    } else {
      document
        .getElementsByTagName('app-sidenav')[0]
        .setAttribute('style', 'width: 0; pointer-events: none;');
      document
        .getElementsByTagName('main')[0]
        .setAttribute('style', 'margin-left: 0;');
    }
  }
}
