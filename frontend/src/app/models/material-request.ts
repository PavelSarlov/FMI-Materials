import { Section } from './section';
import { User } from './user';

export class MaterialRequest {
  id?: number;
  courseId?: number;
  fileFormat?: string;
  fileName?: string;
  sectionDto?: Section;
  userDto?: User;
}
