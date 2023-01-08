import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { StompService } from 'src/app/services/stomp.service';
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
      });

    this.adminService.getAllMaterialRequests(this.user!.id!).subscribe({
      next: (resp) => (this.materialRequests = resp),
      error: (resp) => this.alertService.error(resp.error.error),
    });

    this.stompService.subscribe(
      `/user/${this.user?.name}/queue/request`,
      () => {
        this.adminService.getAllMaterialRequests(this.user!.id!).subscribe({
          next: (resp) => (this.materialRequests = resp),
          error: (resp) => this.alertService.error(resp.error.error),
        });
      }
    );
  }

  ngOnDestroy() {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
  }
}
