import * as React from "react";
import { PositionResponseTRC } from "../types/location";
import { getLocationByClientIdAPI } from "../services/LocationService";
import Map from "../components/Map"; // Assuming you have a Map component
import Maps from "../components/Maps";

export function AllPatientsMapPage() {
  const [locations, setLocations] = React.useState<PositionResponseTRC[]>([]);

  // Get userId from local storage
  const userId: string | null = localStorage.getItem("userId");

  React.useEffect(() => {
    if (!userId) {
      return;
    }

    const fetchLocations = () => {
      // Fetch locations for the client ID
      getLocationByClientIdAPI(userId)
        .then((data) => {
          setLocations(data); // Store the data in state
          console.log("Locations data:", data); // Handle location data
        })
        .catch((error) => {
          console.error("Error fetching locations:", error); // Handle errors
        });
    };

    // Initial fetch
    fetchLocations();

    // Set up polling every 5 seconds
    const intervalId = setInterval(fetchLocations, 5000);

    // Cleanup interval on component unmount
    return () => {
      clearInterval(intervalId);
    };
  }, [userId]); // Only run this effect when the userId changes

  return (
    <div>
      {locations.length > 0 ? (
        <Maps locations={locations} />
      ) : (
        <p>No locations available</p>
      )}
    </div>
  );
}
