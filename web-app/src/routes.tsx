import { createBrowserRouter } from "react-router-dom";
import { HomePage } from "./pages/HomePage";
import { AdminLayout } from "./layout/AdminLayout";
import { PatientPage } from "./pages/PatientPage";
import { PatientMapPage } from "./pages/PatientMapPage";


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
      }
    ],
  },
]);
