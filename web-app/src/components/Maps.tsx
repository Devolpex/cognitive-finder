import React, { useEffect, useRef } from "react";
import L from "leaflet";
import { PositionResponseTRC } from "../types/location";
import { Spinner } from "@material-tailwind/react";

interface IMapsProps {
  locations: PositionResponseTRC[]; // Accept an array of locations
}

const Maps = ({ locations }: IMapsProps) => {
  const mapContainerRef = useRef(null); // Reference to the div element
  const mapInstanceRef = useRef<L.Map | null>(null); // Reference to the Leaflet map instance

  useEffect(() => {
    if (locations.length > 0 && mapContainerRef.current && !mapInstanceRef.current) {
      // Initialize the map with the first location
      mapInstanceRef.current = L.map(mapContainerRef.current).setView(
        [locations[0].latitude, locations[0].longitude],
        13
      );

      L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
        maxZoom: 19,
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }).addTo(mapInstanceRef.current);

      const bounds = L.latLngBounds([]); // Initialize bounds to collect all locations

      // Add a marker for each location
      locations.forEach((location) => {
        const marker = L.marker([location.latitude, location.longitude])
          .addTo(mapInstanceRef.current)
          .bindPopup(`${location.speed} km/h`)
          .openPopup();

        // Extend the bounds to include each marker's location
        bounds.extend(marker.getLatLng());
      });

      // Fit the map to include all markers
      mapInstanceRef.current.fitBounds(bounds);

    } else if (mapInstanceRef.current && locations.length > 0) {
      // If the map is already initialized, update the locations
      const bounds = L.latLngBounds([]); // Initialize bounds to collect all locations

      // Add markers for all locations
      locations.forEach((location) => {
        const marker = L.marker([location.latitude, location.longitude])
          .addTo(mapInstanceRef.current)
          .bindPopup(`${location.speed} km/h`)
          .openPopup();

        // Extend the bounds to include each marker's location
        bounds.extend(marker.getLatLng());
      });

      // Fit the map to include all markers
      mapInstanceRef.current.fitBounds(bounds);
    }

    return () => {
      // Cleanup the map instance when the component unmounts
      if (mapInstanceRef.current) {
        mapInstanceRef.current.remove();
        mapInstanceRef.current = null;
      }
    };
  }, [locations]); // Re-run the effect when locations change

  if (locations.length === 0) {
    return (
      <div className="flex items-center justify-center h-screen">
        <Spinner
          className="h-12 w-12"
          onPointerEnterCapture={undefined}
          onPointerLeaveCapture={undefined}
        />
      </div>
    ); // Show a loading message if there are no locations
  }

  return <div id="map" ref={mapContainerRef} style={{ height: "100vh" }}></div>;
};

export default Maps;
