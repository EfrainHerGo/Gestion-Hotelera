import { Injectable } from '@angular/core';
import { JwtPayload } from '../models/Auth.model';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { JwtHelper } from '../shared/Jwt.helper';
import { tap } from 'rxjs';
import { environment } from '../eviroments/evironment';
import { AuthRequest, AuthResponse} from '../models/Auth.model';
/**
 * se puede inyectar desde la rais y si no debemos 
 * proveer a que modulo
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {
/**
 * tokenkey es el valor del token 
 * 
 */
  private authUrl: string = environment.authUrl;
  private tokenKey: string = 'auth-token';
  private payload: JwtPayload | null = null;

  constructor(private http: HttpClient, private router: Router) {
    this.decodeToken(); // Hay que decodificar el token al iniciarl el servicio
  }
/** hace login, busca y valida la informacion, regresa */
  login(username: string, password: string) {
    let authRequest: AuthRequest = { username, password };
    return this.http.post<AuthResponse>(this.authUrl, authRequest).pipe(
      tap(response => {
        if (response?.token) {
          localStorage.setItem(this.tokenKey, response.token);
          this.decodeToken();
        }
      })
    );
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    return !!token && !JwtHelper.isTokenExpired(token);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.payload = null;
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  // ------------------------
  // Funciones de roles y usuario
  // ------------------------

  private decodeToken(): void {
    const token = this.getToken();
    this.payload = token ? JwtHelper.decodeToken(token) : null;
  }

  getUsername(): string | null {
    return this.payload?.sub || null;
  }

  getRoles(): string[] {
    return this.payload?.roles || [];
  }

  hasRole(role: string): boolean {
    return this.getRoles().includes(role);
  }

  hasAnyRole(roles: string[]): boolean {
    return roles.some(role => this.hasRole(role));
  }
}
