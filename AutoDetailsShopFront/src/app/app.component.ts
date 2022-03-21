import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './auth/token-storage.service';
import * as $ from "jquery";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'front';
  constructor(private tokenStorage: TokenStorageService){}

  isAdmin(){
    return this.tokenStorage.getRole() == 'ADMIN';
  }

  isLogged(){
    return this.tokenStorage.getRole() != null;
  }

  ngOnInit(){
    $(function () {
      function slideMenu() {
        var activeState = $("#menu-container .menu-list").hasClass("active");
        $("#menu-container .menu-list").animate(
          { left: activeState ? "0%" : "-100%" },
          400
        );
      }
      $("#menu-wrapper").click(function (event) {
        event.stopPropagation();
        $("#hamburger-menu").toggleClass("open");
        $("#menu-container .menu-list").toggleClass("active");
        slideMenu();
    
        $("body").toggleClass("overflow-hidden");
      });
    });
  }

  logout() {
    this.tokenStorage.signOut();
    window.location.reload();
  }

  getUsername(){
    return this.tokenStorage.getUsername();
  }
}
