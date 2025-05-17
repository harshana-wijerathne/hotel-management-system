import { Component, ElementRef, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { NgOptimizedImage } from '@angular/common';
import { AuthService } from '../services/auth/auth.service';
import { Toast } from 'bootstrap';
import { ToastService } from '../services/toast/toast.service';
import { Router } from '@angular/router';
import { UserStorageService } from '../services/storage/user-storage.service';

@Component({
  selector: 'app-login',
  imports: [NgOptimizedImage, ReactiveFormsModule],
  template: `
    <div
      class="container d-flex flex-column justify-content-center align-items-center vh-100"
    >
      <div class="text-center">
        <img
          ngSrc="https://t3.ftcdn.net/jpg/03/02/36/70/360_F_302367092_vmqO9rSPmluZJFuOFFNzjGwimbChoIAZ.jpg"
          width="100"
          height="100"
          alt="logo"
          priority
        />

        <h4 class="mb-4">Enter Details to login</h4>
      </div>
      <div class="card p-4" style="width: 100%; max-width: 600px;">
        <form #frm [formGroup]="loginForm" (ngSubmit)="submitForm()">
          <div class="mb-3 position-relative">
            <i class="bi bi-envelope-at-fill form-icon"> Email:</i>
            <input
              type="email"
              class="form-control"
              placeholder="Enter email"
              formControlName="email"
              [class.is-invalid]="isInvalid('email')"
            />
          </div>
          <div class="mb-3 position-relative">
            <i class="bi bi-lock-fill form-icon"> Password:</i>
            <input
              type="password"
              class="form-control"
              placeholder="Enter password"
              formControlName="password"
              [class.is-invalid]="isInvalid('password')"
            />
          </div>
          <button
            type="submit"
            class="btn btn-dark w-100"
            [disabled]="loginForm.invalid"
          >
            Login
          </button>
        </form>
        <div class="pt-2">
          Don't have an account?
          <a
            class="text-primary cursor-pointer "
            style="cursor: pointer;"
            (click)="navigateToRegister()"
            >Sign up</a
          >
        </div>
      </div>
    </div>
  `,
  styleUrl: './login.component.css',
})
export class LoginComponent {
  @ViewChild('frm')
  frmElmRef!: ElementRef<HTMLFormElement>;
  loginForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private toastService: ToastService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required]],
    });
  }

  isInvalid(controlName: string): boolean {
    const ctrl = this.loginForm.get(controlName)!;
    return ctrl.invalid && ctrl.touched;
  }

  submitForm() {
    this.authService.login(this.loginForm.value).subscribe(
      (res) => {
        console.log(res);
        if (res.userId != null) {
          const user = {
            id: res.userId,
            role: res.userRole,
          };

          UserStorageService.saveUser(user);
          UserStorageService.saveToken(res.jwt);
        }
      },
      (error) => this.toastService.show('Bad Credentials', 'error', 2000)
    );
  }

  navigateToRegister(): void {
    this.router.navigateByUrl('/register');
  }
}
