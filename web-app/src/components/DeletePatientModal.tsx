import {
  Button,
  Dialog,
  DialogHeader,
  DialogBody,
  DialogFooter,
} from "@material-tailwind/react";
import { usePatient } from "../context/PatientProvider";
import { deletePatientAPI } from "../services/PatientService";

export function DeletePatientModal() {
  const { deleteModal, IID, setMessage,fetchPatientsList } = usePatient();

  const onDelete = () => {
    deletePatientAPI(IID.deleteID)
      .then((res) => {
        console.log("patient deleted", res);
        deleteModal.closeModal();
        setMessage("Patient deleted successfully");
        fetchPatientsList();
      })
      .catch((err) => {
        console.log("error", err);
      })
      .finally(() => {
        console.log("finally");
      });
  };

  return (
    <Dialog
      open={deleteModal.isOpen}
      handler={deleteModal.closeModal}
      placeholder={undefined}
      onPointerEnterCapture={undefined}
      onPointerLeaveCapture={undefined}
    >
      <DialogHeader
        placeholder={undefined}
        onPointerEnterCapture={undefined}
        onPointerLeaveCapture={undefined}
      >
        Confirm Deletion
      </DialogHeader>
      <DialogBody
        placeholder={undefined}
        onPointerEnterCapture={undefined}
        onPointerLeaveCapture={undefined}
      >
        Are you sure you want to delete this patient? This action is
        irreversible, and all associated data will be permanently removed.
      </DialogBody>
      <DialogFooter
        placeholder={undefined}
        onPointerEnterCapture={undefined}
        onPointerLeaveCapture={undefined}
      >
        <Button
          variant="text"
          color="red"
          onClick={deleteModal.closeModal}
          className="mr-1"
          placeholder={undefined}
          onPointerEnterCapture={undefined}
          onPointerLeaveCapture={undefined}
        >
          <span>Cancel</span>
        </Button>
        <Button
          variant="gradient"
          color="green"
          onClick={onDelete}
          placeholder={undefined}
          onPointerEnterCapture={undefined}
          onPointerLeaveCapture={undefined}
        >
          <span>Delete</span>
        </Button>
      </DialogFooter>
    </Dialog>
  );
}
