import 'device.dart';

class Patient {
  String? id;
  String? name;
  String? maladie;
  String? client;
  Device? device;

  Patient({
    this.id,
    this.name,
    this.maladie,
    this.client,
    this.device,
  });

  // Factory method to create a Patient object from a JSON map
  factory Patient.fromJson(Map<String, dynamic> json) {
    return Patient(
      id: json['id'],
      name: json['name'],
      maladie: json['maladie'],
      client: json['client'],
      device: json['device'] != null ? Device.fromJson(json['device']) : null,
    );
  }

  // Method to convert a Patient object to a JSON map
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'maladie': maladie,
      'client': client,
      'device': device?.toJson(),
    };
  }
}