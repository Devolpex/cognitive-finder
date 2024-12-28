export interface PositionResponseTRC {
    id: number;
    attributes: PositionAttributes;
    deviceId: number;
    protocol: string;
    serverTime: string;
    deviceTime: string;
    fixTime: string;
    outdated: boolean;
    valid: boolean;
    latitude: number;
    longitude: number;
    altitude: number;
    speed: number;
    course: number;
    address: string;
    accuracy: number;
    network: string;
    geofenceIds: number[];
  }
  
  export interface PositionAttributes {
    batteryLevel: number;
    distance: number;
    totalDistance: number;
    motion: boolean;
  }
  