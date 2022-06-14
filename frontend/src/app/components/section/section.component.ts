import {
  Component,
  Input,
  OnInit,
  ViewChild,
  ElementRef,
  EventEmitter,
} from '@angular/core';
import { Section } from '../../models/section';
import { User, USER_ROLES } from '../../models/user';
import { AlertService } from '../../services/alert.service';
import { AuthService } from '../../services/auth.service';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-section',
  templateUrl: './section.component.html',
  styleUrls: ['./section.component.scss'],
})
export class SectionComponent implements OnInit {
  user?: User | null;
  USER_ROLES = USER_ROLES;

  @Input()
  section?: Section;

  @ViewChild('material', { static: false })
  material!: ElementRef;

  materialOnDelete: EventEmitter<any> = new EventEmitter<any>();

  @Input()
  sectionOnDelete?: EventEmitter<any>;

  fileFormats: any = {
    'text/plain': 'text_snippet',
    'text/html': 'html',
    'text/css': 'css',
    'application/javascript': 'javascript',
    'application/x-httpd-php': 'php',
    'image/png': 'image',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document':
      'description',
    'application/pdf': 'picture_as_pdf',
    'application/octet-stream': 'insert_drive_file',
    default: 'text_snippet',
  };

  fileToUpload?: File;

  constructor(
    private authService: AuthService,
    private courseService: CourseService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.authService.user$.subscribe((user) => {
      this.user = user;
    });

    this.materialOnDelete.subscribe(() => {
      this.fetchSection();
    });
  }

  onMaterialSelected(event: any) {
    this.fileToUpload = event.currentTarget.files[0];
  }

  fetchSection() {
    this.courseService.getSectionById(this.section!.id!).subscribe({
      next: (resp) => (this.section = resp),
      error: (resp) => this.alertService.error(resp.error.error),
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
        // todo
      }

      this.material.nativeElement.value = null;
      this.fileToUpload = undefined;
    }
  }

  handleSubmitClick(event: any) {
    event.stopPropagation();
  }

  patchSectionName(patchSectionForm: any) {
    let section = new Section();
    section.id = this.section?.id;
    section.name = patchSectionForm.value.name;

    this.courseService.patchSection(section).subscribe({
      next: (resp) =>
        this.alertService.success('Section updated successfully!'),
      error: (resp) => this.alertService.error(resp.error.error),
    });
  }

  deleteSection() {
    this.courseService.deleteSectionById(this.section!.id!).subscribe({
      next: (resp) => {
        this.alertService.success('Section deleted successfully!');
        this.sectionOnDelete?.emit();
      },
      error: (resp) => this.alertService.error(resp.error.error),
    });
  }
}
