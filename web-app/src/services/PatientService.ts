/* eslint-disable no-useless-catch */
import axiosInstance from "../config/axios.config";
import { IPatient, IPatientREQ } from "../types/patient";
/**
 * this service to call the patient API to fetch patients list
 *
 */

export const fetchPatientsAPI = async (): Promise<IPatient[]> => {
  try {
    const response = await axiosInstance.get("/api/v1/patients/list");
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * This service to call the patient API to create a new patient
 */
export const createPatientAPI = async (req: IPatientREQ): Promise<IPatient> => {
  try {
    const response = await axiosInstance.post("/api/v1/patient", req);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * This service to call the patient API to fetch patient by id
 */
export const fetchPatientByIdAPI = async (id: string): Promise<IPatient> => {
  try {
    const response = await axiosInstance.get(`/api/v1/patient/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * this service to update the patient by id
 */
export const updatePatientAPI = async (
  id: string,
  req: IPatientREQ
): Promise<IPatient> => {
  try {
    const response = await axiosInstance.put(`/api/v1/patient/${id}`, req);
    return response.data;
  } catch (error) {
    throw error;
  }
};

/**
 * this service to delete the patient by id
 */
export const deletePatientAPI = async (id: string): Promise<void> => {
  try {
    const response = await axiosInstance.delete(`/api/v1/patient/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
}
