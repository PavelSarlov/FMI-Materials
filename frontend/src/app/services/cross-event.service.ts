import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CrossEventService {
  toggleSidenav: EventEmitter<boolean> = new EventEmitter<boolean>();
  constructor() {}
}
