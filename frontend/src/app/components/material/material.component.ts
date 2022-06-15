import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { Material } from '../../models/material';
import { User, USER_ROLES } from '../../models/user';
import { CourseService } from '../../services/course.service';
import { AuthService } from '../../services/auth.service';
import { AlertService } from '../../services/alert.service';
import { CrossEventService } from '../../services/cross-event.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-material',
  templateUrl: './material.component.html',
  styleUrls: ['./material.component.scss'],
})
export class MaterialComponent implements OnInit, OnDestroy {
  authSubscription?: Subscription;

  user?: User | null;
  USER_ROLES = USER_ROLES;

  @Input()
  material?: Material;

  @Input()
  sectionId?: number;

  @Input()
  fileFormats?: any;

  constructor(
    private courseService: CourseService,
    private authService: AuthService,
    private crossEventService: CrossEventService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.authService.user$.subscribe(
      (user) => (this.user = user)
    );
  }

  ngOnDestroy() {
    this.authSubscription?.unsubscribe();
  }

  openMaterial() {
    this.courseService.getMaterialById(this.material!.id!).subscribe({
      next: (resp: any) => {
        window.open(URL.createObjectURL(resp));
      },
      error: (resp: any) => console.log(resp),
    });
  }

  downloadMaterial(event: any) {
    this.courseService.getMaterialById(this.material!.id!).subscribe({
      next: (resp: any) => {
        const element = document.createElement('a');
        element.download = this.material!.fileName!;
        element.href = URL.createObjectURL(resp);
        element.click();
      },
      error: (resp: any) => console.log(resp),
    });
  }

  deleteMaterial() {
    this.courseService.deleteMaterialById(this.material!.id!).subscribe({
      next: (resp) => {
        this.alertService.success('Material deleted successfully');
        this.crossEventService.materialOnDelete.emit();
      },
      error: (resp) => {
        this.alertService.success(resp.error.error);
      },
    });
  }
}
