import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, NgIf, RouterLinkActive],
  template: `
    <div class="d-flex">


    <div class="sidebar" [class.collapsed]="isCollapsed">
      <div class="logo"></div>
      <ul class="nav flex-column">
        <li class="nav-item">
          <a class="nav-link" routerLink="/register" routerLinkActive="active-link">
            <i class="bi-person"> <span class="ps-3" *ngIf="!isCollapsed">Register</span></i>
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" routerLink="/login" routerLinkActive="active-link">
            <i class="bi-power"> <span class="ps-3" *ngIf="!isCollapsed">Login</span></i>
          </a>
        </li>
      </ul>
      <button class="btn btn-toggle text-white" (click)="toggleCollapse()">
        {{ isCollapsed ? '>' : '<' }}
      </button>
    </div>
      <div class="content vh-100  d-flex justify-content-center align-items-center w-100">
        <router-outlet />
      </div>

    </div>
  `,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
  isCollapsed = false;

  toggleCollapse() {
    this.isCollapsed = !this.isCollapsed;
  }
}
