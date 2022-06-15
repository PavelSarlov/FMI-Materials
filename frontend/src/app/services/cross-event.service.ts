import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CrossEventService {
  toggleSidenav: EventEmitter<boolean> = new EventEmitter<boolean>();
  sectionOnDelete: EventEmitter<any> = new EventEmitter<any>();
  materialOnDelete: EventEmitter<any> = new EventEmitter<any>();

  constructor() {}
}
