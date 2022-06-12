import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

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
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
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
        validator: this.checkPasswordsMatch,
      }
    );
  }

  get registerData() {
    return this.registerForm.controls;
  }

  checkPasswordsMatch(form: AbstractControl) {
    return form.get('password')?.value === form.get('repeatedPassword')?.value;
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
          next: (resp) => this.router.navigateByUrl('/auth/login'),
          error: (err) => console.log(err),
        });
    } else {
      console.log(this.registerForm.status);
    }
  }
}
