import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ModalComponent } from '../components/modal/modal.component';
import { LoginForm } from '../models/forms/login-form';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('input')
  inputLogin!: ElementRef<HTMLInputElement>;
  isLoading = false;
  @ViewChild('modal')
  modal!: ModalComponent;
  form!: FormGroup;

  constructor(
    private builder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.form = this.builder.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.inputLogin.nativeElement.focus();
    }, 100);
  }

  login() {
    this.isLoading = true;
    const email = this.form.get('email')?.value;
    const password = this.form.get('password')?.value;

    this.authService.authenticate(new LoginForm(email, password)).subscribe({
      error: (err) => {
        this.isLoading = false;
        this.modal.openErro(err);
        this.inputLogin.nativeElement.focus();
      },
      complete: () => {
        this.isLoading = false;
        this.router.navigate(['home']);
      }
    });
  }
}
