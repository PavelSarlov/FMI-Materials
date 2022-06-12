export class Pagination<T> {
  first?: boolean;
  last?: boolean;
  currentPage?: number;
  totalItems?: number;
  totalPages?: number;
  items?: T[];

  constructor() {}
}
