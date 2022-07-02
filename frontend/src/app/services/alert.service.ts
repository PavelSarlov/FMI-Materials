import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {filter} from 'rxjs/operators';
import {Alert, AlertType} from '../models/alert';


@Injectable({providedIn: 'root'})
export class AlertService {
  private subject = new Subject<Alert>();
  private defaultId = 'default-alert';

  // enable subscribing to alerts observable
  onAlert(id = this.defaultId): Observable<Alert> {
    return this.subject.asObservable().pipe(filter((x) => x && x.id === id));
  }

  // convenience methods
  success(message: any, options?: any) {
    if (typeof message === 'string') {
      this.alert(new Alert({...options, type: AlertType.Success, message}));
    }
    else {
      for (let key of Object.keys(message)) {
        this.alert(new Alert({...options, type: AlertType.Success, message: message[key]}));
      }
    }
  }

  error(message: any, options?: any) {
    if (typeof message === 'string') {
      this.alert(new Alert({...options, type: AlertType.Error, message}));
    }
    else {
      for (let key of Object.keys(message)) {
        this.alert(new Alert({...options, type: AlertType.Error, message: message[key]}));
      }
    }
  }

  info(message: any, options?: any) {
    if (typeof message === 'string') {
      this.alert(new Alert({...options, type: AlertType.Info, message}));
    }
    else {
      for (let key of Object.keys(message)) {
        this.alert(new Alert({...options, type: AlertType.Info, message: message[key]}));
      }
    }
  }

  warn(message: any, options?: any) {
    if (typeof message === 'string') {
      this.alert(new Alert({...options, type: AlertType.Warning, message}));
    }
    else {
      for (let key of Object.keys(message)) {
        this.alert(new Alert({...options, type: AlertType.Warning, message: message[key]}));
      }
    }
  }

  // main alert method
  alert(alert: Alert) {
    alert.id = alert.id || this.defaultId;
    this.subject.next(alert);
  }

  // clear alerts
  clear(id = this.defaultId) {
    this.subject.next(new Alert({id}));
  }
}
