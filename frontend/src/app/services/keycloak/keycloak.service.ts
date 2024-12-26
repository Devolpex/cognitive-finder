import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import Keycloak from 'keycloak-js';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  // Private property to store the Keycloak instance
  private _keycloak: Keycloak | undefined;

  // Getter for the Keycloak instance
  get keycloak() {
    if (!this._keycloak && isPlatformBrowser(this.platformId)) {
      this._keycloak = new Keycloak({
        url: 'http://localhost:8181',
        realm: 'COG',
        clientId: 'cog-angular',
      });
    }
    return this._keycloak;
  }

  get token () {
    console.log('Keycloak token:', this.keycloak?.token);
    return this.keycloak?.token;
  }

  constructor(@Inject(PLATFORM_ID) private platformId: any) {}

  async init(): Promise<void> {
    if (isPlatformBrowser(this.platformId)) {
      console.log('Init Keycloak');
      const authenticated = await this.keycloak?.init({
        onLoad: 'login-required',
      });

      if (authenticated) {
        console.log('Keycloak is authenticated');
        this.token;
      } else {
        console.log('Keycloak is not authenticated');
      }
    } else {
      console.log('Keycloak initialization skipped because the environment is not a browser');
    }
  }

  logout() {
    // this.keycloak.accountManagement();
    return this.keycloak?.logout({redirectUri: 'http://localhost:4200'});
  }


}
