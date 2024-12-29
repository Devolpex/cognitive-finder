class Device {
  int? id;
  String? imei;
  String? sim;
  String? patientId;
  int? traccarId;

  Device({
    this.id,
    this.imei,
    this.sim,
    this.patientId,
    this.traccarId,
  });

  // Factory method to create a Device object from a JSON map
  factory Device.fromJson(Map<String, dynamic> json) {
    return Device(
      id: json['id'],
      imei: json['imei'],
      sim: json['sim'],
      patientId: json['patientId'],
      traccarId: json['traccarId'],
    );
  }

  // Method to convert a Device object to a JSON map
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'imei': imei,
      'sim': sim,
      'patientId': patientId,
      'traccarId': traccarId,
    };
  }
}