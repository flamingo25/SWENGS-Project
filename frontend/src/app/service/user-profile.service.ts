import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserProfile} from '../api/profile';

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {

  constructor(private http: HttpClient) {
  }

  // ---------------------------------------------------------------------------------

  getById(id: number) {
    return this.http.get('/api/users/' + id);
  }

  create(userProfile: UserProfile) {
    return this.http.post('/api/users' + userProfile.id, userProfile);
  }

  update(userProfile: UserProfile) {
    return this.http.put('/api/users/' + userProfile.id, userProfile);
  }

  // ---------------------------------------------------------------------------------

}
