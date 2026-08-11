import { HttpInterceptorFn } from '@angular/common/http';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {

  // Do NOT attach JWT to authentication requests
  if (
    req.url.includes('/api/auth/login') ||
    req.url.includes('/api/auth/register')
  ) {
    return next(req);
  }

  let token: string | null = null;

  if (typeof window !== 'undefined') {
    token = localStorage.getItem('token');
  }

  if (token) {

    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

  }

  return next(req);
};