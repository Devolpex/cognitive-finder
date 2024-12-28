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
