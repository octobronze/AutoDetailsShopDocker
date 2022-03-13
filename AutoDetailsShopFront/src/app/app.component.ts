import { Component } from '@angular/core';
import { TokenStorageService } from './auth/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'front';

  constructor(private tokenStorage: TokenStorageService){}

  isAdmin(){
    return this.tokenStorage.getRole() == 'ADMIN';
  }

  isLogged(){
    return this.tokenStorage.getRole() != null;
  }

  logout() {
    this.tokenStorage.signOut();
    window.location.reload();
  }
}
