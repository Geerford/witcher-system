import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import {Constants} from '../utils/constants';

@Injectable()
export class UrlPermissionUserKing implements CanActivate {

  constructor(private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (localStorage.getItem(Constants.ROLES) === 'USER' || localStorage.getItem(Constants.ROLES) === 'KING') {
      return true;
    }
    // not logged in so redirect to login page with the return url
    this.router.navigate(['/login'], {queryParams: {returnUrl: state.url}}).then(() => console.log(Constants.NOT_AUTH));
    return false;
  }
}
