import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap, BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { MaterialRequest } from '../models/material-request';
import { Material } from '../models/material';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  constructor(private http: HttpClient) {}

  getAllMaterialRequests(adminId: number): Observable<MaterialRequest[]> {
    return this.http
      .get<MaterialRequest[]>(
        `${environment.adminsApi}/${adminId}/material-requests`
      )
      .pipe(
        tap({
          error: (err) => console.log(err),
        })
      );
  }

  getMaterialRequestById(
    adminId: number,
    materialRequestId: number
  ): Observable<MaterialRequest> {
    return this.http
      .get<MaterialRequest>(
        `${environment.adminsApi}/${adminId}/material-requests/${materialRequestId}`
      )
      .pipe(
        tap({
          error: (err) => console.log(err),
        })
      );
  }

  getMaterialFromMaterialRequest(
    adminId: number,
    materialRequestId: number
  ): any {
    return this.http
      .get(
        `${environment.adminsApi}/${adminId}/material-requests/${materialRequestId}/material`,
        { responseType: 'blob' }
      )
      .pipe(
        tap({
          error: (err) => console.log(err),
        })
      );
  }

  processMaterialRequest(
    adminId: number,
    materialRequestId: number,
    status: boolean = false
  ) {
    return this.http
      .post(
        `${environment.adminsApi}/${adminId}/material-requests/${materialRequestId}?status=${status}`,
        null
      )
      .pipe(
        tap({
          next: (resp) => console.log(resp),
          error: (err) => console.log(err),
        })
      );
  }
}
