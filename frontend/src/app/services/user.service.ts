import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { tap } from 'rxjs';
import { MaterialRequest } from '../models/material-request';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  constructor(private http: HttpClient) {}

  createMaterialRequest(formData: FormData, sectionId: number, userId: number): any {
    return this.http
      .post<MaterialRequest>(
        `${environment.usersApi}/${userId}/material-requests/${sectionId}`,
        formData
      )
      .pipe(tap({ error: (err) => console.log(err) }));
  }
}
