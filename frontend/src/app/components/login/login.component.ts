import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { AlertService } from '../../services/alert.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.router.navigateByUrl('courses');
    }

    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: [
        '',
        [
          Validators.required,
          Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}$'),
        ],
      ],
    });
  }

  get loginData() {
    return this.loginForm.controls;
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.authService
        .authenticate(
          this.loginForm.get('email')?.value,
          this.loginForm.get('password')?.value
        )
        .subscribe({
          next: (resp) => {
            this.alertService.success('Login successful!', {
              keepAfterRouteChange: true,
            });
            this.router.navigateByUrl('courses');
          },
          error: (resp) => {
            if (typeof resp.error.error === 'object') {
              for (let error of resp.error.error) {
                this.alertService.error(error);
              }
            }
            else {
              this.alertService.error(resp.error.error);
            }
          },
        });
    } else {
      this.alertService.error('Invalid data');
    }
  }
}