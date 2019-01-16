import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {OfferService} from '../service/offer.service';

@Injectable({
  providedIn: 'root'
})
export class OfferListResolver implements Resolve<Observable<any>> {

  constructor(private offerService: OfferService) {
  }

  resolve(route: ActivatedRouteSnapshot,
          state: RouterStateSnapshot): Observable<Observable<any>> | Promise<Observable<any>> | Observable<any> {
    return this.offerService.getAll();
  }
}
