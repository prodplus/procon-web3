export class Page<T> {
  content: T[];
  pageable?: Pageable;
  last: boolean;
  totalElements: number;
  totalPages: number;
  first: boolean;
  sort?: Sort;
  number: number;
  numberOfElements: number;
  size: number;
  empty: boolean;

  constructor() {
    this.content = [];
    this.last = true;
    this.totalElements = 0;
    this.totalPages = 0;
    this.first = true;
    this.number = 0;
    this.numberOfElements = 0;
    this.size = 0;
    this.empty = true;
  }
}

interface Pageable {
  sort: Sort;
  pageNumber: number;
  pageSize: number;
  offset: number;
  paged: boolean;
  unpaged: boolean;
}

interface Sort {
  sorted: boolean;
  unsorted: boolean;
  empty: boolean;
}
