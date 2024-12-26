import { KeycloakService } from './../services/keycloak/keycloak.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, interval } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import * as L from 'leaflet';
import { PositionResponseTRC } from '../types/PositionResponseTRC';
import { PositionService } from '../services/position/position.service';

@Component({
  selector: 'app-patient-map',
  templateUrl: './patient-map.component.html',
  styleUrls: ['./patient-map.component.css'],
})
export class PatientMapComponent implements OnInit, OnDestroy {
  private map: L.Map | undefined;
  private markers: L.Marker[] = [];
  private subscription: Subscription | undefined;
  positionData: PositionResponseTRC[] = [];

  constructor(
    private positionService: PositionService,
    private KeycloakService: KeycloakService // Inject Keycloak service
  ) {}

  ngOnInit(): void {
    this.initMap();
    const clientId = this.KeycloakService.keycloak?.tokenParsed?.sub;
    if (!clientId) {
      console.error('Client ID not available');
      return; // Exit if clientId is undefined
    }
    // Call the API every 10 seconds
    this.subscription = interval(10000)
      .pipe(
        switchMap(() => this.positionService.getPositionsByClientId(clientId)) // Send clientId from Keycloak
      )
      .subscribe({
        next: (data) => {
          this.positionData = data;
          console.log('Position updated:', data);
          this.updateMarkers(data);
        },
        error: (error) => {
          console.error('Error fetching position:', error);
        },
      });
  }

  ngOnDestroy(): void {
    if (this.map) {
      this.map.remove(); // Cleanup the map
    }
    if (this.subscription) {
      this.subscription.unsubscribe(); // Unsubscribe to avoid memory leaks
    }
  }

  private initMap(): void {
    // Initialize the map and set its view to a specific location and zoom level
    this.map = L.map('map').setView([51.505, -0.09], 13);

    // Add OpenStreetMap tiles
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution:
        '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
    }).addTo(this.map);
  }

  private updateMarkers(positions: PositionResponseTRC[]): void {
    // Remove existing markers
    this.markers.forEach(marker => this.map?.removeLayer(marker));
    this.markers = [];

    // Add new markers for each position
    positions.forEach(position => {
      const marker = L.marker([position.latitude, position.longitude]).addTo(this.map!);
      this.markers.push(marker);
    });

    // Optionally, you can fit the map view to the markers
    const group = L.featureGroup(this.markers);
    this.map?.fitBounds(group.getBounds());
  }
}
