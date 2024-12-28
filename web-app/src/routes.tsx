import { createBrowserRouter, Navigate } from "react-router-dom";
import { HomePage } from "./pages/HomePage";
import { AdminLayout } from "./layout/AdminLayout";
import { PatientPage } from "./pages/PatientPage";
import { PatientMapPage } from "./pages/PatientMapPage";
import { AllPatientsMapPage } from "./pages/AllPatientsMapPage";


export const router = createBrowserRouter([

  {
    path: "/",
    element: <AdminLayout />,

    children: [
      {
        path: "", // This is the default path for /
        element: <Navigate to="/patient" />, // Redirects to /patient
      },
      {
        path: "patient",
        element: <PatientPage />,
      },
      {
        path: "patient/map/:patientId",
        element: <PatientMapPage />,
      },
      {
        path: "map",
        element: <AllPatientsMapPage />,
      }
    ],
  },
]);
