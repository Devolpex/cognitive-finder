import { createBrowserRouter } from "react-router-dom";
import { HomePage } from "./pages/HomePage";
import { AdminLayout } from "./layout/AdminLayout";
import { PatientPage } from "./pages/PatientPage";


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
    ],
  },
]);
