import { Material } from './material';
import { MaterialRequest } from './material-request';

export class Section {
  id?: number;
  name?: string;
  materialDtos?: Material[];
  materialRequestDtos?: MaterialRequest[];
}
