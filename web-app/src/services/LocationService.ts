/* eslint-disable no-useless-catch */
import axiosInstance from "../config/axios.config";
import { PositionResponseTRC } from "../types/location";

/**
 * Service to get position of the patient by id
 */
export const getLocationByPatientId = async (
  patientId: string
): Promise<PositionResponseTRC> => {
  try {
    const res = await axiosInstance.get(
      `/api/v1/positions/patient/${patientId}`
    );
    return res.data;
  } catch (error) {
    throw error;
  }
};

/**
 * Service to get positions of the client by id
 */
export const getLocationByClientIdAPI = async (
  clientId: string
): Promise<PositionResponseTRC[]> => {
  try {
    const res = await axiosInstance.get(`/api/v1/positions/client/${clientId}`);
    return res.data;
  } catch (error) {
    throw error;
  }
};
