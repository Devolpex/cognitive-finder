import {
  Button,
  Dialog,
  Card,
  CardBody,
  CardFooter,
  Typography,
  Input,
} from "@material-tailwind/react";
import { usePatient } from "../context/PatientProvider";
import {
  createPatientAPI,
  fetchPatientByIdAPI,
  updatePatientAPI,
} from "../services/PatientService";
import { IPatient, IPatientREQ } from "../types/patient";
import React, { useState } from "react";

export function PatientFormModal() {
  const { formModal } = usePatient();
  const { request, setRequest } = usePatient();
  const [btnLoading, setBtnLoading] = useState(false);
  const { setMessage } = usePatient();
  const { setIID, IID } = usePatient();
  const { fetchPatientsList } = usePatient();
  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const onSubmit = () => {
    setRequest({ ...request, clientId: localStorage.getItem("userId") || "" });
    console.log("Request", request);

    // Validate inputs
    const validationErrors: { [key: string]: string } = {};
    if (!request.name) validationErrors.name = "Name of patient is required";
    if (!request.deviceImei)
      validationErrors.deviceImei = "Device IMEI is required";
    if (!request.clientId) validationErrors.clientId = "Client ID is required";
    if (!request.deviceNumber)
      validationErrors.deviceNumber = "Device number is required";

    setErrors(validationErrors);

    // If there are validation errors, do not submit
    if (Object.keys(validationErrors).length > 0) return;

    if (IID.updateID) {
      updatePatient(IID.updateID, request);
    } else {
      createPatient(request);
    }
  };

  const createPatient = async (req: IPatientREQ) => {
    console.log("create patient");
    setBtnLoading(true);
    createPatientAPI(req)
      .then((res: IPatient) => {
        console.log("patient created", res);
        setMessage("Patient " + res.name + " created successfully");
        setRequest({
          name: "",
          maladie: "",
          deviceImei: "",
          deviceNumber: "",
          clientId: "",
        });
        formModal.closeModal();
        fetchPatientsList();
      })
      .catch((err) => {
        console.log("error", err);
        // Handle error response here if necessary
      })
      .finally(() => {
        setBtnLoading(false);
      });
  };

  const fetchById = async (id: string) => {
    console.log("fetch by id", id);
    fetchPatientByIdAPI(id)
      .then((res: IPatient) => {
        console.log("patient fetched", res);
        setRequest({
          name: res.name,
          maladie: res.maladie,
          deviceImei: res.device?.imei || "",
          deviceNumber: res.device?.sim || "",
        });
        fetchPatientsList();
      })
      .catch((err) => {
        console.log("error", err);
      });
  };

  const updatePatient = async (id: string, req: IPatientREQ) => {
    console.log("update patient");
    setBtnLoading(true);
    updatePatientAPI(id, req)
      .then((res: IPatient) => {
        console.log("patient updated", res);
        setMessage("Patient " + res.name + " updated successfully");
        setRequest({
          name: "",
          maladie: "",
          deviceImei: "",
          deviceNumber: "",
          clientId: "",
        });
        formModal.closeModal();
        setIID({ updateID: "", fetchID: "", deleteID: "" });
      })
      .catch((err) => {
        console.log("error", err);
      })
      .finally(() => {
        setBtnLoading(false);
      });
  };

  React.useEffect(() => {
    setRequest({ ...request, clientId: localStorage.getItem("userId") || "" });
    console.log("Update ID", IID.updateID);
    if (IID.updateID) {
      fetchById(IID.updateID);
    }
  }, [IID.updateID]);

  return (
    <Dialog
      size="xs"
      open={formModal.isOpen}
      handler={formModal.closeModal}
      className="bg-transparent shadow-none"
      placeholder={undefined}
      onPointerEnterCapture={undefined}
      onPointerLeaveCapture={undefined}
    >
      <Card
        className="mx-auto w-full max-w-[24rem]"
        placeholder={undefined}
        onPointerEnterCapture={undefined}
        onPointerLeaveCapture={undefined}
      >
        <CardBody
          className="flex flex-col gap-4"
          placeholder={undefined}
          onPointerEnterCapture={undefined}
          onPointerLeaveCapture={undefined}
        >
          <Typography
            variant="h4"
            color="blue-gray"
            placeholder={undefined}
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
          >
            {IID.updateID ? "Edit Patient" : "Add Patient"}
          </Typography>
          <Typography
            className="mb-3 font-normal"
            variant="paragraph"
            color="gray"
            placeholder={undefined}
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
          >
            Enter your patient details
          </Typography>

          {/* Patient Name */}
          <Typography
            className="-mb-2"
            variant="h6"
            placeholder={undefined}
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
          >
            Your Patient Name
          </Typography>
          <Input
            label="Patient Name"
            size="lg"
            value={request.name}
            onChange={(e) => setRequest({ ...request, name: e.target.value })}
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
            crossOrigin={undefined}
            error={errors.name ? true : false}  // Add conditional error prop

          />
          {errors.name && (
            <Typography
              className="text-red-500 text-sm"
              placeholder={undefined}
              onPointerEnterCapture={undefined}
              onPointerLeaveCapture={undefined}
            >
              {errors.name}
            </Typography>
          )}

          {/* Patient Maladie */}
          <Typography
            className="-mb-2"
            variant="h6"
            placeholder={undefined}
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
          >
            Patient Maladie
          </Typography>
          <Input
            label="Patient Maladie"
            size="lg"
            value={request.maladie}
            onChange={(e) =>
              setRequest({ ...request, maladie: e.target.value })
            }
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
            crossOrigin={undefined}
            error={errors.maladie ? true : false}  // Add conditional error prop

          />
          {errors.maladie && (
            <Typography
              className="text-red-500 text-sm"
              placeholder={undefined}
              onPointerEnterCapture={undefined}
              onPointerLeaveCapture={undefined}
            >
              {errors.maladie}
            </Typography>
          )}

          {/* Device Imei */}
          <Typography
            className="-mb-2"
            variant="h6"
            placeholder={undefined}
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
          >
            Device Imei
          </Typography>
          <Input
            label="Device Imei"
            size="lg"
            value={request.deviceImei}
            onChange={(e) =>
              setRequest({ ...request, deviceImei: e.target.value })
            }
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
            crossOrigin={undefined}
            error={errors.deviceImei ? true : false}  // Add conditional error prop
          />
          {errors.deviceImei && (
            <Typography
              className="text-red-500 text-sm"
              placeholder={undefined}
              onPointerEnterCapture={undefined}
              onPointerLeaveCapture={undefined}
            >
              {errors.deviceImei}
            </Typography>
          )}

          {/* Device Number */}
          <Typography
            className="-mb-2"
            variant="h6"
            placeholder={undefined}
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
          >
            Device Number
          </Typography>
          <Input
            label="Device Number"
            size="lg"
            value={request.deviceNumber}
            onChange={(e) =>
              setRequest({ ...request, deviceNumber: e.target.value })
            }
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
            crossOrigin={undefined}
            error={errors.deviceNumber ? true : false}  // Add conditional error prop

          />
          {errors.deviceNumber && (
            <Typography
              className="text-red-500 text-sm"
              placeholder={undefined}
              onPointerEnterCapture={undefined}
              onPointerLeaveCapture={undefined}
            >
              {errors.deviceNumber}
            </Typography>
          )}
        </CardBody>
        <CardFooter
          className="pt-0"
          placeholder={undefined}
          onPointerEnterCapture={undefined}
          onPointerLeaveCapture={undefined}
        >
          <Button
            variant="gradient"
            onClick={onSubmit}
            fullWidth
            placeholder={undefined}
            onPointerEnterCapture={undefined}
            onPointerLeaveCapture={undefined}
          >
            {btnLoading ? "Loading..." : "Save"}
          </Button>
        </CardFooter>
      </Card>
    </Dialog>
  );
}