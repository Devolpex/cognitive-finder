class Patient {
  String? id;
  String? name;
  String? maladie;

  Patient({
    this.id,
    this.name,
    this.maladie,
  });

  // Factory method to create a Patient object from a JSON map
  factory Patient.fromJson(Map<String, dynamic> json) {
    return Patient(
      id: json['id'],
      name: json['name'],
      maladie: json['maladie'],
    );
  }

  // Method to convert a Patient object to a JSON map
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'maladie': maladie,
    };
  }
}