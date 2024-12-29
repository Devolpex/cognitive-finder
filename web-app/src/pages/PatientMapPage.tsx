import { useParams } from "react-router-dom";
import Map from "../components/Map";
import React from "react";
import { getLocationByPatientId } from "../services/LocationService";
import { PositionResponseTRC } from "../types/location";

export function PatientMapPage() {
  const { patientId } = useParams<{ patientId: string }>(); // Extract patientId from the URL
  const [location, setLocation] = React.useState<PositionResponseTRC | null>(null);


  React.useEffect(() => {
    let intervalId: NodeJS.Timeout | null = null;

    const fetchLocation = () => {
      if (patientId) {
        getLocationByPatientId(patientId)
          .then((location) => {
            console.log("Location data:", location); // Handle location data
            setLocation(location);
          })
          .catch((error) => {
            console.error("Error fetching location:", error); // Handle errors
          });
      }
    };

    // Initial fetch
    fetchLocation();

    // Set up polling every 5 seconds
    if (patientId) {
      intervalId = setInterval(fetchLocation, 5000);
    }

    // Cleanup interval on component unmount
    return () => {
      if (intervalId) {
        clearInterval(intervalId);
      }
    };
  }, [patientId]);

  return (
    <div>
      <Map location={location} />
    </div>
  );
}
