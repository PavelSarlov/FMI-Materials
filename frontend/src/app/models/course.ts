import {CourseGroup} from './course-group';
import {FacultyDepartment} from './faculty-department';
import {Section} from './section';

export class Course {
  id?: number;
  name?: string;
  description?: string;
  createdBy?: string;
  facultyDepartmentDto?: FacultyDepartment;
  courseGroup?: CourseGroup;
  sectionDtos?: Section[];
}
