import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CrossEventService {
  toggleSidenav: EventEmitter<boolean> = new EventEmitter<boolean>();
  sectionEvent: EventEmitter<any> = new EventEmitter<any>();
  materialEvent: EventEmitter<any> = new EventEmitter<any>();
  materialSearchEvent: EventEmitter<any> = new EventEmitter<any>();

  constructor() {}
}
