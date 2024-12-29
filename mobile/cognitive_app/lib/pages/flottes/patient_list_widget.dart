import 'package:cognitive_app/model/patient.dart';
import 'package:flutter/material.dart';
import 'package:cognitive_app/services/patient_service.dart';

class PatientListWidget extends StatefulWidget {
  final String searchQuery;
  // final Function(Patient) onDeviceTap;

  const PatientListWidget({
    super.key,
    required this.searchQuery,
    // required this.onDeviceTap,
  });

    @override
  _PatientListWidgetState createState() => _PatientListWidgetState();
}

class _PatientListWidgetState extends State<PatientListWidget> {
  late Future<List<Patient>> _patientsFuture;
  final PatientService _patientService = PatientService();

  @override
  void initState() {
    super.initState();
    _loadPatients();
  }

  Future<void> _loadPatients() async {
    setState(() {
      _patientsFuture = _patientService.getPatientsPage();
    });
  }

  @override
  Widget build(BuildContext context) {
    return RefreshIndicator(
      onRefresh: _loadPatients,
      child: FutureBuilder<List<Patient>>(
        future: _patientsFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return Center(child: Text('No patients found'));
          } else {
            List<Patient> patients = snapshot.data!;
            return ListView.builder(
              itemCount: patients.length,
              itemBuilder: (context, index) {
                Patient patient = patients[index];
                return ListTile(
                  title: Text(patient.name ?? 'No Name'),
                  subtitle: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text('Maladie: ${patient.maladie ?? 'Unknown'}'),
                      if (patient.device != null) ...[
                        Text('Device IMEI: ${patient.device!.imei ?? 'Unknown'}'),
                        Text('Device SIM: ${patient.device!.sim ?? 'Unknown'}'),
                      ],
                    ],
                  ),
                );
              },
            );
          }
        },
      ),
    );
  }
}
