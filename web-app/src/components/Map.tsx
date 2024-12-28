import React, { useEffect, useRef, useState } from "react";
import L from "leaflet";
import { PositionResponseTRC } from "../types/location";

interface IMapProps {
  location: PositionResponseTRC | null;
}

const Map = ({ location }: IMapProps) => {
  const mapContainerRef = useRef(null); // Reference to the div element
  const mapInstanceRef = useRef<L.Map | null>(null); // Reference to the Leaflet map instance
  const userMarkerRef = useRef<L.Marker | null>(null); // Reference to the user marker

  useEffect(() => {
    if (location && mapContainerRef.current && !mapInstanceRef.current) {
      // Initialize the map only if location is not null and map is not initialized
      mapInstanceRef.current = L.map(mapContainerRef.current).setView(
        [location.latitude, location.longitude],
        13
      );

      L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
        maxZoom: 19,
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }).addTo(mapInstanceRef.current);

      // Create a marker for the user location
      if (location) {
        userMarkerRef.current = L.marker([location.latitude, location.longitude]).addTo(mapInstanceRef.current)
          .bindPopup(`${location.speed} km/h`)
          .openPopup();
      }
    } else if (mapInstanceRef.current && location) {
      // If the map is already initialized, just update the view and marker
      mapInstanceRef.current.setView([location.latitude, location.longitude], 13);

      if (userMarkerRef.current) {
        userMarkerRef.current.setLatLng([location.latitude, location.longitude]);
      } else {
        // If no marker exists yet, create a new one
        userMarkerRef.current = L.marker([location.latitude, location.longitude]).addTo(mapInstanceRef.current)
          .bindPopup(`${location.speed} km/h`)
          .openPopup();
      }
    }

    return () => {
      // Cleanup the map instance when the component unmounts
      if (mapInstanceRef.current) {
        mapInstanceRef.current.remove();
        mapInstanceRef.current = null;
      }
    };
  }, [location]); // Re-run the effect when location changes

  if (!location) {
    return <div>Loading map...</div>; // Show a loading message if location is null
  }

  return <div id="map" ref={mapContainerRef} style={{ height: "100vh" }}></div>;
};

export default Map;
