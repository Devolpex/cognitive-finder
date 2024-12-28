import { createBrowserRouter } from "react-router-dom";
import { HomePage } from "./pages/HomePage";
import { AdminLayout } from "./layout/AdminLayout";
import { PatientPage } from "./pages/PatientPage";
import { PatientMapPage } from "./pages/PatientMapPage";
import { AllPatientsMapPage } from "./pages/AllPatientsMapPage";


export const router = createBrowserRouter([
  {
    path: "/",
    element: <HomePage />,
  },
  {
    path: "/admin",
    element: <AdminLayout />,
    children: [
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
