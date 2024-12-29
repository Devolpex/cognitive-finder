import 'package:cognitive_app/model/device.dart';
import 'package:cognitive_app/utils/colors.dart';
import 'package:flutter/material.dart';
import 'package:cognitive_app/model/patient.dart';
import 'package:cognitive_app/services/patient_service.dart';

class AddPatientPage extends StatefulWidget {
  final String accessToken;

  const AddPatientPage({required this.accessToken, Key? key}) : super(key: key);

  @override
  _AddPatientPageState createState() => _AddPatientPageState();
}

class _AddPatientPageState extends State<AddPatientPage> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _maladieController = TextEditingController();
  final _imeiController = TextEditingController();
  final _simController = TextEditingController();

  final PatientService _patientService = PatientService();

  Future<void> _addPatient() async {
    if (_formKey.currentState!.validate()) {
      try {
        Patient newPatient = Patient(
          name: _nameController.text,
          maladie: _maladieController.text,
          device: Device(imei: _imeiController.text, sim: _simController.text),
        );
        // await _patientService.addPatient(widget.accessToken, newPatient); // Add the patient using the service
        Navigator.pop(context); // Close the form page after adding the patient
      } catch (e) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Failed to add patient: $e')),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Add Patient"),
        backgroundColor: primary,
        foregroundColor: white,
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              TextFormField(
                controller: _nameController,
                decoration: InputDecoration(labelText: "Name"),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return "Please enter the patient's name";
                  }
                  return null;
                },
              ),
              TextFormField(
                controller: _maladieController,
                decoration: InputDecoration(labelText: "Maladie"),
              ),
              TextFormField(
                controller: _imeiController,
                decoration: InputDecoration(labelText: "Device IMEI"),
              ),
              TextFormField(
                controller: _simController,
                decoration: InputDecoration(labelText: "Device SIM"),
              ),
              SizedBox(height: 20),
              ElevatedButton(
                onPressed: _addPatient,
                child: Text("Add Patient"),
                style: ElevatedButton.styleFrom(backgroundColor: primary, foregroundColor: white),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
