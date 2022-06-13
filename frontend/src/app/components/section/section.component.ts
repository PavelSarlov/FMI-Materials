import { Component, OnInit, Input } from '@angular/core';
import { Section } from '../../models/section';

@Component({
  selector: 'app-section',
  templateUrl: './section.component.html',
  styleUrls: ['./section.component.scss'],
})
export class SectionComponent implements OnInit {
  @Input()
  section?: Section;

  fileFormats: any = {
    'text/plain': 'text_snippet',
    'text/html': 'html',
    'text/css': 'css',
    'application/javascript': 'javascript',
    'application/x-httpd-php': 'php',
    'image/png': 'image',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document': 'description',
    'application/pdf': 'picture_as_pdf',
    'application/octet-stream': 'insert_drive_file',
    'default': 'text_snippet'
  }

  fileToUpload?: File;

  constructor() {}

  ngOnInit(): void {}

  onMaterialSelected(event: any) {
    event.preventDefault();
    this.fileToUpload = event.currentTarget.files[0];
  }

  onMaterialSendRequest(event: any) {
    console.log(event);
  }

  handleSubmitClick(event: any) {
    event.stopPropagation();
  }
}
