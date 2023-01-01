import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { MaterialRequest } from '../models/material-request';
import { ResponseDto } from '../models/response';
import { Subscription } from '../models/subscription';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  subscriptions$: BehaviorSubject<Subscription[]> = new BehaviorSubject<
    Subscription[]
  >([]);

  constructor(private http: HttpClient) {}

  createMaterialRequest(
    formData: FormData,
    sectionId: number,
    userId: number
  ): any {
    return this.http
      .post<MaterialRequest>(
        `${environment.usersApi}/${userId}/material-requests/${sectionId}`,
        formData
      )
      .pipe(tap({ error: (err) => console.log(err) }));
  }

  findSubscriptionsByUserId(userId: number) {
    return this.http
      .get<Subscription[]>(`${environment.usersApi}/${userId}/subscriptions`)
      .pipe(
        tap({
          error: (err) => console.log(err),
        })
      )
      .subscribe({
        next: (value) => this.subscriptions$.next(value),
      });
  }

  createSubscription(userId: number, subscription: Subscription) {
    return this.http
      .post<Subscription>(
        `${environment.usersApi}/${userId}/subscriptions`,
        subscription
      )
      .pipe(
        tap({
          error: (err) => console.log(err),
        })
      )
      .subscribe({
        next: (_) => this.findSubscriptionsByUserId(userId),
      });
  }

  deleteSubscriptionById(userId: number, subscription: Subscription) {
    return this.http
      .delete<ResponseDto>(
        `${environment.usersApi}/${userId}/subscriptions/${subscription.id!}`
      )
      .pipe(
        tap({
          error: (err) => console.log(err),
        })
      )
      .subscribe({
        next: (_) => this.findSubscriptionsByUserId(userId),
      });
  }
}
