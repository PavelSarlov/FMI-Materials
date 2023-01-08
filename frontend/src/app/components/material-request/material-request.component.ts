import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { takeUntil, Subject } from 'rxjs';
import { MaterialRequest } from '../../models/material-request';
import { User, USER_ROLES } from '../../models/user';
import { AdminService } from '../../services/admin.service';
import { AlertService } from '../../services/alert.service';
import { AuthService } from '../../services/auth.service';
import { CrossEventService } from '../../services/cross-event.service';
import { FILE_FORMATS } from '../../vo/file-formats';

@Component({
  selector: 'app-material-request',
  templateUrl: './material-request.component.html',
  styleUrls: ['./material-request.component.scss'],
})
export class MaterialRequestComponent implements OnInit, OnDestroy {
  user?: User;
  USER_ROLES = USER_ROLES;

  FILE_FORMATS = FILE_FORMATS;

  @Input()
  materialRequest?: MaterialRequest;

  @Input()
  sectionId?: number;

  private unsubscribe$ = new Subject();

  constructor(
    private authService: AuthService,
    private crossEventService: CrossEventService,
    private alertService: AlertService,
    private adminService: AdminService
  ) {}

  ngOnInit(): void {
    this.authService.user$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((user) => {
        this.user = user;
      });
  }

  ngOnDestroy() {
    this.unsubscribe$.next(true);
    this.unsubscribe$.complete();
  }

  openMaterial() {
    this.adminService
      .getMaterialFromMaterialRequest(this.user!.id!, this.materialRequest!.id!)
      .subscribe({
        next: (resp: any) => {
          window.open(URL.createObjectURL(resp));
        },
        error: (resp: any) => this.alertService.error(resp.error.error),
      });
  }

  downloadMaterial() {
    this.adminService
      .getMaterialFromMaterialRequest(this.user!.id!, this.materialRequest!.id!)
      .subscribe({
        next: (resp: any) => {
          const element = document.createElement('a');
          element.download = this.materialRequest!.fileName!;
          element.href = URL.createObjectURL(resp);
          element.click();
        },
        error: (resp: any) => this.alertService.error(resp.error.error),
      });
  }

  processMaterialRequest(status: boolean) {
    this.adminService
      .processMaterialRequest(this.user!.id!, this.materialRequest!.id!, status)
      .subscribe({
        next: () => {
          this.alertService.success('Material processed successfully!');
          this.crossEventService.materialEvent.emit(this.sectionId!);
        },
        error: (resp) => {
          this.alertService.success(resp.error.error);
        },
      });
  }

  fileFormatSupported(format: string | undefined) {
    return format === undefined ? false : format in this.FILE_FORMATS;
  }
}
