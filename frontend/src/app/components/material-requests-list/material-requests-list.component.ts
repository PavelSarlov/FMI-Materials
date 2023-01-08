import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil, Observable } from 'rxjs';
import { StompService, TopicSub } from 'src/app/services/stomp.service';
import { MaterialRequest } from '../../models/material-request';
import { User } from '../../models/user';
import { AdminService } from '../../services/admin.service';
import { AlertService } from '../../services/alert.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-material-requests-list',
  templateUrl: './material-requests-list.component.html',
  styleUrls: ['./material-requests-list.component.scss'],
})
export class MaterialRequestsListComponent implements OnInit, OnDestroy {
  user?: User;

  materialRequests: MaterialRequest[] = [];

  topic: TopicSub | null = null;
  topic$?: Observable<TopicSub | null>;

  private unsubscribe$ = new Subject();

  constructor(
    private authService: AuthService,
    private alertService: AlertService,
    private adminService: AdminService,
    private stompService: StompService
  ) {}

  ngOnInit(): void {
    this.authService.user$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((user) => {
        this.user = user;

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
        next: (resp) => (this.materialRequests = resp),
        error: (resp) => this.alertService.error(resp.error.error),
      });
  }
}
