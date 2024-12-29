export interface IPatient {
  id: string;
  name: string;
  maladie: string;
  client?: IClient;
  device?: IDevice;
}

export interface IClient {
  id: string;
  name: string;
  email: string;
}

export interface IDevice {
  id: number;
  imei: string;
  sim: string;
}

export interface IPatientREQ {
  name: string;
  maladie?: string;
  deviceImei: string;
  deviceNumber: string;
  clientId?: string;
}
