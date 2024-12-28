import { PatientProvider } from "../context/PatientProvider";
import { PatientTableBody } from "../components/PatientTableBody";
import { Card } from "@material-tailwind/react";
import { PatientTableHeader } from "../components/PatientTableHeader";
import { PatientFormModal } from "../components/PatientFormModal";
import { DeletePatientModal } from "../components/DeletePatientModal";

export function PatientPage() {
  return (
    <div className="h-screen flex flex-col items-center">
      <div className="mt-16 w-full flex justify-center">
        <PatientProvider>
          <Card
            className="w-3/4 p-8"
            placeholder={undefined}
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
          >
            <h1 className="text-center mb-4 text-lg font-bold">Patient Page</h1>
            <PatientTableHeader />
            <PatientTableBody />
          </Card>
          <PatientFormModal />
          <DeletePatientModal />
        </PatientProvider>
      </div>
    </div>
  );
}
