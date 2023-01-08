import { Component, OnInit } from '@angular/core';
import { Observable, Subject, takeUntil } from 'rxjs';
import { AdminService } from 'src/app/services/admin.service';
import { AlertService } from 'src/app/services/alert.service';
import { StompService, TopicSub } from 'src/app/services/stomp.service';
import { User, USER_ROLES } from '../../models/user';
import { AuthService } from '../../services/auth.service';
import { CrossEventService } from '../../services/cross-event.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss'],
})
export class SidenavComponent implements OnInit {
  sidenavToggled: boolean = true;
  user?: User;
  USER_ROLES = USER_ROLES;

  requestsCount: number = 0;

  topic: TopicSub | null = null;
  topic$?: Observable<TopicSub | null>;

  private unsubscribe$ = new Subject();

  constructor(
    private crossEventService: CrossEventService,
    private authService: AuthService,
    private stompService: StompService,
    private adminService: AdminService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.authService.user$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((user) => {
        this.user = user;

        if (this.user?.roles?.includes(USER_ROLES.ADMIN)) {
          this.fetchMaterialRequests();

          this.stompService.unsubscribe(this.topic);
          this.topic$ = this.stompService.subscribe(
            `/user/${this.user?.id}/request`,
            () => {
              this.fetchMaterialRequests();
            }
          );
          if (this.topic$) {
            this.topic$
              .pipe(takeUntil(this.unsubscribe$))
              .subscribe((topic) => (this.topic = topic));
          }
        }
      });

    this.crossEventService.toggleSidenav
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((status) => {
        this.sidenavToggled = status;
        this.toggleSidenav();
      });
  }

  ngOnDestroy() {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
    this.stompService.unsubscribe(this.topic);
  }

  fetchMaterialRequests() {
    this.adminService
      .getAllMaterialRequests(this.user!.id!)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (resp) => (this.requestsCount = resp.length),
        error: (resp) => this.alertService.error(resp.error.error),
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
