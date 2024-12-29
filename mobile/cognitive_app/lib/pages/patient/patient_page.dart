import 'package:cognitive_app/model/patient.dart';
import 'package:cognitive_app/utils/colors.dart';
import 'package:flutter/material.dart';
import 'package:cognitive_app/services/patient_service.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'add_patient_page.dart';  // Import the new AddPatientPage

class PatientPage extends StatefulWidget {
  final String accessToken;

  const PatientPage({required this.accessToken, Key? key}) : super(key: key);

  @override
  _PatientPageState createState() => _PatientPageState();
}

class _PatientPageState extends State<PatientPage> {
  final PatientService _patientService = PatientService();
  late Future<List<Patient>> _patientsFuture;
  String searchQuery = '';
  ScrollController _scrollController = ScrollController();
  List<Patient> _patients = [];
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _patientsFuture = _fetchPatients();
    _scrollController.addListener(_scrollListener);
  }

  Future<List<Patient>> _fetchPatients() async {
    setState(() {
      _isLoading = true;
    });
    try {
      List<Patient> patients = await _patientService.getPatientsPage();
      setState(() {
        _patients.addAll(patients);
      });
      return patients;
    } catch (e) {
      throw Exception("Failed to load patients: $e");
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
  }

  void _scrollListener() {
    if (_scrollController.position.pixels == _scrollController.position.maxScrollExtent && !_isLoading) {
      _fetchPatients();
    }
  }

  Future<void> _onRefresh() async {
    setState(() {
      _patients.clear();
      _patientsFuture = _fetchPatients(); // Refresh by re-fetching data
    });
  }

  @override
  void dispose() {
    _scrollController.removeListener(_scrollListener);
    _scrollController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        foregroundColor: white,
        backgroundColor: primary,
        title: Text("Patients"),
      ),
      body: Padding(
        padding: EdgeInsets.all(8.0),
        child: Column(
          children: [
            Padding(
              padding: EdgeInsets.all(8.0),
              child: TextField(
                onChanged: (value) {
                  setState(() {
                    searchQuery = value;
                  });
                },
                style: const TextStyle(color: primary),
                decoration: InputDecoration(
                  hintText: "Rechercher un Patient ...",
                  hintStyle: TextStyle(color: Colors.black.withOpacity(0.7), fontSize: 18.sp),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8.0),
                    borderSide: BorderSide(color: primary),
                  ),
                  suffixIcon: Icon(Icons.search, color: primary, size: 30.sp),
                ),
              ),
            ),
            Expanded(
              child: RefreshIndicator(
                onRefresh: _onRefresh,
                child: FutureBuilder<List<Patient>>(
                  future: _patientsFuture,
                  builder: (context, snapshot) {
                    if (snapshot.connectionState == ConnectionState.waiting && _patients.isEmpty) {
                      return Center(child: CircularProgressIndicator());
                    } else if (snapshot.hasError) {
                      return Center(child: Text('Error: ${snapshot.error}'));
                    } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
                      return Center(child: Text('No patients found'));
                    } else {
                      List<Patient> patients = _patients;

                      if (searchQuery.isNotEmpty) {
                        patients = patients.where((patient) {
                          return patient.name!.toLowerCase().contains(searchQuery.toLowerCase());
                        }).toList();
                      }

                      return ListView.builder(
                        controller: _scrollController,
                        itemCount: patients.length + (_isLoading ? 1 : 0),
                        itemBuilder: (context, index) {
                          if (index == patients.length) {
                            return Center(child: CircularProgressIndicator());
                          }

                          Patient patient = patients[index];
                          return Card(
                            elevation: 4.0,
                            margin: EdgeInsets.symmetric(vertical: 8.h, horizontal: 10.w),
                            child: ListTile(
                              contentPadding: EdgeInsets.all(16.0),
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
                            ),
                          );
                        },
                      );
                    }
                  },
                ),
              ),
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => AddPatientPage(accessToken: widget.accessToken),
            ),
          );
        },
        backgroundColor: primary,
        child: Icon(Icons.add),
      ),
    );
  }
}
