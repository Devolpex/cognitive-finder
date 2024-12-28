/* eslint-disable react-hooks/exhaustive-deps */
import {
  CardBody,
  IconButton,
  Tooltip,
  Typography,
} from "@material-tailwind/react";
import * as React from "react";
import { usePatient } from "../context/PatientProvider";
import { IPatient } from "../types/patient";
import { PencilIcon } from "@heroicons/react/16/solid";
import { TrashIcon } from "@heroicons/react/24/outline";
import { useNavigate } from "react-router-dom";
import { MapIcon } from "@heroicons/react/24/solid";

// export interface IPatientTableBodyProps {}

export function PatientTableBody() {
  const headers = [
    "Patient Name",
    "Maladie",
    // "Client Name",
    // "Client Email",
    "Device IMEI",
    "Device SIM",
    "Actions",
  ];

  const { patients, fetchPatientsList, deleteModal } = usePatient();
  const navigate = useNavigate();


  React.useEffect(() => {
    fetchPatientsList();
  }, []);

  const { setIID, formModal } = usePatient();

  const handleEdit = (id: string) => {
    console.log("Edit", id);
    setIID({ fetchID: "", deleteID: "", updateID: id });
    formModal.openModal();
  };

  const handleDelete = (id: string) => {
    console.log("Delete", id);
    setIID({ fetchID: "", deleteID: id, updateID: "" });
    deleteModal.openModal();
  };

  const navigateToPatientMap = (id: string) => {
    navigate(`/patient/map/${id}`);
  };

  return (
    <CardBody
      className="overflow-scroll px-0"
      placeholder={undefined}
      onPointerEnterCapture={undefined}
      onPointerLeaveCapture={undefined}
    >
      <table className="w-full min-w-max table-auto text-left">
        <thead>
          <tr>
            {headers.map((head) => (
              <th
                key={head}
                className="border-y border-blue-gray-100 bg-blue-gray-50/50 p-4"
              >
                <Typography
                  variant="small"
                  color="blue-gray"
                  className="font-normal leading-none opacity-70"
                  placeholder={undefined}
                  onPointerEnterCapture={undefined}
                  onPointerLeaveCapture={undefined}
                >
                  {head}
                </Typography>
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {patients.map((patient: IPatient, index) => {
            const isLast = index === patients.length - 1;
            const classes = isLast ? "p-4" : "p-4 border-b border-blue-gray-50";

            return (
              <tr key={patient.id}>
                <td className={classes}>
                  <Typography
                    variant="small"
                    color="blue-gray"
                    className="font-normal"
                    placeholder={undefined}
                    onPointerEnterCapture={undefined}
                    onPointerLeaveCapture={undefined}
                  >
                    {patient.name}
                  </Typography>
                </td>
                <td className={classes}>
                  <Typography
                    variant="small"
                    color="blue-gray"
                    className="font-normal"
                    placeholder={undefined}
                    onPointerEnterCapture={undefined}
                    onPointerLeaveCapture={undefined}
                  >
                    {patient.maladie}
                  </Typography>
                </td>
                {/* <td className={classes}>
                  <Typography
                    variant="small"
                    color="blue-gray"
                    className="font-normal"
                    placeholder={undefined}
                    onPointerEnterCapture={undefined}
                    onPointerLeaveCapture={undefined}
                  >
                    {patient.client?.name}
                  </Typography>
                </td>
                <td className={classes}>
                  <Typography
                    variant="small"
                    color="blue-gray"
                    className="font-normal"
                    placeholder={undefined}
                    onPointerEnterCapture={undefined}
                    onPointerLeaveCapture={undefined}
                  >
                    {patient.client?.email}
                  </Typography>
                </td> */}
                <td className={classes}>
                  <Typography
                    variant="small"
                    color="blue-gray"
                    className="font-normal"
                    placeholder={undefined}
                    onPointerEnterCapture={undefined}
                    onPointerLeaveCapture={undefined}
                  >
                    {patient.device?.imei}
                  </Typography>
                </td>
                <td className={classes}>
                  <Typography
                    variant="small"
                    color="blue-gray"
                    className="font-normal"
                    placeholder={undefined}
                    onPointerEnterCapture={undefined}
                    onPointerLeaveCapture={undefined}
                  >
                    {patient.device?.sim}
                  </Typography>
                </td>
                <td className={classes}>
                  <div className="flex items-center justify-center gap-2">
                    <Tooltip content="Edit User">
                      <IconButton
                        placeholder={undefined}
                        onPointerEnterCapture={undefined}
                        onPointerLeaveCapture={undefined}
                        color="green"
                        onClick={() => handleEdit(patient.id)}
                      >
                        <PencilIcon className="h-4 w-4" />
                      </IconButton>
                    </Tooltip>
                    <Tooltip content="Delete User">
                      <IconButton
                        placeholder={undefined}
                        onPointerEnterCapture={undefined}
                        onPointerLeaveCapture={undefined}
                        color="red"
                        onClick={() => handleDelete(patient.id)}
                      >
                        <TrashIcon className="h-4 w-4" />
                      </IconButton>
                    </Tooltip>
                    <Tooltip content="View on Map">
                      <IconButton
                        placeholder={undefined}
                        onPointerEnterCapture={undefined}
                        onPointerLeaveCapture={undefined}
                        color="blue"
                        onClick={() => navigateToPatientMap(patient.id)}
                      >
                        <MapIcon className="h-4 w-4" />
                      </IconButton>
                    </Tooltip>
                  </div>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </CardBody>
  );
}
