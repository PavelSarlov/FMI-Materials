export class Pagination<T> {
  first?: boolean;
  last?: boolean;
  currentPage?: number;
  totalItems?: number;
  totalPages?: number;
  itemsPerPage?: number;
  items?: T[];

  constructor() {}
}
