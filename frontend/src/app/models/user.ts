export class User {
  id?: number;
  name?: string;
  email?: string;
  roles?: string[];
}

export enum USER_ROLES {
  ADMIN = 'ADMIN',
  USER = 'USER',
}
