import * as React from "react";
import {
  MagnifyingGlassIcon,
  UserPlusIcon,
} from "@heroicons/react/24/outline";
import {
  CardHeader,
  Typography,
  Button,
  Input,
} from "@material-tailwind/react";
import { usePatient } from "../context/PatientProvider";

export function PatientTableHeader() {
    const {formModal} = usePatient();
  return (
    <CardHeader
      floated={false}
      shadow={false}
      className="rounded-none"
      placeholder={undefined}
      onPointerEnterCapture={undefined}
      onPointerLeaveCapture={undefined}
    >
      <div className="mb-4 flex flex-col justify-between gap-8 md:flex-row md:items-center">
        <div>
          <Typography
            variant="h5"
            color="blue-gray"
            placeholder={undefined}
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
          >
            Patients List
          </Typography>
          <Typography
            color="gray"
            className="mt-1 font-normal"
            placeholder={undefined}
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
          >
            List of all patients
          </Typography>
        </div>
        <div className="flex w-full items-center justify-between">
          <div className="w-3/4 md:w-72">
            <Input
              label="Search"
              icon={<MagnifyingGlassIcon className="h-5 w-5" />}
              onPointerEnterCapture={undefined}
              onPointerLeaveCapture={undefined}
              crossOrigin={undefined}
            />
          </div>
          <Button
            className="flex items-center gap-3"
            size="md"
            placeholder={undefined}
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
            onClick={formModal.openModal}
          >
            <UserPlusIcon strokeWidth={2} className="h-4 w-4"  /> Add Patient
          </Button>
        </div>
      </div>
    </CardHeader>
  );
}
