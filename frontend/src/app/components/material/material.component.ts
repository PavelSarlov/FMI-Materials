import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { Material } from '../../models/material';
import { User, USER_ROLES } from '../../models/user';
import { AlertService } from '../../services/alert.service';
import { AuthService } from '../../services/auth.service';
import { CourseService } from '../../services/course.service';
import { CrossEventService } from '../../services/cross-event.service';
import { FILE_FORMATS } from '../../vo/file-formats';

@Component({
  selector: 'app-material',
  templateUrl: './material.component.html',
  styleUrls: ['./material.component.scss'],
})
export class MaterialComponent implements OnInit, OnDestroy {
  user?: User;
  USER_ROLES = USER_ROLES;

  FILE_FORMATS = FILE_FORMATS;

  @Input()
  material?: Material;

  @Input()
  sectionId?: number;

  private unsubscribe$ = new Subject();

  constructor(
    private courseService: CourseService,
    private authService: AuthService,
    private crossEventService: CrossEventService,
    private alertService: AlertService
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
    this.courseService.getMaterialById(this.material!.id!).subscribe({
      next: (resp: any) => {
        window.open(URL.createObjectURL(resp));
      },
      error: (resp: any) => this.alertService.error(resp.error.error),
    });
  }

  downloadMaterial() {
    this.courseService.getMaterialById(this.material!.id!).subscribe({
      next: (resp: any) => {
        const element = document.createElement('a');
        element.download = this.material!.fileName!;
        element.href = URL.createObjectURL(resp);
        element.click();
      },
      error: (resp: any) => this.alertService.error(resp.error.error),
    });
  }

  deleteMaterial() {
    this.courseService.deleteMaterialById(this.material!.id!).subscribe({
      next: () => {
        this.alertService.success('Material deleted successfully');
        this.crossEventService.materialEvent.emit(this.sectionId);
      },
      error: (resp) => {
        this.alertService.success(resp.error.error);
      },
    });
  }
}
