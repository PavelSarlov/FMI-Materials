import { FacultyDepartment } from './faculty-department';
import { CourseGroup } from './course-group';
import { Section } from './section';

export class Course {
  id?: number;
  name?: string;
  description?: string;
  createdBy?: string;
  facultyDepartmentDto?: FacultyDepartment;
  courseGroupDto?: CourseGroup;
  sectionDtos?: Section[];

  constructor() {}
}
