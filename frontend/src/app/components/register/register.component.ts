import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  AbstractControl,
  ValidationErrors,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { AlertService } from '../../services/alert.service';

const checkPasswordsMatch = (
  control: AbstractControl
): ValidationErrors | null => {
  return control.get('password')?.value !==
    control.get('repeatedPassword')?.value
    ? { passwordsDontMatch: true }
    : null;
};

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.alertService.warn('You are already logged in!', {
        keepAfterRouteChange: true,
      });
      this.router.navigateByUrl('courses');
    }

    this.registerForm = this.fb.group(
      {
        email: ['', [Validators.required, Validators.email]],
        name: ['', [Validators.required]],
        password: [
          '',
          [
            Validators.required,
            Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}$'),
          ],
        ],
        repeatedPassword: [''],
      },
      {
        validators: checkPasswordsMatch,
      }
    );
  }

  get registerData() {
    return this.registerForm.controls;
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.authService
        .register(
          this.registerForm.get('email')?.value,
          this.registerForm.get('name')?.value,
          this.registerForm.get('password')?.value,
          this.registerForm.get('repeatedPassword')?.value
        )
        .subscribe({
          next: (resp) => {
            this.alertService.success('Registration successful!', {
              keepAfterRouteChange: true,
            });
            this.router.navigateByUrl('/auth/login');
          },
          error: (resp) => {
            if (typeof resp.error.error === 'object') {
              for (let error of resp.error.error) {
                this.alertService.error(error);
              }
            } else {
              this.alertService.error(resp.error.error);
            }
          },
        });
    } else {
      this.alertService.error('Invalid data!');
    }
  }
}
