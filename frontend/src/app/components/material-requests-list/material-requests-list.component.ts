import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { User, USER_ROLES } from '../../models/user';
import { MaterialRequest } from '../../models/material-request';
import { AuthService } from '../../services/auth.service';
import { AlertService } from '../../services/alert.service';
import { AdminService } from '../../services/admin.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-material-requests-list',
  templateUrl: './material-requests-list.component.html',
  styleUrls: ['./material-requests-list.component.scss'],
})
export class MaterialRequestsListComponent implements OnInit, OnDestroy {
  authSubscription?: Subscription;

  user?: User | null;

  materialRequests: MaterialRequest[] = [];

  constructor(
    private router: Router,
    private authService: AuthService,
    private alertService: AlertService,
    private adminService: AdminService
  ) {}

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.authSubscription = this.authService.user$.subscribe((user) => {
        this.user = user;
      });

      if (!this.user?.roles?.includes(USER_ROLES.ADMIN)) {
        this.alertService.warn(
          'You do not have the necessary permissions to do that.',
          { keepAfterRouteChange: true }
        );
        this.router.navigateByUrl('/courses');
      }

      this.adminService.getAllMaterialRequests(this.user!.id!).subscribe({
        next: (resp) => (this.materialRequests = resp),
        error: (resp) => this.alertService.error(resp.error.error),
      });
    } else {
      this.alertService.warn('You need to login in order to do this.', {
        keepAfterRouteChange: true,
      });
      this.router.navigateByUrl('/auth/login');
    }
  }

  ngOnDestroy() {
    this.authSubscription?.unsubscribe();
  }
}
