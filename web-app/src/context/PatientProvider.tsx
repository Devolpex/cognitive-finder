/* eslint-disable react-refresh/only-export-components */
import React, { createContext, useContext, ReactNode } from "react";
import { IPatient, IPatientREQ } from "../types/patient";
import { fetchPatientsAPI } from "../services/PatientService";
import { IID, ILoading, IModal } from "../types";
import { Alert } from "@material-tailwind/react";

// Define the type for the context value
type PatientContextType = {
  patients: IPatient[];
  setPatients: (patients: IPatient[]) => void;
  request: IPatientREQ;
  setRequest: (request: IPatientREQ) => void;
  fetchPatientsList: () => void;
  loading: ILoading;
  setLoading: (loading: ILoading) => void;
  formModal: IModal;
  message: string | null;
  setMessage: (message: string | null) => void;
  IID: IID<string>;
  setIID: (id: IID<string>) => void;
  deleteModal: IModal;
};

// Create the context
const PatientContext = createContext<PatientContextType | undefined>(undefined);

// Define props for the provider
type PatientProviderProps = {
  children: ReactNode;
};

// Create the provider component
export const PatientProvider = ({ children }: PatientProviderProps) => {
  // Define the state
  const [patients, setPatients] = React.useState<IPatient[]>([]);

  const [request, setRequest] = React.useState<IPatientREQ>({
    name: "",
    maladie: "",
    deviceImei: "",
    deviceNumber: "",
    clientId: "",
  });

  // Loading state
  const [loading, setLoading] = React.useState<ILoading>({
    isTable: false,
    isForm: false,
    isDelete: false,
  });

  // Fetch all patients
  const fetchPatientsList = async () => {
    setLoading({ ...loading, isTable: true });
    fetchPatientsAPI()
      .then((res: IPatient[]) => {
        setPatients(res);
        console.log("patients data", res);
      })
      .catch((err) => {
        console.log("error", err);
      })
      .finally(() => {
        setLoading({ ...loading, isTable: false });
        console.log("finally");
      });
  };

  // Modal state
  const [formModal, setFormModal] = React.useState<IModal>({
    isOpen: false,
    openModal: () => setFormModal({ ...formModal, isOpen: true }),
    closeModal: () => setFormModal({ ...formModal, isOpen: false }),
  });

  // Message state
  const [message, setMessage] = React.useState<string | null>(null);

  // Automatically clear the message after 5 seconds
  React.useEffect(() => {
    if (message) {
      const timer = setTimeout(() => setMessage(null), 5000);
      return () => clearTimeout(timer); // Cleanup timeout if message changes
    }
  }, [message]);

  // IID state
  const [IID, setIID] = React.useState<IID<string>>({
    fetchID: "",
    deleteID: "",
    updateID: "",
  });

  // Delete Modal
  const [deleteModal, setDeleteModal] = React.useState<IModal>({
    isOpen: false,
    openModal: () => setDeleteModal({ ...deleteModal, isOpen: true }),
    closeModal: () => setDeleteModal({ ...deleteModal, isOpen: false }),
  });

  return (
    <PatientContext.Provider
      value={{
        patients,
        setPatients,
        request,
        setRequest,
        fetchPatientsList,
        loading,
        setLoading,
        formModal,
        message,
        setMessage,
        IID,
        setIID,
        deleteModal,
      }}
    >
      {children}
      {message && (
        <div
          style={{
            position: "fixed",
            bottom: 20,
            right: 20,
            zIndex: 1000,
            padding: "10px",
            maxWidth: "400px",
          }}
        >
          <Alert color="green" onClose={() => setMessage(null)}>
            {message}
          </Alert>
        </div>
      )}
    </PatientContext.Provider>
  );
};

// Create a custom hook for easier use of the context
export const usePatient = (): PatientContextType => {
  const context = useContext(PatientContext);
  if (!context) {
    throw new Error("usePatient must be used within a PatientProvider");
  }
  return context;
};
