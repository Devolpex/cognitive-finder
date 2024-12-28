import { PatientProvider } from "../context/PatientProvider";
import { PatientTableBody } from "../components/PatientTableBody";
import { Card } from "@material-tailwind/react";
import { PatientTableHeader } from "../components/PatientTableHeader";
import { PatientFormModal } from "../components/PatientFormModal";
import { DeletePatientModal } from "../components/DeletePatientModal";

export function PatientPage() {
  return (
    <div>
      <PatientProvider>
        <Card
          className="w-full p-8"
          placeholder={undefined}
          onPointerEnterCapture={undefined}
          onPointerLeaveCapture={undefined}
        >
          <PatientTableHeader />
          <PatientTableBody />
        </Card>
        <PatientFormModal />
        <DeletePatientModal />
      </PatientProvider>
    </div>
  );
}
