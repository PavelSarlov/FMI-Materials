import {
  Component,
  Input,
  OnInit,
  OnDestroy,
  ViewChild,
  ElementRef,
} from '@angular/core';
import { Subscription } from 'rxjs';
import { Section } from '../../models/section';
import { User, USER_ROLES } from '../../models/user';
import { AlertService } from '../../services/alert.service';
import { AuthService } from '../../services/auth.service';
import { CourseService } from '../../services/course.service';
import { CrossEventService } from '../../services/cross-event.service';
import { UserService } from '../../services/user.service';
import { FILE_FORMATS } from '../../vo/file-formats';

@Component({
  selector: 'app-section',
  templateUrl: './section.component.html',
  styleUrls: ['./section.component.scss'],
})
export class SectionComponent implements OnInit, OnDestroy {
  authSubscription?: Subscription;
  materialEventSubscription?: Subscription;

  user?: User | null;
  USER_ROLES = USER_ROLES;

  FILE_FORMATS = FILE_FORMATS;

  @Input()
  courseId?: number;

  @Input()
  section?: Section;

  @ViewChild('material', { static: false })
  material!: ElementRef;

  fileToUpload?: File;

  constructor(
    private authService: AuthService,
    private courseService: CourseService,
    private crossEventService: CrossEventService,
    private alertService: AlertService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.authService.user$.subscribe((user) => {
      this.user = user;
    });

    this.crossEventService.materialEvent.subscribe((sectionId) => {
      if (sectionId === this.section?.id) {
        this.fetchSection();
      }
    });
  }

  ngOnDestroy() {
    this.authSubscription?.unsubscribe();
    this.materialEventSubscription?.unsubscribe();
  }

  onMaterialSelected(event: any) {
    this.fileToUpload = event.currentTarget.files[0];
  }

  fetchSection() {
    this.courseService.getSectionById(this.section!.id!).subscribe({
      next: (resp) => (this.section = resp),
      error: (resp) => {
        if (typeof resp.error.error == 'object') {
          for (let key of Object.keys(resp.error.error)) {
            for (let err of resp.error.error[key]) {
              this.alertService.error(err);
            }
          }
        } else {
          this.alertService.error(resp.error.error);
        }
      },
    });
  }

  onMaterialUpload() {
    if (this.fileToUpload) {
      const formData = new FormData();
      formData.append('file', this.fileToUpload);

      if (this.user && this.user.roles?.includes(USER_ROLES.ADMIN)) {
        this.courseService
          .createMaterial(formData, this.section!.id!)
          .subscribe({
            next: (resp: any) => {
              this.alertService.success('File uploaded successfully');
              this.fetchSection();
            },
            error: (resp: any) => this.alertService.error(resp.error.error),
          });
      } else {
        this.userService
          .createMaterialRequest(formData, this.section!.id!, this.user!.id!)
          .subscribe({
            next: (resp: any) => {
              this.alertService.success('Request send successfully!');
              this.fetchSection();
            },
            error: (resp: any) => this.alertService.error(resp.error.error),
          });
      }

      this.material.nativeElement.value = null;
      this.fileToUpload = undefined;
    }
  }

  patchSectionName(patchSectionForm: any) {
    if (patchSectionForm.valid) {
      let section = new Section();
      section.id = this.section?.id;
      section.name = patchSectionForm.value.name;

      this.courseService.patchSection(section).subscribe({
        next: (resp) =>
          this.alertService.success('Section updated successfully!'),
        error: (resp) => {
          if (typeof resp.error.error == 'object') {
            for (let key of Object.keys(resp.error.error)) {
              for (let err of resp.error.error[key]) {
                this.alertService.error(err);
              }
            }
          } else {
            this.alertService.error(resp.error.error);
          }
        },
      });
    }
  }

  deleteSection() {
    this.courseService.deleteSectionById(this.section!.id!).subscribe({
      next: (resp) => {
        this.alertService.success('Section deleted successfully!');
        this.crossEventService.sectionEvent.emit(this.courseId);
      },
      error: (resp) => this.alertService.error(resp.error.error),
    });
  }
}
