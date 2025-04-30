import {Component, ElementRef, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-register',
  imports: [
    NgOptimizedImage,
    ReactiveFormsModule
  ],
  template: `
    <div class="container d-flex flex-column justify-content-center align-items-center vh-100">
      <div class="text-center">

        <img ngSrc="https://t3.ftcdn.net/jpg/03/02/36/70/360_F_302367092_vmqO9rSPmluZJFuOFFNzjGwimbChoIAZ.jpg"
             width="100" height="100" alt="logo">

        <h4 class="mb-4">Enter Details to Register</h4>
      </div>
      <div class="card p-4" style="width: 100%; max-width: 600px;">
        <form #frm [formGroup]="registerForm">
          <div class="mb-3 position-relative">
            <i class="bi bi-person form-icon"> Name:</i>
            <input type="text"
                   class="form-control"
                   placeholder="Enter name"
                   formControlName="name"
                   [class.is-invalid]="isInvalid('name')"

            />

          </div>
          <div class="mb-3 position-relative">
            <i class="bi bi-envelope form-icon"> Email:</i>
            <input
              type="email"
              class="form-control"
              placeholder="Enter email"
              formControlName="email"
              [class.is-invalid]="isInvalid('email')"
            />
          </div>
          <div class="mb-3 position-relative">
            <i class="bi bi-lock form-icon"> Password:</i>
            <input type="password"
                   class="form-control"
                   placeholder="Enter password"
                   formControlName="password"
                   [class.is-invalid]="isInvalid('password')"
            />
          </div>
          <button type="button" class="btn btn-dark w-100">Register</button>
        </form>
      </div>
    </div>
  `,
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  @ViewChild("frm")
  frmElmRef!: ElementRef<HTMLFormElement>
  registerForm!: FormGroup;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required, Validators.minLength(6)]],
      name: [null, [Validators.required]],
    })
  }



  isInvalid(controlName: string ): boolean {
    const ctrl = this.registerForm
      .get(controlName)!;
    return ctrl.invalid && ctrl.touched;
  }

}
