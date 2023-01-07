import {Component, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import { AdminService } from 'src/app/services/admin.service';
import { AlertService } from 'src/app/services/alert.service';
import { StompService } from 'src/app/services/stomp.service';
import {User, USER_ROLES} from '../../models/user';
import {AuthService} from '../../services/auth.service';
import {CrossEventService} from '../../services/cross-event.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss'],
})
export class SidenavComponent implements OnInit {
  authSubscription?: Subscription;
  toggleSidenavSubscription?: Subscription;

  sidenavToggled: boolean = true;
  user?: User | null;
  USER_ROLES = USER_ROLES;

  requestsCount: number = 0

  constructor(
    private crossEventService: CrossEventService,
    private authService: AuthService,
    private stompService: StompService,
    private adminService: AdminService,
    private alertService: AlertService,
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.authService.user$.subscribe(
      (user) => (this.user = user)
    );
    this.authService.isAuthenticated();

    this.toggleSidenav();
    this.toggleSidenavSubscription =
      this.crossEventService.toggleSidenav.subscribe((status) => {
        this.sidenavToggled = status;
        this.toggleSidenav();
      });

    if (this.user?.roles?.includes(USER_ROLES.ADMIN)) {
      this.adminService.getAllMaterialRequests(this.user!.id!).subscribe({
        next: (resp) => (this.requestsCount = resp.length),
        error: (resp) => this.alertService.error(resp.error.error),
      });

      this.stompService.subscribe(`/user/${this.user?.name}/queue/request`, (): void => {
        this.adminService.getAllMaterialRequests(this.user!.id!).subscribe({
          next: (resp) => (this.requestsCount = resp.length),
          error: (resp) => this.alertService.error(resp.error.error),
        });
      });
    }
  }

  ngOnDestroy() {
    this.authSubscription?.unsubscribe();
    this.toggleSidenavSubscription?.unsubscribe();
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
